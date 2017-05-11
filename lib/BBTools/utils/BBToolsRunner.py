import os.path
import subprocess


class BBToolsRunner:

    BBTOOLS_PATH = '/kb/module/bbmap'

    def __init__(self, scratch_dir):
        self.scratch_dir = scratch_dir

    def run(self, command, options):
        ''' options is an array of command-line parameters passed to the RQCFilter App '''
        command = [os.path.join(self.BBTOOLS_PATH, command)] + options

        print('In working directory: ' + ' '.join(command))
        print('Running: ' + ' '.join(command))

        p = subprocess.Popen(command, cwd=self.scratch_dir, shell=False)
        exitCode = p.wait()

        if (exitCode == 0):
            print('Success, exit code was: ' + str(exitCode))
        else:
            raise ValueError('Error running command: ' + ' '.join(command) + '\n' +
                             'Exit Code: ' + str(exitCode))
