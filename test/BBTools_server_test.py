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

from ReadsUtils.ReadsUtilsClient import ReadsUtils

from biokbase.workspace.client import Workspace as workspaceService
from BBTools.BBToolsImpl import BBTools
from BBTools.BBToolsServer import MethodContext
from BBTools.authclient import KBaseAuth as _KBaseAuth


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
    def getPairedEndLibInfo(self):
        if hasattr(self.__class__, 'pairedEndLibInfo'):
            return self.__class__.pairedEndLibInfo

        # copy the local test file to the shared scratch space so that the ReadsUtils
        # container can see it.
        test_fastq_file_local = 'data/interleaved.fastq'
        test_fastq_file_scratch = os.path.join(self.scratch, os.path.basename(test_fastq_file_local))
        shutil.copy(test_fastq_file_local, test_fastq_file_scratch)

        # call the ReadsUtils libary to upload the test data to KBase
        ru = ReadsUtils(os.environ['SDK_CALLBACK_URL'])
        paired_end_ref = ru.upload_reads({'fwd_file': test_fastq_file_scratch,
                                          'sequencing_tech': 'artificial reads',
                                          'interleaved': 1, 'wsname': self.getWsName(),
                                          'name': 'test.pe.reads'})['obj_ref']

        # get the object metadata for the new test dataset
        new_obj_info = self.ws.get_object_info_new({'objects': [{'ref': paired_end_ref}]})
        self.__class__.pairedEndLibInfo = new_obj_info[0]
        return new_obj_info[0]


    def getImpl(self):
        return self.__class__.serviceImpl

    def getContext(self):
        return self.__class__.ctx


    def test_basic_app(self):
        # get the test reads library
        lib_info = self.getPairedEndLibInfo()
        print(lib_info)

        params = {'read_library_ref': str(lib_info[6]) + '/' + str(lib_info[0]) + '/' + str(lib_info[4]),
                  'output_workspace_name': self.getWsName(),
                  'output_library_name': 'filtered.reads'
                  }
        bbtools = self.getImpl()
        res = bbtools.run_RQCFilter_app(self.ctx, params)

        print('result:')
        pprint(res)
        pass
