FROM kbase/kbase:sdkbase2.latest
MAINTAINER KBase Developer
# -----------------------------------------
# In this section, you can install any system dependencies required
# to run your App.  For instance, you could place an apt-get update or
# install line here, a git checkout to download code, or run any other
# installation scripts.

# Here we install a python coverage tool and an
# https library that is out of date in the base image.

# update security libraries in the base image
ENV NSLOTS 4

# -----------------------------------------
WORKDIR /kb/module

ENV BBMAP_VERSION=38.91

# add SAMTools (don't need yet)
#RUN apt-get update && apt-get install -y samtools

# needed for python3 base image
#RUN apt-get update && apt-get install -y wget gcc


# install BBTools

RUN BBMAP=BBMap_$BBMAP_VERSION.tar.gz \
    && wget -O $BBMAP https://sourceforge.net/projects/bbmap/files/$BBMAP/download \
    && tar -xf $BBMAP \
    && rm $BBMAP

# build BBTools small C-lib
RUN cd /kb/module/bbmap/jni \
    && make -f makefile.linux

# copy local ref files
RUN mkdir /global
COPY data/rqc_data/global /global

#copy everything in
COPY ./ /kb/module
RUN mkdir -p /kb/module/work
RUN chmod -R a+rw /kb/module


RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
