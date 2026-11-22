#!/bin/bash

# data directory is always mounted in the /data

BBMAP_VERSION=$(cat /kb/module/bbmap_version)

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
safe_execute "wget --no-verbose https://portal.nersc.gov/dna/microbial/assembly/bushnell/RQCFilterData.tar" "failed to download reference data!"
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

# Pre-build the human Bloom filter. RQCFilter's human-removal step looks for
# human_genome/ref/index/1/bloom.serial and, if it's missing, rebuilds the filter
# on every run and tries (and fails) to write it back, since /data is read-only
# at job time. /data is writable here, so build it once.
#
# These settings must match what RQCFilter passes to BBMap: build=1, k=<mapk>
# (the wrapper forces mapk=13), bloomk=29, bloomhashes=1, bloomminhits=6.
# A cached filter is loaded without checking its settings, so a mismatch would
# fail silently. The filter's size scales with free heap, so BLOOM_MEM must not
# exceed the -Xmx jobs run with (RQCFilterRunner default: 32), or jobs may OOM
# loading it.
date
BLOOM_MEM=${BLOOM_MEM:-32}
HUMAN_PATH=/data/RQCFilterData/human_genome
BLOOM_FILE=$HUMAN_PATH/ref/index/1/bloom.serial
echo "Building human Bloom filter at $BLOOM_FILE with -Xmx${BLOOM_MEM}g"
rm -f $BLOOM_FILE
/kb/module/bbmap/bbmap.sh path=$HUMAN_PATH build=1 k=13 jni=f \
    bloomfilter bloomk=29 bloomhashes=1 bloomminhits=6 bloomserial forceanalyze \
    -Xmx${BLOOM_MEM}g
bloom_ret=$?
if [ $bloom_ret != 0 ] || ! [ -s $BLOOM_FILE ] ; then
    # Not fatal: RQCFilter still works without the cache, it just rebuilds the
    # filter each run. Remove any partial file so jobs don't load a bad one.
    echo "WARNING: failed to build $BLOOM_FILE (exit code $bloom_ret); jobs will rebuild it each run."
    rm -f $BLOOM_FILE
else
    echo "Built $BLOOM_FILE"
fi

date

echo "Success.  Writing __READY__ file."
touch /data/__READY__
