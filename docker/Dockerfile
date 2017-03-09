FROM openjdk:8-jre-alpine

# Copyright © 2016 Meteogroup
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is furnished
# to do so.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#
#
# S3 ninja (http://s3ninja.net) © 2015 scireum GmbH,
# licensed under the MIT License (http://s3ninja.net).
#

ENV S3NINJA_HOME=/opt/s3ninja
ENV S3NINJA_WORK=/var/lib/s3ninja

RUN apk --no-cache --update upgrade &&\
    apk --no-cache --update add bash openssl &&\
    mkdir -p "$S3NINJA_HOME" &&\
    cd "$S3NINJA_HOME" &&\
    wget https://oss.sonatype.org/content/groups/public/com/scireum/s3ninja/2.7/s3ninja-2.7-zip.zip &&\
    unzip s3ninja-2.7-zip.zip &&\
    rm s3ninja-2.7-zip.zip

RUN adduser -h "/var/lib/s3ninja" -k /var/empty/ -s /bin/false -D s3ninja s3ninja &&\
    mv "$S3NINJA_HOME/data" "$S3NINJA_HOME/logs" "$S3NINJA_WORK" &&\
    chown -R s3ninja:s3ninja "$S3NINJA_WORK/data" "$S3NINJA_WORK/logs" &&\
    ln -s "$S3NINJA_WORK/data" "$S3NINJA_WORK/logs" \
          "$S3NINJA_HOME/"

COPY s3ninja.sh /

EXPOSE 9444

USER s3ninja
CMD /s3ninja.sh