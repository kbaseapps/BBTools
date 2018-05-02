
# BBTools
---

This module makes [BBTools](https://jgi.doe.gov/data-and-tools/bbtools/) available in KBase.  

It currently (5/2/2018) only exposes RQCFilter for use as either a standalone tool or as part of other pipelines, but other tools (BBMap, BBDuk, etc.) will be available over time.

## Usage
RQCFilter can be run either as an App from the Narrative Interface, or as a local function from other KBase Apps. Along with the configured parameters, it always uses the following set of arguments:
```
-Xmx24g barcodefilter=f sketch=f mapk=13
```
And a provided set of JGI Reference Data, kindly provided by Brian Bushnell.

To run it locally, first install this module with:
```
kb-sdk install BBTools
```
Then you can run `run_RQCFilter_local` with the following steps (Python below, Java and Perl will differ slightly).

Note that not all RQCFilter parameters are available right now. See [BBTools.spec](https://github.com/briehl/BBTools/blob/master/BBTools.spec#L12-L68) for a list.

```
# instantiate the client
from BBTools.BBToolsClient import BBTools
bbtools = BBTools(callback_url)   # see the SDK docs about finding the callback_url.

# set up input files
rqc_filter_input = {
    "reads_file": "/path/to/interleaved/fastq"
}
# or, if you want to use a KBase Workspace UPA for your reads object:
rqc_filter_input = {
    "reads_library_ref": "xx/yy/zz"
}

# set up parameters (example below, there are many more options, see BBTools.spec)
rqc_filter_params = {
    "qtrim": "rl",
    "maxns": 3,
    "minlength": 40
}

# run the local RQCFilter function
result = bbtools.run_RQCFilter_local(rqc_filter_input, rqc_filter_params)
```

The result of an RQCFilter run is a structure with the following keys:
* output_directory - the absolute path to the directory containing all output from the RQCFilter run.
* run_log - the absolute path to the generated log from RQCFilter (what's output to stderr)
* filtered_fastq_file - the absolute path to the generated fastq file that has been filtered according to the user's criteria.

All of the output directory info should be available from your SDK module's file system.

# How to Update
There's a few caveats for updating the version of BBTools being used here. Here's the steps to follow.
1. Update Dockerfile.  
The version downloaded is embedded in the `Dockerfile` for this repo, line 29. Bump that version to whatever you need.
2. Update App Spec.  
The version is also added to `ui/narrative/methods/RQCFilter/display.yaml`, line 4. Update that there to display the version of BBTools being run.
3. Update kbase.yml  
The `module-version` in `kbase.yml` should also be bumped. It follows semantic versioning, so if you're just bumping the BBTools version, then a patch upgrade is most appropriate.
4. (optional) Update reference data.  
If the reference data changes - either in the large tar file hosted at NERSC, or what's packaged with bbmap, this gets slightly more complex. The file `scripts/load_reference_data.sh` works outside of the module compile process, so it maintains its own version of things. There's a version of BBTools embedded there as well, so that will need to change if the bundled data is updated. Finally, you'll have to increase the value of the `data-version` line in `kbase.yml` for the reference data to be re-downloaded.
