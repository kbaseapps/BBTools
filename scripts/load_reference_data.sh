#!/bin/bash

# data directory is always mounted in the /data

BBMAP_VERSION=38.00

check_exists() {
    if ! [ -d $1 ] ; then
        echo "Error initializing reference data; failed on: $1"
        fail=1
    fi
}

# cd /data
fail=0

date

# Fetch the monster compilation of reference data that Brian Bushnell set up.
echo "Downloading RQCFilterData from NERSC Portal"
# wget --no-verbose http://portal.nersc.gov/dna/microbial/assembly/bushnell/RQCFilterData.tar
tar -xf RQCFilterData.tar -C /data
rm -f RQCFilterData.tar
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
wget -O $BBMAP https://sourceforge.net/projects/bbmap/files/$BBMAP/download
tar -xf $BBMAP -C /data
check_exists /data/bbmap
if [ $fail -eq 1 ] ; then
    echo "Unable to expand $BBMAP! Failing."
    exit 1
fi
echo "Copying BBMap resources data"
cp /data/bbmap/resources/* /data/RQCFilterData/
echo "Cleaning up"
rm $BBMAP
rm -rf /data/bbmap

date

echo "Success.  Writing __READY__ file."
touch /data/__READY__
