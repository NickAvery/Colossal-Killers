#!/bin/sh
cd unicon
./build.sh
if grep ^\#define\ HAVE_LIBGL\ 1$ src/h/auto.h
then
        echo "Think we have libGL, you are good to go"
else
        echo "think we don't have OpenGL, skipping CVE build"
        exit -1
fi
make Unicon
cd bin
DIR=$PWD
echo $DIR
export PATH=$PATH:$DIR
cp iconx ../../cve/bin
cd ../../cve
make
