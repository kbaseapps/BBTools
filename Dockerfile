FROM kbase/sdkpython:3.8.10

RUN apt update && apt install -y wget

# TODO what is this for?
ENV NSLOTS=4

WORKDIR /kb/module

# copy everything in, we need the version file
COPY ./ /kb/module


# add SAMTools (don't need yet)
#RUN apt-get update && apt-get install -y samtools

# install BBTools

RUN BBMAP_VERSION=$(cat /kb/module/bbmap_version) \
    && BBMAP=BBMap_$BBMAP_VERSION.tar.gz \
    && wget -O $BBMAP https://sourceforge.net/projects/bbmap/files/$BBMAP/download \
    && tar -xf $BBMAP \
    && rm $BBMAP

# build BBTools small C-lib
RUN cd /kb/module/bbmap/jni \
    && make -f makefile.linux
	
# Per BB, don't use this, removed in later versions. Causing test failures
RUN sed -i 's/jni=t//g' /kb/module/bbmap/rqcfilter2.sh

# copy local ref files
RUN mkdir /global
COPY data/rqc_data/global /global

RUN mkdir -p /kb/module/work
RUN chmod -R a+rw /kb/module


RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
