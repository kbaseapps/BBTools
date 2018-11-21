import unittest
from BBTools.BBToolsImpl import BBTools
from BBTools.BBToolsServer import MethodContext
from BBTools.authclient import KBaseAuth as _KBaseAuth
from BBTools.utils.MemEstimatorRunner import MemEstimatorRunner
import os
try:
    from ConfigParser import ConfigParser  # py2
except:
    from configparser import ConfigParser  # py3

class MemEstimatorTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        token = os.environ.get('KB_AUTH_TOKEN', None)
        config_file = os.environ.get('KB_DEPLOYMENT_CONFIG', None)
        cls.cfg = {}
        config = ConfigParser()
        config.read(config_file)
        for nameval in config.items('BBTools'):
            cls.cfg[nameval[0]] = nameval[1]
        # Getting username from Auth profile for token
        authServiceUrl = cls.cfg['auth-service-url']
        auth_client = _KBaseAuth(authServiceUrl)
        user_id = auth_client.get_user(token)
        # WARNING: don't call any logging methods on the context object,
        # it'll result in a NoneType error
        cls.ctx = MethodContext(None)
        cls.ctx.update({'token': token,
                        'user_id': user_id,
                        'provenance': [
                            {'service': 'BBTools',
                             'method': 'please_never_use_it_in_production',
                             'method_params': []
                             }],
                        'authenticated': 1})
        cls.serviceImpl = BBTools(cls.cfg)
        cls.scratch = cls.cfg['scratch']
        cls.callback_url = os.environ['SDK_CALLBACK_URL']

    def test_mem_estimator_ok_unit(self):
        in_file = "data/interleaved.fastq"
        params = {
            "file": in_file
        }
        runner = MemEstimatorRunner(params)
        estimate = runner.run()
        self.assertGreater(estimate, 0)

    def test_mem_estimator_ok_pair(self):
        params = {
            "file": "data/small.forward.fq",
            "file2": "data/small.reverse.fq"
        }
        runner = MemEstimatorRunner(params)
        estimate = runner.run()
        self.assertGreater(estimate, 0)

    def test_mem_estimator_pair_dups(self):
        in_file = "data/interleaved.fastq"
        params = {
            "file": in_file,
            "file2": in_file
        }
        with self.assertRaises(ValueError) as e:
            MemEstimatorRunner(params)
        self.assertIn("If two files are present, they must be different.", str(e.exception))

    def test_mem_estimator_bad_file(self):
        in_file = "data/bad_reads.txt"
        params = {
            "file": in_file
        }
        with self.assertRaises(ValueError) as e:
            MemEstimatorRunner(params).run()
        self.assertIn("Count of unique 31-mers not found.", str(e.exception))
        self.assertNotIn("31-mers processed", str(e.exception))
        self.assertNotIn("Correct 31-mers", str(e.exception))
        self.assertNotIn("Unique 31-mers", str(e.exception))

    def getImpl(self):
        return self.__class__.serviceImpl

    def getContext(self):
        return self.__class__.ctx

    def test_mem_estimator_e2e(self):
        impl = self.getImpl()
        ctx = self.getContext()
        in_file = "data/interleaved.fastq"
        params = {
            "file": in_file
        }
        res = impl.run_mem_estimator(ctx, params)[0]
        self.assertIn("estimate", res)
        self.assertGreater(res["estimate"], 0)

        params = {
            "file": "data/small.forward.fq",
            "file2": "data/small.reverse.fq"
        }
        res = impl.run_mem_estimator(ctx, params)[0]
        self.assertIn("estimate", res)
        self.assertGreater(res["estimate"], 0)

    def test_bad_inputs(self):
        impl = self.getImpl()
        ctx = self.getContext()
        with self.assertRaises(ValueError) as e:
            MemEstimatorRunner({})
        self.assertIn('Parameter "file" must be present!', str(e.exception))

        with self.assertRaises(ValueError) as e:
            impl.run_mem_estimator(ctx, {"file": "not_real"})
        self.assertIn("The file not_real does not seem to exist, or is a directory", str(e.exception))

        with self.assertRaises(ValueError) as e:
            impl.run_mem_estimator(ctx, {"file": "data/interleaved.fastq", "file2": "not_real2"})
        self.assertIn("The file not_real2 does not seem to exist, or is a directory", str(e.exception))