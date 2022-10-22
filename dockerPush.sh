#!/bin/sh

version=0.1
echo $version
username=thiepha

docker tag property-sa:$version $username/property-sa:$version
