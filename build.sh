#!/bin/bash -ex

docker build -t "meteogroup/s3mock" "$(dirname "$0")/docker"
