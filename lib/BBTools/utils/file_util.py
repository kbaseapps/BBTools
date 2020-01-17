"""
File utility functions.
Fetching data, re-uploading from file, zipping files into a report, etc.
"""
import os
import errno
from installed_clients.ReadsUtilsClient import ReadsUtils
from installed_clients.DataFileUtilClient import DataFileUtil


def download_interleaved_reads(callback_url, reads_upa):
    ru = ReadsUtils(callback_url)
    reads_info = ru.download_reads({
        'read_libraries': [reads_upa],
        'interleaved': 'true',
        'gzipped': None
    })['files'][reads_upa]
    return reads_info


def upload_interleaved_reads(callback_url, reads_file, ws_name, reads_obj_name, source_reads_upa):
    """
    callback_url = as usual.
    reads_file = full path to the reads file to upload
    ws_name = the workspace to use for uploading the reads file
    reads_obj_name = the name of the new reads object to save as
    source_reads = if not None, the source UPA for the original reads file.
    """
    # unfortunately, the ReadsUtils only accepts uncompressed fq files- this should
    # be fixed on the KBase side
    dfu = DataFileUtil(callback_url)
    reads_unpacked = dfu.unpack_file({'file_path': reads_file})['file_path']

    ru = ReadsUtils(callback_url)
    new_reads_upa = ru.upload_reads({
        'fwd_file': reads_unpacked,
        'interleaved': 1,
        'wsname': ws_name,
        'name': reads_obj_name,
        'source_reads_ref': source_reads_upa
    })['obj_ref']
    print('saved ' + str(reads_unpacked) + ' to ' + str(new_reads_upa))
    return new_reads_upa


def pack_and_upload_folder(callback_url, folder_path, zip_file_name, zip_file_description):
    ''' Simple utility for packaging a folder and saving to shock '''
    dfu = DataFileUtil(callback_url)
    output = dfu.file_to_shock({'file_path': folder_path,
                                'make_handle': 0,
                                'pack': 'zip'})
    return {'shock_id': output['shock_id'],
            'name': zip_file_name,
            'description': zip_file_description}


def mkdir_p(path):
    try:
        os.makedirs(path)
    except OSError as e:
        if e.errno == errno.EEXIST and os.path.isdir(path):
            pass
        else:
            raise
