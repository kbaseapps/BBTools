import unittest
from BBTools.BBToolsImpl import BBTools
from BBTools.utils.MemEstimatorRunner import MemEstimatorRunner

class MemEstimatorTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        token = environ.get('KB_AUTH_TOKEN', None)
        config_file = environ.get('KB_DEPLOYMENT_CONFIG', None)
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
        cls.wsURL = cls.cfg['workspace-url']
        cls.ws = workspaceService(cls.wsURL)
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
        in_fie = "data/interleaved.fastq"
        params = {
            "file": in_file,
            "file2": in_file
        }
        runner = MemEstimatorRunner(params)
        estimate = runner.run()
        self.assertGreater(estimate, 0)

    def test_mem_estimator_missing_input(self):
        with self.assertRaises(ValueError) as e:
            MemEstimatorRunner({})
        self.assertIn('Parameter "file" must be present!', str(e.exception))