/*

*/
module BBTools {


    /* A boolean - 0 for false, 1 for true.
        @range (0, 1)
    */
    typedef int boolean;

    /*

    */
    typedef structure {
        string read_library_ref;
        string output_workspace_name;
        string output_library_name;

        string library;
        boolean rna;
    } RQCFilterAppParams;


    typedef structure {
        string report_name;
        string report_ref;
    } RQCFilterAppOutput;


    funcdef run_RQCFilter_app(RQCFilterAppParams params) returns (RQCFilterAppOutput output)
        authentication required;

};
