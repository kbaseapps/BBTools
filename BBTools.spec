/*

*/
module BBTools {


    /* A boolean - 0 for false, 1 for true.
        @range (0, 1)
    */
    typedef int boolean;

    /*
    Contains all parameters for the RQCFilter program, EXCEPT for the inputs and outputs.
    Those are added specifically by each function. This lets us describe them separately for the
    local function that works mainly against the file system and the app that mainly works against
    the Workspace.

    This doesn't cover all of the 110+ parameters provided by rqcfilter. Those not listed here
    are left as default values, except sketch=f (as that sends data to JGI servers for processing),
    barcodefilter=f, and mapk=13.

    Notes below are taken from the help output from rqcfilter2.sh ver 38.00

    Parameters (format = param name - default - description):
    ---------------------------------------------------------
    library - frag - should be one of 'frag', 'clip', 'lfpe', or 'clrs'.

    Adapter trimming parameters:
    ----------------------------
    trimfragadapter - f - Trim all known Illumina adapter sequences, including TruSeq and Nextera.

    Quality trimming parameters:
    ----------------------------
    qtrim - f - Trim read ends to remove bases with quality below minq. Performed AFTER looking for kmers.
            Values: rl (trim both ends), f (neither end), r (right end only), l (left end only).
    trimq - 10 - Trim quality threshold.  Must also set qtrim for direction, will be ignored if qtrim=f
    maxns - 0 - Reads with more Ns than this will be discarded.
    minavgquality - 5 - (maq) Reads with average quality (before trimming) below this will be discarded.
    minlength - 45 - (ml) Reads shorter than this after trimming will be discarded.  Pairs will be discarded only if both are shorter.
    mlf - 0.333 - (minlengthfraction) Reads shorter than this fraction of original length after trimming will be discarded.


    Mapping parameters (for vertebrate contaminants):
    -------------------------------------------------
    removemouse - f - (mouse) Remove mouse reads via mapping.
    removecat - f - (cat) Remove cat reads via mapping.
    removedog - f - (dog) Remove dog reads via mapping.
    removehuman - f - (human) Remove human reads via mapping.

    Microbial contaminant removal parameters:
    -----------------------------------------
    removemicrobes - f - (microbes) Remove common contaminant microbial reads via mapping, and place them in a separate file.
    taxlist - emptylist - (tax) Remove these taxa from the database before filtering.  Typically, this would be the organism name or NCBI ID, or a comma-delimited list.  Organism names should have underscores instead of spaces, such as Escherichia_coli.

    Filtering parameters (for artificial and microbial contaminants):
    -----------------------------------------------------------------
    rna - f - Remove reads containing RNA-specific artifacts.
    phix - t - Remove reads containing phiX kmers.

    Clumpify parameters:
    --------------------
    clumpify - f - Run clumpify.
    dedupe - f - Remove duplicate reads.
    opticaldupes - f - Remove optical duplicates (Clumpify optical flag).

    Other processing parameters:
    ----------------------------
    khist - f - Set to true to generate a kmer-frequency histogram of the output data. (included in report in the app, as a file in local function)

    Memory requirements (DON'T EXPOSE THESE TO APPS):
    -------------------------------------------------
    maxmem - 50 - Set maximum memory flag for RQCFilter to try to allocate. Should be an integer, in GB.
    */
    typedef structure {
        string library;
        boolean trimfragadapter;

        string qtrim;
        int trimq;
        int maxns;
        int minavgquality;
        int minlength;
        float mlf;

        boolean removemouse;
        boolean removecat;
        boolean removedog;
        boolean removehuman;
        boolean removemicrobes;

        list <string> taxlist;

        boolean rna;
        boolean phix;

        boolean clumpify;
        boolean dedupe;
        boolean opticaldupes;

        boolean khist;

        int maxmem;
    } RQCFilterParams;


    /*
        Parameters for the Narrative App version of RQCFilter.
        read_library_ref - UPA for the read library to filter.
        output_workspace_name - name of the workspace to put the output reads library and report.
        output_library_name - name of the Reads library object produced by the app.
    */
    typedef structure {
        string read_library_ref;
        string output_workspace_name;
        string output_library_name;
    } RQCFilterAppParams;


    typedef structure {
        string report_name;
        string report_ref;
        string run_command;
    } RQCFilterAppOutput;


    /*
        Parameters for local version of RQCFilter.
        read_library_ref - UPA for the read library to filter.
        -OR-
        reads_file - path to the reads file to filter. Expects an interleaved file, if it's paired end.
        If both of the above are given, the read_library_ref takes precedence.
    */
    typedef structure {
        string read_library_ref;
        string reads_file;
    } RQCFilterLocalParams;

    /*
        The output from the local function version of RQCFilter.

        output_directory:
            the path to the output directory containing all files generated by RQCFilter.
        run_log:
            the path to the run log from RQCFilter (i.e. its stderr). This will be a path in the
            output directory, added separately here for convenience.
        filtered_fastq_file:
            the path to the file (in the output directory) containing the filtered FASTQ reads.
            This will likely be compressed, if you need it decompressed, you can use
            DataFileUtil.unpack_file (see that module).
        run_command:
            the string that's run on the command line with all parameters formatted, etc.
    */
    typedef structure {
        string output_directory;
        string run_log;
        string filtered_fastq_file;
        string run_command;
    } RQCFilterLocalOutput;

    funcdef run_RQCFilter_app(RQCFilterAppParams io_params, RQCFilterParams run_params) returns (RQCFilterAppOutput output)
        authentication required;

    funcdef run_RQCFilter_local(RQCFilterLocalParams io_params, RQCFilterParams run_params) returns (RQCFilterLocalOutput output)
        authentication required;

    /*
        Returns the semantic version of the currently installed BBTools. So something like "38.08"
    */
    funcdef bbtools_version() returns (string version);
};
