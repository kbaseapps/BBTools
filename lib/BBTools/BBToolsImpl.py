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
    VERSION = "0.0.3"
    GIT_URL = "https://github.com/briehl/BBTools"
    GIT_COMMIT_HASH = "f046b23ce8276eec1b388da79312515548637075"

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
           of String, parameter "library" of String, parameter "rna" of type
           "boolean" (A boolean - 0 for false, 1 for true. @range (0, 1)),
           parameter "trimfragadapter" of type "boolean" (A boolean - 0 for
           false, 1 for true. @range (0, 1)), parameter "qtrim" of String,
           parameter "removemouse" of type "boolean" (A boolean - 0 for
           false, 1 for true. @range (0, 1)), parameter "removecat" of type
           "boolean" (A boolean - 0 for false, 1 for true. @range (0, 1)),
           parameter "removedog" of type "boolean" (A boolean - 0 for false,
           1 for true. @range (0, 1)), parameter "removehuman" of type
           "boolean" (A boolean - 0 for false, 1 for true. @range (0, 1)),
           parameter "removemicrobes" of type "boolean" (A boolean - 0 for
           false, 1 for true. @range (0, 1)), parameter "taxlist" of list of
           String, parameter "dedupe" of type "boolean" (A boolean - 0 for
           false, 1 for true. @range (0, 1)), parameter "opticaldupes" of
           type "boolean" (A boolean - 0 for false, 1 for true. @range (0, 1))
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

    def run_RQCFilter_local(self, ctx, params):
        """
        :param params: instance of type "RQCFilterLocalParams" (Parameters
           for local version of RQCFilter. read_library_ref - UPA for the
           read library to filter. -OR- reads_file - path to the reads file
           to filter. Expects an interleaved file, if it's paired end. The
           rest is as above for the App version.) -> structure: parameter
           "read_library_ref" of String, parameter "reads_file" of String,
           parameter "library" of String, parameter "rna" of type "boolean"
           (A boolean - 0 for false, 1 for true. @range (0, 1)), parameter
           "trimfragadapter" of type "boolean" (A boolean - 0 for false, 1
           for true. @range (0, 1)), parameter "qtrim" of String, parameter
           "removemouse" of type "boolean" (A boolean - 0 for false, 1 for
           true. @range (0, 1)), parameter "removecat" of type "boolean" (A
           boolean - 0 for false, 1 for true. @range (0, 1)), parameter
           "removedog" of type "boolean" (A boolean - 0 for false, 1 for
           true. @range (0, 1)), parameter "removehuman" of type "boolean" (A
           boolean - 0 for false, 1 for true. @range (0, 1)), parameter
           "removemicrobes" of type "boolean" (A boolean - 0 for false, 1 for
           true. @range (0, 1)), parameter "taxlist" of list of String,
           parameter "dedupe" of type "boolean" (A boolean - 0 for false, 1
           for true. @range (0, 1)), parameter "opticaldupes" of type
           "boolean" (A boolean - 0 for false, 1 for true. @range (0, 1))
        :returns: instance of type "RQCFilterLocalOutput" (The output from
           the local function version of RQCFilter. output_directory: the
           path to the output directory containing all files generated by
           RQCFilter. filtered_fastq_file: the path to the file (in the
           output directory) containing the filtered FASTQ reads. This will
           likely be compressed, if you need it decompressed, you can use
           DataFileUtil.unpack_file (see that module).) -> structure:
           parameter "output_directory" of String, parameter
           "filtered_fastq_file" of String
        """
        # ctx is the context object
        # return variables are: output
        #BEGIN run_RQCFilter_local
        rqcfilter = RQCFilterRunner(self.callback_url, self.scratch_dir)
        output = rqcfilter.run_local(params)
        #END run_RQCFilter_local

        # At some point might do deeper type checking...
        if not isinstance(output, dict):
            raise ValueError('Method run_RQCFilter_local return value ' +
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
