import re
import subprocess
import os

REFORMAT_PATH = "/kb/module/bbmap/reformat.sh"


class MemEstimatorRunner(object):
    def __init__(self, params):
        self.file = params.get('file')
        self.file2 = params.get('file2')
        self.check_params()

    def check_params(self):
        if not self.file:
            raise ValueError('Parameter "file" must be present!')
        if not os.path.exists(self.file) and not os.path.isfile(self.file):
            raise ValueError('The file {} does not seem to exist, or is a directory'.format(self.file))
        if self.file2:
            if not os.path.exists(self.file2) and not os.path.isfile(self.file2):
                raise ValueError('The file {} does not seem to exist, or is a directory'.format(self.file2))
        if self.file == self.file2:
            raise ValueError("If two files are present, they must be different.")

    def run(self):
        # use reformat.sh from bbtools to calculate unique 31mers
        # cmd="./bbmap/reformat.sh in=" + reads + " cardinality"
        cmd = [REFORMAT_PATH, "in="+self.file]
        if self.file2:
            cmd.append("in2="+self.file2)
        cmd.append("cardinality")
        process = subprocess.Popen(cmd, stderr=subprocess.PIPE)
        output = str(process.communicate()[1])

        print(output)
        m = re.search(r'Unique 31-mers:\s*(\d+)', output)
        if m is not None:
            uniq_kmers = m.group(1)
        else:
            raise ValueError("Count of unique 31-mers not found. Run result:\n{}".format(output))

        # This is the formula to estimate ram required (in gigs)
        # Gb ~ 6e-8(N) where N is unique 36mers
        # the constant was calculated empirically from real MetaSpades.py runs
        ram_in_gigs = float(uniq_kmers) * .00000006

        # print ("Estimated RAM %s Gibabytes" % (ram_in_gigs))

        # # if ram requirements greater than some number (e.g. 1 Terabyte) then don't run metaspades
        # # because job may be too large for even the large memory node.
        # if ram_in_gigs > limit:
        #     print ("Job requires too much RAM (>" + str(limit) + "). [Estimate was {0:.2f}G]".format(ram_in_gigs))
        #     sys.exit(0)
        return ram_in_gigs