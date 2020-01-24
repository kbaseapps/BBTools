# -*- coding: utf-8 -*-
import unittest
import os
import time
import shutil

from os import environ
try:
    from ConfigParser import ConfigParser  # py2
except:
    from configparser import ConfigParser  # py3

from pprint import pprint

from installed_clients.ReadsUtilsClient import ReadsUtils
from installed_clients.AssemblyUtilClient import AssemblyUtil

from installed_clients.WorkspaceClient import Workspace as workspaceService
from installed_clients.authclient import KBaseAuth as _KBaseAuth
from BBTools.BBToolsImpl import BBTools
from BBTools.BBToolsServer import MethodContext


class BBToolsTest(unittest.TestCase):

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

    @classmethod
    def tearDownClass(cls):
        if hasattr(cls, 'wsName'):
            cls.ws.delete_workspace({'workspace': cls.wsName})
            print('Test workspace was deleted')

    def getWsClient(self):
        return self.__class__.ws

    def getWsName(self):
        if hasattr(self.__class__, 'wsName'):
            return self.__class__.wsName
        suffix = int(time.time() * 1000)
        wsName = "test_BBTools_" + str(suffix)
        ret = self.getWsClient().create_workspace({'workspace': wsName})  # noqa
        self.__class__.wsName = wsName
        return wsName

    # call this method to get the WS object info of a Paired End Library (will
    # upload the example data if this is the first time the method is called during tests)
    def getPairedEndLibInfo(self, lib_name):
        if hasattr(self.__class__, 'pairedEndLibInfo'):
            if self.__class__.pairedEndLibInfo.get(lib_name):
                return self.__class__.pairedEndLibInfo[lib_name]

        # copy the local test file to the shared scratch space so that the ReadsUtils
        # container can see it.
        test_fastq_file_local = os.path.join('data', 'reads', lib_name)
        test_fastq_file_scratch = os.path.join(self.scratch, os.path.basename(test_fastq_file_local))
        shutil.copy(test_fastq_file_local, test_fastq_file_scratch)

        # call the ReadsUtils libary to upload the test data to KBase
        ru = ReadsUtils(os.environ['SDK_CALLBACK_URL'])
        paired_end_ref = ru.upload_reads({'fwd_file': test_fastq_file_scratch,
                                          'sequencing_tech': 'artificial reads',
                                          'interleaved': 1, 'wsname': self.getWsName(),
                                          'name': lib_name})['obj_ref']

        # get the object metadata for the new test dataset
        new_obj_info = self.ws.get_object_info_new({'objects': [{'ref': paired_end_ref}]})
        if not hasattr(self.__class__, 'pairedEndLibInfo'):
            self.__class__.pairedEndLibInfo = dict()
        self.__class__.pairedEndLibInfo[lib_name] = new_obj_info[0]
        return new_obj_info[0]


    # call this method to get the WS object info of an Assembly (will
    # upload the example data if this is the first time the method is called during tests)
    def getAssemblyInfo(self, ass_name):
        if hasattr(self.__class__, 'assemblyInfo'):
            if self.__class__.assemblyInfo.get(ass_name):
                return self.__class__.assemblyInfo[ass_name]

        # copy the local test file to the shared scratch space so that the AssemblyUtil
        # container can see it.
        test_fasta_file_local = os.path.join('data', 'assemblies', ass_name)
        test_fasta_file_scratch = os.path.join(self.scratch, os.path.basename(test_fasta_file_local))
        shutil.copy(test_fasta_file_local, test_fasta_file_scratch)

        # call the AssemblyUtil libary to upload the test data to KBase
        au = AssemblyUtil(os.environ['SDK_CALLBACK_URL'])
        ass_ref = au.save_assembly_from_fasta({'file': {'path': test_fasta_file_scratch},
                                               'workspace_name': self.getWsName(),
                                               'assembly_name': ass_name})

        # get the object metadata for the new test dataset
        new_obj_info = self.ws.get_object_info_new({'objects': [{'ref': ass_ref}]})
        if not hasattr(self.__class__, 'assemblyInfo'):
            self.__class__.assemblyInfo = dict()
        self.__class__.assemblyInfo[ass_name] = new_obj_info[0]
        return new_obj_info[0]


    def getImpl(self):
        return self.__class__.serviceImpl

    def getContext(self):
        return self.__class__.ctx

        
    # HIDE @unittest.skip('skip test_RQCFilter_basic_app()')  # Uncomment to skip
    def test_RQCFilter_basic_app(self):
        # get the test reads library
        lib_info = self.getPairedEndLibInfo('interleaved.fastq')
        print(lib_info)

        io_params = {
            'read_library_ref': str(lib_info[6]) + '/' + str(lib_info[0]) + '/' + str(lib_info[4]),
            'output_workspace_name': self.getWsName(),
            'output_library_name': 'filtered.reads'
        }
        run_params = {}
        bbtools = self.getImpl()
        res = bbtools.run_RQCFilter_app(self.ctx, io_params, run_params)

        print('result:')
        pprint(res)

    # HIDE @unittest.skip('skip test_BBMap_basic_app()')  # Uncomment to skip
    def test_BBMap_basic_app(self):

        # get the test assembly
        ass_name = 'Thermodesulfo_trim.SPAdes.contigs.fa'
        ass_info = self.getAssemblyInfo(ass_name)
        ass_ref = '/'.join([str(ass_info[6]),
                            str(ass_info[0]),
                            str(ass_info[4])])
        print(ass_info)

        # get the test reads library
        lib_name = 'seven_species_nonuniform_10K.inter.fastq'
        lib_info = self.getPairedEndLibInfo(lib_name)
        lib_ref = '/'.join([str(lib_info[6]),
                            str(lib_info[0]),
                            str(lib_info[4])])
        print(lib_info)

        io_params = {
            'in_assembly_refs': [ass_ref],
            'in_readslib_ref': lib_ref,
            'workspace_name': self.getWsName(),
            'out_obj_name': lib_name+'.out.reads'
        }
        run_params = {
            'get_mapped_reads': '1',
            'get_unmapped_reads': '1'
        }
        bbtools = self.getImpl()
        res = bbtools.run_BBMap(self.ctx, io_params, run_params)

        print('result:')
        pprint(res)

    # HIDE @unittest.skip('skip test_RQCFilter_app_jgi_parameters()')  # Uncomment to skip
    def test_RQCFilter_app_jgi_parameters(self):
        lib_info = self.getPairedEndLibInfo('interleaved.fastq')
        io_params = {
            'read_library_ref': "{}/{}/{}".format(lib_info[6], lib_info[0], lib_info[4]),
            'output_workspace_name': self.getWsName(),
            'output_library_name': 'filtered_reads_all_params'
        }
        run_params = {
            'rna': 0,
            'trimfragadapter': 1,
            'qtrim': 'r',
            'trimq': 0,
            'maxns': 3,
            'minavgquality': 3,
            'minlength': 51,
            'mlf': 0.333,
            'phix': 1,
            'removehuman': 1,
            'removedog': 1,
            'removecat': 1,
            'removemouse': 1,
            'khist': 1,
            'removemicrobes': 1,
            'clumpify': 1
        }
        bbtools = self.getImpl()
        res = bbtools.run_RQCFilter_app(self.ctx, io_params, run_params)[0]
        print('result:')
        pprint(res)
        self.assertIn('report_name', res)
        self.assertIn('report_ref', res)
        self.assertIn('run_command', res)
        self.assertIn('rqcfilter2.sh', res['run_command'])

    # HIDE @unittest.skip('skip test_RQCFilter_app_bad_parameters()')  # Uncomment to skip
    def test_RQCFilter_app_bad_parameters(self):
        pass

    # HIDE @unittest.skip('skip test_RQCFilter_app_missing_parameters()')  # Uncomment to skip
    def test_RQCFilter_app_missing_parameters(self):
        pass

    # HIDE @unittest.skip('skip test_RQCFilter_local_mem_req()')  # Uncomment to skip
    def test_RQCFilter_local_mem_req(self):
        lib_info = self.getPairedEndLibInfo('interleaved.fastq')
        io_params = {
            "read_library_ref": "{}/{}/{}".format(lib_info[6], lib_info[0], lib_info[4]),
        }
        bbtools = self.getImpl()
        res = bbtools.run_RQCFilter_local(self.ctx, io_params, { "maxmem": 5 })
        self.assertIn('output_directory', res)
        self.assertIn('filtered_fastq_file', res)
        self.assertIn('run_log', res)
        self.assertIn('run_command', res)
        self.assertIn('rqcfilter2.sh', res['run_command'])
        self.assertIn('-Xmx5g', res['run_command'])

    # HIDE @unittest.skip('skip test_RQCFilter_local_bad_mem_param()')  # Uncomment to skip
    def test_RQCFilter_local_bad_mem_param(self):
        lib_info = self.getPairedEndLibInfo('interleaved.fastq')
        io_params = {
            "read_library_ref": "{}/{}/{}".format(lib_info[6], lib_info[0], lib_info[4]),
        }
        bbtools = self.getImpl()
        with self.assertRaises(ValueError) as e:
            bbtools.run_RQCFilter_local(self.ctx, io_params, { "maxmem": -1 })
        self.assertIn("The value of maxmem must be an integer > 0.", str(e.exception))
        with self.assertRaises(ValueError) as e:
            bbtools.run_RQCFilter_local(self.ctx, io_params, { "maxmem": "one" })
        self.assertIn("The value of maxmem must be an integer > 0.", str(e.exception))
        with self.assertRaises(ValueError) as e:
            bbtools.run_RQCFilter_local(self.ctx, io_params, { "maxmem": 0 })
        self.assertIn("The value of maxmem must be an integer > 0.", str(e.exception))

    # HIDE @unittest.skip('skip test_RQCFilter_run_local_reads_upa()')  # Uncomment to skip
    def test_RQCFilter_run_local_reads_upa(self):
        lib_info = self.getPairedEndLibInfo('interleaved.fastq')
        print(lib_info)

        io_params = {
            "read_library_ref": "{}/{}/{}".format(lib_info[6], lib_info[0], lib_info[4]),
        }
        run_params = {}
        bbtools = self.getImpl()
        res = bbtools.run_RQCFilter_local(self.ctx, io_params, run_params)[0]
        print('result:')
        pprint(res)
        self.assertIn('output_directory', res)
        self.assertTrue(os.path.exists(res['output_directory']))
        self.assertIn('filtered_fastq_file', res)
        self.assertTrue(os.path.exists(res['filtered_fastq_file']))
        self.assertIn('run_log', res)
        self.assertTrue(os.path.exists(res['run_log']))
        self.assertIn('run_command', res)
        self.assertIn('rqcfilter2.sh', res['run_command'])

    # HIDE @unittest.skip('skip test_RQCFilter_run_local_reads_file()')  # Uncomment to skip
    def test_RQCFilter_run_local_reads_file(self):
        test_fastq_file_local = os.path.join('data', 'reads', 'interleaved.fastq')
        test_fastq_file_scratch = os.path.join(self.scratch, os.path.basename(test_fastq_file_local))
        shutil.copy(test_fastq_file_local, test_fastq_file_scratch)

        io_params = {
            "reads_file": test_fastq_file_scratch
        }
        run_params = {}
        bbtools = self.getImpl()
        res = bbtools.run_RQCFilter_local(self.ctx, io_params, run_params)[0]
        print('result:')
        pprint(res)
        self.assertIn('output_directory', res)
        self.assertTrue(os.path.exists(res['output_directory']))
        self.assertIn('filtered_fastq_file', res)
        self.assertTrue(os.path.exists(res['filtered_fastq_file']))
        self.assertIn('run_log', res)
        self.assertTrue(os.path.exists(res['run_log']))
        self.assertIn('run_command', res)
        self.assertIn('rqcfilter2.sh', res['run_command'])


    # HIDE @unittest.skip('skip test_BBMap_run_local_reads_file_mapped_reads_01()')  # Uncomment to skip
    def test_BBMap_run_local_reads_file_mapped_reads_01(self):
        lib_name = 'seven_species_nonuniform_10K.inter.fastq.gz'
        test_reads_file_local = os.path.join('data', 'reads', lib_name)
        test_reads_file_scratch = os.path.join(self.scratch, os.path.basename(test_reads_file_local))
        shutil.copy(test_reads_file_local, test_reads_file_scratch)

        ass_name = 'Thermodesulfo_trim.SPAdes.contigs.fa.gz'
        test_ass_file_local = os.path.join('data', 'assemblies', ass_name)
        test_ass_file_scratch = os.path.join(self.scratch, os.path.basename(test_ass_file_local))
        shutil.copy(test_ass_file_local, test_ass_file_scratch)

        io_params = {
            "in_assembly_paths": [test_ass_file_scratch],
            "in_readslib_path":  test_reads_file_scratch,
            "out_obj_name":     'foo.out'
        }
        run_params = {
            "get_mapped_reads": '1',
            "get_unmapped_reads": '0',
            "get_bam": '0'
        }
        bbtools = self.getImpl()
        res = bbtools.run_BBMap_local(self.ctx, io_params, run_params)[0]
        print('result:')
        pprint(res)
        self.assertIn('output_directory', res)
        self.assertTrue(os.path.exists(res['output_directory']))
        self.assertIn('mapped_reads_files', res)
        for file_path in res['mapped_reads_files']:
            self.assertTrue(os.path.exists(file_path))
        self.assertIn('run_log', res)
        self.assertTrue(os.path.exists(res['run_log']))
        self.assertIn('run_command', res)
        self.assertIn('bbmap.sh', res['run_command'])


    # HIDE @unittest.skip('skip test_BBMap_run_local_reads_file_unmapped_reads_01()')  # Uncomment to skip
    def test_BBMap_run_local_reads_file_unmapped_reads_01(self):
        lib_name = 'seven_species_nonuniform_10K.inter.fastq.gz'
        test_reads_file_local = os.path.join('data', 'reads', lib_name)
        test_reads_file_scratch = os.path.join(self.scratch, os.path.basename(test_reads_file_local))
        shutil.copy(test_reads_file_local, test_reads_file_scratch)

        ass_name = 'Thermodesulfo_trim.SPAdes.contigs.fa.gz'
        test_ass_file_local = os.path.join('data', 'assemblies', ass_name)
        test_ass_file_scratch = os.path.join(self.scratch, os.path.basename(test_ass_file_local))
        shutil.copy(test_ass_file_local, test_ass_file_scratch)

        io_params = {
            "in_assembly_paths": [test_ass_file_scratch],
            "in_readslib_path":  test_reads_file_scratch,
            "out_obj_name":     'foo.out'
        }
        run_params = {
            "get_mapped_reads": '0',
            "get_unmapped_reads": '1',
            "get_bam": '0'
        }
        bbtools = self.getImpl()
        res = bbtools.run_BBMap_local(self.ctx, io_params, run_params)[0]
        print('result:')
        pprint(res)
        self.assertIn('output_directory', res)
        self.assertTrue(os.path.exists(res['output_directory']))
        self.assertIn('unmapped_reads_files', res)
        for file_path in res['unmapped_reads_files']:
            self.assertTrue(os.path.exists(file_path))
        self.assertIn('run_log', res)
        self.assertTrue(os.path.exists(res['run_log']))
        self.assertIn('run_command', res)
        self.assertIn('bbmap.sh', res['run_command'])


    # HIDE @unittest.skip('skip test_BBMap_run_local_reads_file_split_reads_01()')  # Uncomment to skip
    def test_BBMap_run_local_reads_file_split_reads_01(self):
        lib_name = 'seven_species_nonuniform_10K.inter.fastq.gz'
        test_reads_file_local = os.path.join('data', 'reads', lib_name)
        test_reads_file_scratch = os.path.join(self.scratch, os.path.basename(test_reads_file_local))
        shutil.copy(test_reads_file_local, test_reads_file_scratch)

        ass_name = 'Thermodesulfo_trim.SPAdes.contigs.fa.gz'
        test_ass_file_local = os.path.join('data', 'assemblies', ass_name)
        test_ass_file_scratch = os.path.join(self.scratch, os.path.basename(test_ass_file_local))
        shutil.copy(test_ass_file_local, test_ass_file_scratch)

        io_params = {
            "in_assembly_paths": [test_ass_file_scratch],
            "in_readslib_path":  test_reads_file_scratch,
            "out_obj_name":     'foo.out'
        }
        run_params = {
            "get_mapped_reads": '1',
            "get_unmapped_reads": '1',
            "get_bam": '0'
        }
        bbtools = self.getImpl()
        res = bbtools.run_BBMap_local(self.ctx, io_params, run_params)[0]
        print('result:')
        pprint(res)
        self.assertIn('output_directory', res)
        self.assertTrue(os.path.exists(res['output_directory']))
        self.assertIn('mapped_reads_files', res)
        for file_path in res['mapped_reads_files']:
            self.assertTrue(os.path.exists(file_path))
        self.assertIn('unmapped_reads_files', res)
        for file_path in res['unmapped_reads_files']:
            self.assertTrue(os.path.exists(file_path))
        self.assertIn('run_log', res)
        self.assertTrue(os.path.exists(res['run_log']))
        self.assertIn('run_command', res)
        self.assertIn('bbmap.sh', res['run_command'])


    @unittest.skip('skip test_BBMap_run_local_reads_file_bam_01()')  # Uncomment to skip
    def test_BBMap_run_local_reads_file_bam_01(self):
        lib_name = 'seven_species_nonuniform_10K.inter.fastq.gz'
        test_reads_file_local = os.path.join('data', 'reads', lib_name)
        test_reads_file_scratch = os.path.join(self.scratch, os.path.basename(test_reads_file_local))
        shutil.copy(test_reads_file_local, test_reads_file_scratch)

        ass_name = 'Thermodesulfo_trim.SPAdes.contigs.fa.gz'
        test_ass_file_local = os.path.join('data', 'assemblies', ass_name)
        test_ass_file_scratch = os.path.join(self.scratch, os.path.basename(test_ass_file_local))
        shutil.copy(test_ass_file_local, test_ass_file_scratch)

        io_params = {
            "in_assembly_paths": [test_ass_file_scratch],
            "in_readslib_path":  test_reads_file_scratch,
            "out_obj_name":     'foo.out'
        }
        run_params = {
            "get_mapped_reads": '0',
            "get_unmapped_reads": '0',
            "get_bam": '1'
        }
        bbtools = self.getImpl()
        res = bbtools.run_BBMap_local(self.ctx, io_params, run_params)[0]
        print('result:')
        pprint(res)
        self.assertIn('output_directory', res)
        self.assertTrue(os.path.exists(res['output_directory']))
        self.assertIn('bam_files', res)
        for file_path in res['bam_files']:
            self.assertTrue(os.path.exists(file_path))
        self.assertIn('run_log', res)
        self.assertTrue(os.path.exists(res['run_log']))
        self.assertIn('run_command', res)
        self.assertIn('bbmap.sh', res['run_command'])


    # HIDE @unittest.skip('skip test_BBTools_get_version()')  # Uncomment to skip
    def test_BBTools_get_version(self):
        version = self.getImpl().bbtools_version(self.ctx)[0]
        ver_file = "/kb/module/bbmap_version"
        with open(ver_file) as f:
            version_from_file = f.read().strip()
        self.assertEqual(version, version_from_file)
