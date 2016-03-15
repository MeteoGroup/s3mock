#!/bin/bash -ex

TIMESTAMP="$(date '+%Y-%m-%d')"

docker build -t "meteogroup/s3mock" "$(dirname "$0")/docker"
