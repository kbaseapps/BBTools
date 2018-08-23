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

RUN apt-get -y update && apt-get install -y python-dev libffi-dev libssl-dev ca-certificates
RUN pip install cffi ndg-httpsclient pyopenssl==17.03 cryptography==2.0.3 --upgrade \
    && pip install pyasn1 --upgrade \
    && pip install requests --upgrade \
    && pip install 'requests[security]' --upgrade \
    && pip install coverage

# -----------------------------------------

# yattag for HTML rendering from python
RUN pip install yattag

WORKDIR /kb/module

# copy everything in, we need the version file
COPY ./ /kb/module

# install BBTools

RUN BBMAP_VERSION=$(cat /kb/module/bbmap_version) \
    && BBMAP=BBMap_$BBMAP_VERSION.tar.gz \
    && wget -O $BBMAP https://sourceforge.net/projects/bbmap/files/$BBMAP/download \
    && tar -xf $BBMAP \
    && rm $BBMAP

# build BBTools small C-lib
RUN cd /kb/module/bbmap/jni \
    && make -f makefile.linux

# copy local ref files
RUN mkdir /global
COPY data/rqc_data/global /global

RUN mkdir -p /kb/module/work
RUN chmod -R a+rw /kb/module


RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
