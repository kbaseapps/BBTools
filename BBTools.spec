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

        boolean trimfragadapter;
        string qtrim;
        boolean removemouse;
        boolean removecat;
        boolean removedog;
        boolean removehuman;
        boolean removemicrobes;

        list <string> taxlist;

        boolean dedupe;
        boolean opticaldupes;

    } RQCFilterAppParams;


    typedef structure {
        string report_name;
        string report_ref;
    } RQCFilterAppOutput;

    typedef structure {

    } RQCFilterLocalOutput;

    funcdef run_RQCFilter_app(RQCFilterAppParams params) returns (RQCFilterAppOutput output)
        authentication required;

    funcdef run_RQCFilter_local(RQCFilterAppParams params) returns (RQCFilterLocalOutput output)
        authentication required;
};
