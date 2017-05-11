# -*- coding: utf-8 -*-
#BEGIN_HEADER
import os

from BBTools.utils.RQCFilterRunner import RQCFilterRunner
#END_HEADER


class BBTools:
    '''
    Module Name:
    BBTools

    Module Description:
    
    '''

    ######## WARNING FOR GEVENT USERS ####### noqa
    # Since asynchronous IO can lead to methods - even the same method -
    # interrupting each other, you must be *very* careful when using global
    # state. A method could easily clobber the state set by another while
    # the latter method is running.
    ######################################### noqa
    VERSION = "0.0.1"
    GIT_URL = ""
    GIT_COMMIT_HASH = ""

    #BEGIN_CLASS_HEADER
    #END_CLASS_HEADER

    # config contains contents of config file in a hash or None if it couldn't
    # be found
    def __init__(self, config):
        #BEGIN_CONSTRUCTOR
        self.scratch_dir = os.path.abspath(config['scratch'])
        self.callback_url = os.environ['SDK_CALLBACK_URL']
        #END_CONSTRUCTOR
        pass


    def run_RQCFilter_app(self, ctx, params):
        """
        :param params: instance of type "RQCFilterAppParams" -> structure:
           parameter "read_library_ref" of String, parameter
           "output_workspace_name" of String, parameter "output_library_name"
           of String
        :returns: instance of type "RQCFilterAppOutput" -> structure:
           parameter "report_name" of String, parameter "report_ref" of String
        """
        # ctx is the context object
        # return variables are: output
        #BEGIN run_RQCFilter_app
        rqcfilter = RQCFilterRunner(self.callback_url, self.scratch_dir)
        output = rqcfilter.run_app(params)
        #END run_RQCFilter_app

        # At some point might do deeper type checking...
        if not isinstance(output, dict):
            raise ValueError('Method run_RQCFilter_app return value ' +
                             'output is not type dict as required.')
        # return the results
        return [output]
    def status(self, ctx):
        #BEGIN_STATUS
        returnVal = {'state': "OK",
                     'message': "",
                     'version': self.VERSION,
                     'git_url': self.GIT_URL,
                     'git_commit_hash': self.GIT_COMMIT_HASH}
        #END_STATUS
        return [returnVal]
