
S3 mock
=======

Docker image providing a S3 mock based on
[S3 ninja](http://s3ninja.net/):

> S3 ninja emulates the S3 API for development and testing purposes.

`test` contains a java test suite showing the use of _S3 mock_ together
with Amazon's [AWS SDK for Java ](https://aws.amazon.com/sdk-for-java/).

To build the image run `./build.sh`

To start the _S3 mock_ in a docker container run `./run.sh`.

By default _S3 ninja_ is bound to port 9444. To select another port set
the `S3NINJA_PORT` environment variable, e.g.:

```bash
S3NINJA_PORT=9444 ./run.sh
```


To run the tests run `mvn -f test test` from the repository root. Per default
tests try to connect to `http://127.0.0.1:9444/s3`. This can be changed be
setting the `s3mock` system property, e.g.:

```bash
mvn -f test test -Ds3mock=http://192.168.99.100:9444/s3
```


To connect to the _S3 mock_ you should use the following credentials:
```json5
apiKey: "AKIAIOSFODNN7EXAMPLE"
secretKey: "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
```

Copyright & Licences
--------------------

*S3 ninja* (http://s3ninja.net) © 2015 scireum GmbH,
licensed under the [MIT License](http://s3ninja.net).

*AWS SDK for Java* (https://aws.amazon.com/sdk-for-java/) © 2010-2014 Amazon.com, Inc.,
licensed under the [Apache License 2.0](http://www.apache.org/licenses/).

All files in this repository are released under the terms of the MIT License:

> Copyright © 2016 MeteoGroup Deutschland GmbH
>
> Permission is hereby granted, free of charge, to any person obtaining a copy
> of this software and associated documentation files (the "Software"), to deal
> in the Software without restriction, including without limitation the rights
> to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
> copies of the Software, and to permit persons to whom the Software is
> furnished to do so, subject to the following conditions:
>
> The above copyright notice and this permission notice shall be included in
> all copies or substantial portions of the Software.
>
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
> IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
> FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
> AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
> LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
> OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
> SOFTWARE.
