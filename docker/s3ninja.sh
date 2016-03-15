#!/bin/bash -ex

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

mkdir -p "$S3NINJA_WORK/data/s3"

"$S3NINJA_HOME/sirius.sh" start

tail -n+0 -F /var/lib/s3ninja/logs/application.log /var/lib/s3ninja/logs/stdout.txt

"$S3NINJA_HOME/sirius.sh" stop
