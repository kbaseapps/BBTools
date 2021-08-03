#!/bin/bash

# data directory is always mounted in the /data

#BBMAP_VERSION=38.73

check_exists() {
    if ! [ -d $1 ] ; then
        echo "Error initializing reference data; failed on: $1"
        fail=1
    fi
}

safe_execute() {
    cmd=$1
    echo "running $cmd"
    eval $cmd
    ret_code=$?
    if [ $ret_code != 0 ]; then
        echo $2
        exit $ret_code
    fi
}

fail=0

date

# Move to /data - that's got room for the big tar file.
cd /data

# Fetch the monster compilation of reference data that Brian Bushnell set up.
echo "Downloading RQCFilterData from NERSC Portal"
safe_execute "wget -L --no-verbose http://portal.nersc.gov/dna/microbial/assembly/bushnell/RQCFilterData.tar" "failed to download reference data!"
safe_execute "tar -xf RQCFilterData.tar -C /data" "failed to untar reference data!"
safe_execute "rm -f RQCFilterData.tar" "failed to remove reference data!"
check_exists /data/RQCFilterData
if [ $fail -eq 1 ] ; then
    echo "Unable to expand RQCFilterData.tar! Failing."
    exit 1
fi
echo "Done expanding RQCFilterData"

# Now, we need another pull of BBMap, and copy its bundled datasets into the location where
# the RQCFilterData.tar landed.
date
echo "Fetching BBMap $BBMAP_VERSION"
BBMAP=BBMap_$BBMAP_VERSION.tar.gz
safe_execute "wget -O $BBMAP https://sourceforge.net/projects/bbmap/files/$BBMAP/download" "failed to download $BBMAP"
safe_execute "tar -xf $BBMAP -C /data" "failed to expand $BBMAP"
check_exists /data/bbmap
if [ $fail -eq 1 ] ; then
    echo "Unable to expand $BBMAP! Failing."
    exit 1
fi
echo "Copying BBMap resources data"
safe_execute "cp /data/bbmap/resources/* /data/RQCFilterData/" "failed to move BBMap data!"
echo "Cleaning up"
rm $BBMAP
rm -rf /data/bbmap

date

echo "Success.  Writing __READY__ file."
touch /data/__READY__
