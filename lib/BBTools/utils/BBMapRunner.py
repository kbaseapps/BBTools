import os
import time
import uuid
import zipfile
from pprint import pprint

from BBTools.utils.BBToolsRunner import BBToolsRunner
from installed_clients.KBaseReportClient import KBaseReport
from commandbuilder import build_options
from .file_util import (
    download_assemblies,
    download_assembly,
    download_interleaved_reads,
    upload_interleaved_reads,
    pack_and_upload_folder,
    mkdir_p
)


class BBMapRunner:

    BBMAP_CMD = 'bbmap.sh'

    def __init__(self, callback_url, scratch_dir):
        self.callback_url = callback_url
        self.scratch_dir = scratch_dir
        self._timestamp = str(int(time.time() * 1000))  # used for output and report directory names.
        has_kb_data = os.path.isdir("/kb/data")
        has_data = os.path.isdir("/data")
        has_kb = os.path.isdir("/kb")

        print("/kb/data - {}".format(has_kb_data))
        try:
            print(os.listdir("/kb/data"))
        except:
            print("can't list dir /kb/data")
        print("/data - {}".format(has_data))
        try:
            print(os.listdir("/data"))
        except:
            print("can't list dir /data")
        print("/kb - {}".format(has_kb))
        try:
            print(os.listdir("/kb"))
        except:
            print("can't list dir /kb")

    def run_app(self, io_params, app_params):
        output_dir, run_log, run_command = self._run(io_params, app_params, is_app=True)
        return self._save_output_to_kbase(io_params, app_params, output_dir, run_log, run_command)

    def run_local(self, io_params, app_params):
        output_dir, run_log, run_command = self._run(io_params, app_params, is_app=False)
        file_lookup = self._read_outputfile(os.path.join(output_dir, 'file-list.txt'))
        result = {
            'output_directory': output_dir,
            'run_log': run_log,
            'mapped_reads_paths': None,
            'unmapped_reads_paths': None,
            'bam_paths': None,
            'run_command': run_command
        }
        output_created = False
        if 'mapped_reads_files' in file_lookup:
            result['mapped_reads_paths'] = []
            for file_name in file_lookup['mapped_reads_files'].split(','):
                result['mapped_reads_paths'].append(os.path.join(output_dir, file_name))
            output_created = True
        if 'unmapped_reads_files' in file_lookup:
            result['unmapped_reads_paths'] = []
            for file_name in file_lookup['unmapped_reads_files'].split(','):
                result['unmapped_reads_paths'].append(os.path.join(output_dir, file_name))
            output_created = True
        if 'bam_files' in file_lookup:
            result['bam_paths'] = []
            for file_name in file_lookup['bam_files'].split(','):
                result['bam_paths'].append(os.path.join(output_dir, file_name))
            output_created = True
        if not output_created:
            print('No output generated by BBTools BBMap!')
        return result

    def _run(self, io_params, app_params, is_app=True):
        """
        Does the run part of BBMap. Formats parameters, etc., then sends them off
        to the BBToolsRunner to run.
        At the end, returns the output directory, path to the run log, and the command-line string
        that was run.
        """
        print('Running BBMap. Params=')
        pprint(io_params)
        pprint(app_params)
        self._runtime = str(int(time.time() * 1000))
        output_dir = os.path.join(self.scratch_dir, 'bbmap_output_' + self._timestamp)
        run_log = os.path.join(output_dir, 'run_log.txt')
        options = self._process_app_params_to_cli(io_params, app_params, output_dir, run_log, is_app)
        bbtools = BBToolsRunner(self.scratch_dir)
        bbtools.run(self.BBMAP_CMD, options, log_path=run_log)
        cmd = [self.BBMAP_CMD] + options
        self._create_output_file_list('file-list.txt', output_dir)
        return output_dir, run_log, " ".join(cmd)

    def _create_output_file_list(self, file_list_name, output_dir):
        file_list_path = os.path.join(output_dir, file_list_name)
        mapped_reads_files = []
        unmapped_reads_files = []
        bam_files = []
        
        for name in os.listdir(output_dir):
            if name.endswith('.FASTQ'):
                if name.endswith('-UNMAPPED.FASTQ'):
                    unmapped_reads_files.append(name)
                elif name.endswith('-MAPPED.FASTQ'):
                    mapped_reads_files.append(name)
                else:
                    raise ValueError ("unknown file "+name+" was created as output")
            elif name.endswith('.BAM'):
                bam_files.append(name)

        with open (file_list_path, 'w') as file_list_handle:
            if len(mapped_reads_files) > 0:
                this_line = 'mapped_reads_files'+"\t"+','.join(mapped_reads_files)+"\n"
                file_list_handle.write(this_line)
            if len(unmapped_reads_files) > 0:
                this_line = 'unmapped_reads_files'+"\t"+','.join(unmapped_reads_files)+"\n"
                file_list_handle.write(this_line)
            if len(bam_files) > 0:
                this_line = 'bam_files'+"\t"+','.join(bam_files)+"\n"
                file_list_handle.write(this_line)

        return file_list_path


    def _process_app_params_to_cli(self, io_params, app_params, output_dir, run_log, is_app):
        ''' given the parameters passed into the KBase App, validate them, stage the input
            and create the set of options that will be passed to rqcfilter.sh '''

        available_params = {
            'input_parameter_suite': {'type': 'string'},
            'speed_mode': {'type': 'string',
                           'allowed_values': ['vslow', 'slow', 'default', 'fast']},
            'min_id': {'type': 'float'},
            'kmer_len': {'type': 'int'},
            'max_indel': {'type': 'int'},
            'strict_max_indel': {'type': 'boolean'},
            'subfilter_thresh': {'type': 'int'},
            'delfilter_thresh': {'type': 'int'},
            'require_correct_strand': {'type': 'boolean'},
            'qual_score_mode': {'type': 'string',
                                'allowed_values': ['33', '64']}
        }

        self._validate_file_inputs(io_params, is_app)
        if 'in_assembly_refs' in io_params:
            assembly_files = download_assemblies(
                self.callback_url, io_params['in_assembly_refs'])
        else:
            assembly_files = io_params['in_assembly_paths']
        if 'in_readslib_ref' in io_params:
            reads_file = download_interleaved_reads(
                self.callback_url, io_params['in_readslib_ref'])['files']['fwd']
        else:
            reads_file = io_params['in_readslib_path']

        # maxmem is brought in, possibly as well.
        # it should be removed from the build options list, as it's a special
        # command sent to java to set memory requirements
        #mem = 50
        mem = 15
        if 'maxmem' in app_params:
            try:
                mem = int(app_params['maxmem'])
                if mem < 1:
                    raise ValueError()
                del app_params['maxmem']
            except:
                raise ValueError('The value of maxmem must be an integer > 0.')

        # run BBMap separately for each assembly file target
        for (assembly_i, assembly_file) in enumerate(assembly_files):
            options = build_options(app_params, available_params)

            # setup input/output paths
            options.append('ref={}'.format(assembly_file))
            options.append('nodisk')
            options.append('in={}'.format(reads_file))

            mkdir_p(output_dir)
            if app_params.get('get_bam') and int(app_params['get_bam']) != 0:
                bam_out_file = os.path.join(output_dir, str(assembly_i)+'-'+io_params['out_obj_name']+'.BAM')
                options.append('out={}'.format(bam_out_file))
            if app_params.get('get_mapped_reads') and int(app_params['get_mapped_reads']) != 0:
                mapped_reads_out_file = os.path.join(output_dir, str(assembly_i)+'-'+io_params['out_obj_name']+'-'+'MAPPED'+'.FASTQ')
                options.append('outm={}'.format(mapped_reads_out_file))
            if app_params.get('get_unmapped_reads') and int(app_params['get_unmapped_reads']) != 0:
                unmapped_reads_out_file = os.path.join(output_dir, str(assembly_i)+'-'+io_params['out_obj_name']+'-'+'UNMAPPED'+'.FASTQ')
                options.append('outu={}'.format(unmapped_reads_out_file))

        # hard-code max threads for now
        options.append('t={}'.format('4'))

        # use pigz and unpigz for what are usually huge files
        options.append('pigz=t')
        options.append('unpigz=t')

        # enforce qual=33 if not given
        if app_params.get('qual_score_mode'):
           options.append('qin={}'.format(str(app_params['qual_score_mode'])))
        else:
           options.append('qin={}'.format('33'))

        # add the memory requirement at the end
        options.append('-Xmx{}g'.format(mem))

        # finally, route stderr (a log file) to a file in the output dir
        options = options + ['2>', run_log]
        return options

    def _validate_file_inputs(self, params, is_app):
        if is_app:
            method = 'BBMap App()'
            required_fields = ['in_assembly_refs', 'in_readslib_ref', 'workspace_name', 'out_obj_name']
            for r in required_fields:
                if r not in params:
                    raise ValueError('Error running '+method+': ' + r + ' parameter is required')
        else:
            method = 'BBMap Local()'
            required_fields = ['in_assembly_paths', 'in_readslib_path', 'out_obj_name']
            for r in required_fields:
                if r not in params:
                    raise ValueError('Error running '+method+': ' + r + ' parameter is required')

    def _save_output_to_kbase(self, io_params, app_params, output_dir, run_log, run_command):
        # TODO: insert the run_command into the output log
        #
        # read the output file list
        file_lookup = self._read_outputfile(os.path.join(output_dir, 'file-list.txt'))

        # save the new reads
        mapped_reads_ref = None
        unmapped_reads_ref = None
        objects_created = []
        if 'mapped_reads_files' not in file_lookup:
            print('No mapped reads fastq file found in output.  Not creating any mapped reads objects.')
        else:
            for file_name in file_lookup['mapped_reads_files'].split(','):
                mapped_reads_path = os.path.join(output_dir, file_name)
                mapped_reads_ref = upload_interleaved_reads(
                    self.callback_url,
                    mapped_reads_path,
                    io_params['workspace_name'],
                    file_name+'.reads',
                    io_params.get('in_readslib_ref'))
                objects_created.append({
                    'ref': mapped_reads_ref,
                    'description': 'Mapped reads library'
                })
        if 'unmapped_reads_files' not in file_lookup:
            print('No unmapped reads fastq file found in output.  Not creating any unmapped reads objects.')
        else:
            for file_name in file_lookup['unmapped_reads_files'].split(','):
                unmapped_reads_path = os.path.join(output_dir, file_name)
                unmapped_reads_ref = upload_interleaved_reads(
                    self.callback_url,
                    unmapped_reads_path,
                    io_params['workspace_name'],
                    file_name+'.reads',
                    io_params.get('in_readslib_ref'))
                objects_created.append({
                    'ref': unmapped_reads_ref,
                    'description': 'Unmapped reads library'
                })

        # build the HTML report
        html_zipped = self._build_html_report(io_params.get('in_readslib_ref'), output_dir, file_lookup)
        file_links = self._build_file_report(output_dir, run_log)
        # save the report
        report_params = {
            'message': '',
            'objects_created': objects_created,
            'direct_html_link_index': 0,
            'html_links': [html_zipped],
            'file_links': file_links,
            'report_object_name': 'bbtools_bbmap_report_' + str(uuid.uuid4()),
            'workspace_name': io_params['workspace_name']
        }

        kr = KBaseReport(self.callback_url)
        report_output = kr.create_extended_report(report_params)

        return {'report_name': report_output['name'],
                'report_ref': report_output['ref'],
                'run_command': run_command}

    def _build_file_report(self, output_dir, run_log):
        # list of files = start with everything that's unzipped (or not .fq.gz) in output_dir
        file_list = os.listdir(output_dir)
        result_file = os.path.join(output_dir, 'bbmap_report.zip')
        with zipfile.ZipFile(result_file, 'w', zipfile.ZIP_DEFLATED, allowZip64=True) as report_zip:
            for file_name in file_list:
                if file_name.endswith('.gz') or file_name.endswith('.fastq'):
                    continue
                zipped_file_name = file_name
                report_zip.write(os.path.join(output_dir, file_name), zipped_file_name)
        file_links = [{
            'path': result_file,
            'name': os.path.basename(result_file),
            'label': 'BBMap_report',
            'description': 'BBMap report files'
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
                    tokens = line.split("\t", 1)
                    if len(tokens) != 2:
                        print('bad line in ' + file_path + ' output, skipping: ' + str(line))
                    filedata[tokens[0]] = tokens[1]
        return filedata

    def _build_html_report(self, reads_ref, output_dir, file_lookup):
        html_dir = os.path.join(self.scratch_dir, 'bbmap_report_' + self._timestamp)
        os.makedirs(html_dir)

        # note: we should use a real library, like yattag, to generate the HTML report here
        # this is just a quick hack to get one simple table parsed and displayed
        html = open(os.path.join(html_dir, 'BBMap_report.html'), 'w')
        html.write('<html><head><title>BBMap Report: ' + reads_ref + '</title></head>\n')
        html.write('<body>\n')

        #stats = self._read_outputfile(os.path.join(output_dir, 'filterStats.txt'))
        html.write('  <table style="border: 1px solid black; border-collapse: collapse;">\n')
        tdstyle = 'style="border: 1px solid black; padding: 8px;"'
        #for key in stats.keys():
        #    html.write('   <tr><td ' + tdstyle + '>' + str(key) + '</td>')
        #    html.write(' <td ' + tdstyle + '>' + str(stats[key]) + '</td></tr>\n')

        html.write('  </table>\n')
        html.write('</body>\n')
        html.write('</html>')
        html.close()

        return pack_and_upload_folder(self.callback_url, html_dir, 'BBMap_report.html', 'Summarized report from BBMap')