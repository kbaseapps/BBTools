import os
import subprocess


class BBToolsRunner:

    BBTOOLS_PATH = '/kb/module/bbmap'

    def __init__(self, scratch_dir):
        self.scratch_dir = scratch_dir

    def run(self, command, options, log_path=None):
        """
        command: string, the BBTools command to run
        options: list, command-line parameters to pass to the BBTools app
        log_path: string, the path to the standard generated log file. If not None, this will
                  get dumped to stdout (and the SDK logs) after the run.
        """
        command = [os.path.join(self.BBTOOLS_PATH, command)] + options

        print('In working directory: ' + ' '.join(command))
        print('Running: ' + ' '.join(command))

        p = subprocess.Popen(command, cwd=self.scratch_dir, shell=False)
        exitCode = p.wait()

        if log_path is not None:
            if not os.path.exists(log_path):
                print('Unable to print log file! File "{}" does not exist!'.format(log_path))
            else:
                print('==================== RUN LOG ====================')
                with open(log_path, 'r') as log_file:
                    print(log_file.read(), end="")
                print('==================== END LOG ====================')

        if (exitCode == 0):
            print('Success, exit code was: ' + str(exitCode))
        else:
            raise ValueError('Error running command: ' + ' '.join(command) + '\n' +
                             'Exit Code: ' + str(exitCode))
