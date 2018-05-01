import os.path
import time
import uuid
import zipfile
from pprint import pprint

from BBTools.utils.BBToolsRunner import BBToolsRunner
from KBaseReport.KBaseReportClient import KBaseReport
from commandbuilder import build_options
from file_util import (
    download_interleaved_reads,
    upload_interleaved_reads,
    pack_and_upload_folder,
    mkdir_p
)


class RQCFilterRunner:

    RQCFILTER_CMD = 'rqcfilter.sh'

    def __init__(self, callback_url, scratch_dir):
        self.callback_url = callback_url
        self.scratch_dir = scratch_dir

    def run_app(self, io_params, app_params):
        output_dir, run_log = self._run(io_params, app_params, is_app=True)
        return self._save_output_to_kbase(io_params, app_params, output_dir, run_log)

    def run_local(self, io_params, app_params):
        output_dir, run_log = self._run(io_params, app_params, is_app=False)
        file_lookup = self._read_outputfile(os.path.join(output_dir, 'file-list.txt'))
        result = {
            'output_directory': output_dir,
            'run_log': run_log,
            'filtered_fastq_file': None
        }
        if 'filtered_fastq' in file_lookup:
            result['filtered_fastq_file'] = os.path.join(output_dir, file_lookup['filtered_fastq'])
        else:
            print('No filtered fastq file generated by BBTools RQCFilter!')
        return result

    def _run(self, io_params, app_params, is_app=True):
        print('Running RQCFilter. Params=')
        pprint(io_params)
        pprint(app_params)
        output_dir = os.path.join(self.scratch_dir, 'rqcfilter_output_' + str(int(time.time() * 1000)))
        run_log = os.path.join(output_dir, 'run_log.txt')
        options = self._process_app_params_to_cli(io_params, app_params, output_dir, run_log, is_app)
        bbtools = BBToolsRunner(self.scratch_dir)
        bbtools.run(self.RQCFILTER_CMD, options)
        return output_dir, run_log

    def _process_app_params_to_cli(self, io_params, app_params, output_dir, run_log, is_app):
        ''' given the parameters passed into the KBase App, validate them, stage the input
            and create the set of options that will be passed to rqcfilter.sh '''

        available_params = {
            'rna': {'type': 'boolean'},
            'phix': {'type': 'boolean'},
            'khist': {'type': 'boolean'},
            'trimfragadapter': {'type': 'boolean'},
            'removemouse': {'type': 'boolean'},
            'removecat': {'type': 'boolean'},
            'removedog': {'type': 'boolean'},
            'removehuman': {'type': 'boolean'},
            'removemicrobes': {'type': 'boolean'},
            'clumpify': {'type': 'boolean'},
            'dedupe': {'type': 'boolean'},
            'opticaldupes': {'type': 'boolean'},
            'trimq': {'type': 'int'},
            'maxns': {'type': 'int'},
            'minavgquality': {'type': 'int'},
            'minlength': {'type': 'int'},
            'mlf': {'type': 'float'},
            'library': {'type': 'string'},
            'qtrim': {'type': 'string', 'allowed_values': ['rl', 'r', 'l', 'f']},
            'taxlist': {'type': 'list'}
        }

        self._validate_file_inputs(io_params, is_app)
        if 'read_library_ref' in io_params:
            reads_file = download_interleaved_reads(
                self.callback_url, io_params['read_library_ref'])['files']['fwd']
        else:
            reads_file = io_params['reads_file']

        options = build_options(app_params, available_params)

        # setup input/output paths
        options.append('in={}'.format(reads_file))
        options.append('path={}'.format(output_dir))
        mkdir_p(output_dir)

        # used to override invalid barcode in input
        options.append('barcodefilter=f')

        # make sure that the pipeline does not call out to the external sketch servers
        options.append('sketch=f')

        options.append('mapk=13')
        options.append('-Xmx24g')
        # set the reference file locations
        options.append('rqcfilterdata=/data/RQCFilterData')
        # options.append('humanpath=/data/hg19/')
        # options.append('catpath=/data/cat_genome/')
        # options.append('dogpath=/data/dog_genome/')
        # options.append('mousepath=/data/mouse_genome/')
        # options.append('microberef=/data/commonMicrobes/fusedERPBBmasked2.fa.gz')
        # options.append('taxtree=/data/tree.taxtree.gz')

        # missing ability to set mouseCatDogHumanPath

        # finally, route stderr (a log file) to a file in the output dir
        options = options + ['2>', run_log]
        return options

    def _validate_file_inputs(self, params, is_app):
        if is_app:
            required_fields = ['read_library_ref', 'output_workspace_name', 'output_library_name']
            for r in required_fields:
                if r not in params:
                    raise ValueError('Error running RQCFilter App: ' + r + ' parameter is required')
        else:
            if 'read_library_ref' not in params and 'reads_file' not in params:
                raise ValueError('Error running RQCFilter local: either read_library_ref or reads_file is required')

    def _save_output_to_kbase(self, io_params, app_params, output_dir, run_log):
        # read the output file list
        file_lookup = self._read_outputfile(os.path.join(output_dir, 'file-list.txt'))

        # save the new reads
        filtered_reads_ref = None
        objects_created = None
        if 'filtered_fastq' not in file_lookup:
            print('No filtered fastq file found in output! Not creating a filtered reads object.')
        else:
            filtered_fastq_path = os.path.join(output_dir, file_lookup['filtered_fastq'])
            filtered_reads_ref = upload_interleaved_reads(
                self.callback_url,
                filtered_fastq_path,
                io_params['output_workspace_name'],
                io_params['output_library_name'],
                io_params.get('read_library_ref'))
            objects_created = [{
                'ref': filtered_reads_ref,
                'description': 'Filtered reads library'
            }]
        # build the HTML report
        html_zipped = self._build_html_report(io_params.get('read_library_ref'), output_dir, file_lookup)
        file_links = self._build_file_report(output_dir, run_log)
        # save the report
        report_params = {
            'message': '',
            'objects_created': objects_created,
            'direct_html_link_index': 0,
            'html_links': [html_zipped],
            'file_links': file_links,
            'report_object_name': 'bbtools_rqcfilter_report_' + str(uuid.uuid4()),
            'workspace_name': io_params['output_workspace_name']
        }

        kr = KBaseReport(self.callback_url)
        report_output = kr.create_extended_report(report_params)

        return {'report_name': report_output['name'],
                'report_ref': report_output['ref']}

    def _build_file_report(self, output_dir, run_log):
        # list of files = start with everything that's unzipped (or not .fq.gz) in output_dir
        file_list = os.listdir(output_dir)
        result_file = os.path.join(output_dir, 'rqcfilter_report.zip')
        with zipfile.ZipFile(result_file, 'w', zipfile.ZIP_DEFLATED, allowZip64=True) as report_zip:
            for file_name in file_list:
                if file_name.endswith('.gz') or file_name.endswith('.fastq'):
                    continue
                zipped_file_name = file_name
                report_zip.write(os.path.join(output_dir, file_name), zipped_file_name)
        file_links = [{
            'path': result_file,
            'name': os.path.basename(result_file),
            'label': 'RQCFilter_report',
            'description': 'RQCFilter report files'
        }]
        return file_links

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
                        print('bad line in ' + file_path + ' output, skipping: ' + str(line))
                    filedata[tokens[0]] = tokens[1]
        return filedata

    def _build_html_report(self, reads_ref, output_dir, file_lookup):
        html_dir = os.path.join(self.scratch_dir, 'rqcfilter_report')
        os.makedirs(html_dir)

        # note: we should use a real library, like yattag, to generate the HTML report here
        # this is just a quick hack to get one simple table parsed and displayed
        html = open(os.path.join(html_dir, 'report.html'), 'w')
        html.write('<html><head><title>RQCFilter Report: ' + reads_ref + '</title></head>\n')
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

        return pack_and_upload_folder(self.callback_url, html_dir, 'report.html', 'Summarized report from CheckM')
