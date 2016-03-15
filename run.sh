#!/bin/bash -x

docker run --rm -ti \
    -p "${S3NINJA_PORT:-9444}:9444" \
    "${@:2}" "meteogroup/s3mock" ${@:1:1}
