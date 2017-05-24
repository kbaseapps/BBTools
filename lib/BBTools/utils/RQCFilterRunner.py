import os.path
import time
import uuid

from pprint import pprint

from BBTools.utils.BBToolsRunner import BBToolsRunner

from ReadsUtils.ReadsUtilsClient import ReadsUtils
from DataFileUtil.DataFileUtilClient import DataFileUtil
from KBaseReport.KBaseReportClient import KBaseReport


class RQCFilterRunner:

    RQCFILTER_CMD = 'rqcfilter.sh'

    def __init__(self, callback_url, scratch_dir):
        self.callback_url = callback_url
        self.scratch_dir = scratch_dir

    def run_app(self, params):
        ''' entrypoint for running the entire App '''
        print('Running RQCFilter App.  Params=')
        pprint(params)
        output_dir = os.path.join(self.scratch_dir, 'rqcfilter_output_' + str(int(time.time() * 1000)))
        options = self._process_app_params_to_cli(params, output_dir)
        bbtools = BBToolsRunner(self.scratch_dir)
        bbtools.run(self.RQCFILTER_CMD, options)
        return self._save_output_to_kbase(params, output_dir)


    def _process_boolean_parameter(self, params, param_name, options, opt_name=None):
        ''' looks for params[param_name], if set, set options[opt_name] appropriately '''
        if not opt_name:
            opt_name = param_name
        if param_name in params and params[param_name]:
            value = str(params[param_name])
            if value in ['1', 't']:
                options.append(opt_name + '=t')
            elif value in ['0', 'f']:
                options.append(opt_name + '=f')
            else:
                print('WARNING: ignoring parameter ' + param_name + ', was set to "' + value + '", but' +
                      'must be: 0 | 1 | "t" | "f", so will be ignored')


    def _process_app_params_to_cli(self, params, output_dir):
        ''' given the parameters passed into the KBase App, validate them, stage the input
            and create the set of options that will be passed to rqcfilter.sh '''

        self._validate(params)
        reads_info = self._stage_input(params)

        options = []

        # setup input/output paths
        options.append('in=' + str(reads_info['files']['fwd']))
        options.append('path=' + str(output_dir))

        # parse user specified options

        if 'library' in params and params['library']:
            options.append('library=' + str(params['library']))

        self._process_boolean_parameter(params, 'rna', options)

        self._process_boolean_parameter(params, 'trimfragadapter', options)

        if 'qtrim' in params and params['qtrim']:
            if str(params['qtrim']) in ['rl', 'r', 'l', 'f']:
                options.append('qtrim=' + str(params['qtrim']))

        self._process_boolean_parameter(params, 'removemouse', options)
        self._process_boolean_parameter(params, 'removecat', options)
        self._process_boolean_parameter(params, 'removedog', options)
        self._process_boolean_parameter(params, 'removehuman', options)
        self._process_boolean_parameter(params, 'removemicrobes', options)

        self._process_boolean_parameter(params, 'dedupe', options)
        self._process_boolean_parameter(params, 'opticaldupes', options)

        if 'taxlist' in params and params['taxlist']:
            formatted_list = []
            for taxa_name in params['taxlist']:
                formatted_list.append(taxa_name.strip().replace(' ', '_'))
            options.append('tax_list=' + ','.join(formatted_list))

        # used to override invalid barcode in input
        options.append('barcodefilter=f')

        # make sure that the pipeline does not call out to the external sketch servers
        options.append('sketch=f')

        # set the reference file locations
        options.append('humanpath=/data/hg19/')
        options.append('catpath=/data/cat_genome/')
        options.append('dogpath=/data/dog_genome/')
        options.append('mousepath=/data/mouse_genome/')
        options.append('microberef=/data/commonMicrobes/')

        # missing ability to set mouseCatDogHumanPath

        return options


    def _validate(self, params):
        required_fields = ['read_library_ref', 'output_workspace_name', 'output_library_name']
        for r in required_fields:
            if r not in params:
                raise ValueError('Error running RQCFilter App: ' + r + ' parameter is required')


    def _stage_input(self, params):
        ru = ReadsUtils(self.callback_url)
        reads_info = ru.download_reads({'read_libraries': [params['read_library_ref']],
                                        'interleaved': 'true',
                                        'gzipped': None
                                        })['files'][params['read_library_ref']]
        return reads_info


    def _save_output_to_kbase(self, params, output_dir):

        # read the output file list
        file_lookup = self._read_outputfile(os.path.join(output_dir, 'file-list.txt'))

        # save the new reads
        filtered_reads_ref = self._save_reads_to_kbase(params, output_dir, file_lookup)

        # build the HTML report
        html_zipped = self._build_html_report(params, output_dir, file_lookup)

        # loop over the other available files, select some to package in an output bundle
        # TODO: implement and pick which files to save

        # save the report
        report_params = {'message': '',
                         'objects_created': [{'ref': filtered_reads_ref, 'description': 'Filtered reads library.'}],
                         'direct_html_link_index': 0,
                         'html_links': [html_zipped],
                         #'file_links': output_packages,
                         'report_object_name': 'bbtools_rqcfilter_report_' + str(uuid.uuid4()),
                         'workspace_name': params['output_workspace_name']
                         }

        kr = KBaseReport(self.callback_url)
        report_output = kr.create_extended_report(report_params)

        return {'report_name': report_output['name'],
                'report_ref': report_output['ref']}


    def _read_outputfile(self, file_path):
        filedata = {}
        with open(file_path) as f:
            lines = [l.strip() for l in f.readlines()]
            for line in lines:
                if line:
                    if line.startswith('#'):
                        continue
                    tokens = line.split('=', 1)
                    if len(tokens) != 2:
                        print('bad line in file-list.txt output, skipping: ' + str(line))
                    filedata[tokens[0]] = tokens[1]
        return filedata


    def _save_reads_to_kbase(self, params, output_dir, file_lookup):
        filtered_reads_ref = None
        if 'filtered_fastq' in file_lookup:
            # unfortunately, the ReadsUtils only accepts uncompressed fq files- this should
            # be fixed on the KBase side
            filtered_fastq = os.path.join(output_dir, file_lookup['filtered_fastq'])
            dfu = DataFileUtil(self.callback_url)
            filtered_fastq_unpacked = dfu.unpack_file({'file_path': filtered_fastq})['file_path']

            ru = ReadsUtils(self.callback_url)
            filtered_reads_ref = ru.upload_reads({'fwd_file': filtered_fastq_unpacked,
                                                  'interleaved': 1,
                                                  'wsname': params['output_workspace_name'],
                                                  'name': params['output_library_name'],
                                                  'source_reads_ref': params['read_library_ref']
                                                  })['obj_ref']
            print('saved ' + str(filtered_fastq) + ' to ' + str(filtered_reads_ref))
        else:
            print('No filtered fastq file found!')
        return filtered_reads_ref


    def _build_html_report(self, params, output_dir, file_lookup):
        html_dir = os.path.join(self.scratch_dir, 'rqcfilter_report')
        os.makedirs(html_dir)

        # note: we should use a real library, like yattag, to generate the HTML report here
        # this is just a quick hack to get one simple table parsed and displayed
        html = open(os.path.join(html_dir, 'report.html'), 'w')
        html.write('<html><head><title>RQCFilter Report: ' + str(params['read_library_ref']) + '</title></head>\n')
        html.write('<body>\n')

        stats = self._read_outputfile(os.path.join(output_dir, 'filterStats.txt'))
        html.write('  <table style="border: 1px solid black; border-collapse: collapse;">\n')
        tdstyle = 'style="border: 1px solid black; padding: 8px;"'
        for key in stats.keys():
            html.write('   <tr><td ' + tdstyle + '>' + str(key) + '</td>')
            html.write(' <td ' + tdstyle + '>' + str(stats[key]) + '</td></tr>\n')

        html.write('  </table>\n')
        html.write('</body>\n')
        html.write('</html>')
        html.close()

        return self._package_folder(html_dir, 'report.html', 'Summarized report from CheckM')


    def _package_folder(self, folder_path, zip_file_name, zip_file_description):
        ''' Simple utility for packaging a folder and saving to shock '''
        dfu = DataFileUtil(self.callback_url)
        output = dfu.file_to_shock({'file_path': folder_path,
                                    'make_handle': 0,
                                    'pack': 'zip'})
        return {'shock_id': output['shock_id'],
                'name': zip_file_name,
                'description': zip_file_description}
