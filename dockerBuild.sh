#!/bin/sh

version=0.1
echo $version

docker rmi property-sa:$version -f; docker build -t property-sa:$version .