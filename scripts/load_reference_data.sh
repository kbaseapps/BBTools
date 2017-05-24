#!/bin/bash

# data directory is always mounted in the /data


get_file () {
    cd /data
    echo "downloading: ftp://dtn.chicago.kbase.us/BBdata/$1"
    wget --no-verbose ftp://dtn.chicago.kbase.us/BBdata/$1
    tar -xf $1 -C /data
    rm -f $1
}
check_exists() {
    if ! [ -d "/data/$(basename $1)" ] ; then
        echo "Error initializing reference data; failed on: $1"
        fail=1
    fi
}

cd /data
fail=0

date
get_file 'commonMicrobes.tar'
mv global/projectb/sandbox/gaag/bbtools/commonMicrobes .
check_exists 'global'
if [ $fail -eq 1 ] ; then
    exit 1
fi

# date
# get_file 'cat.tar'
# check_exists 'cat_genome'
# if [ $fail -eq 1 ] ; then
#     exit 1
# fi

# date
# get_file 'dog.tar'
# check_exists 'dog_genome'
# if [ $fail -eq 1 ] ; then
#     exit 1
# fi

# date
# get_file 'hg19.tar'
# check_exists 'hg194'
# if [ $fail -eq 1 ] ; then
#     exit 1
# fi

# date
# get_file 'mouse.tar'
# check_exists 'mouse_genome'
# if [ $fail -eq 1 ] ; then
#     exit 1
# fi

# date
# get_file 'mousecatdoghuman.tar'
# check_exists 'mousecatdoghuman'
# if [ $fail -eq 1 ] ; then
#     exit 1
# fi

# date

#echo $fail
#if [ $fail -eq 0 ] ; then
#    # touching this file indicates success
#    echo "Success.  Writing __READY__ file."
#    touch /data/__READY__
#fi
