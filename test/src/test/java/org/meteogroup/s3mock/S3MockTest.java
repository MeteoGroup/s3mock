/*
Copyright © 2016 MeteoGroup Deutschland GmbH

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/
package org.meteogroup.s3mock;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class S3MockTest {

    public static final String BUCKET_NAME = "test-bucket";
    public static final String OBJECT_NAME = "test-object";
    public static final String OBJECT_DATA = "object data";
    public static final String OBJECT_DATA_MD5 = "hfMGNWAtwJvYWVem6CosIQ==";

    private AmazonS3Client client;
    private String s3mock;

    @BeforeMethod
    public void setUp() throws Exception {
        s3mock = System.getProperty("s3mock");
        s3mock = s3mock != null ? s3mock : "http://localhost:9444/s3";
        client = new AmazonS3Client(new BasicAWSCredentials("AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"));
        client.setEndpoint(s3mock);
    }

    @Test
    public void create_bucket() throws Exception {

        final Bucket bucket = client.createBucket(BUCKET_NAME);

        assertThat(bucket).isNotNull();
        assertThat(bucket.getName()).isEqualTo(BUCKET_NAME);
    }

    @Test(dependsOnMethods = "create_bucket")
    public void list_buckets() throws Exception {

        final List<Bucket> bucketList = client.listBuckets();

        assertThat(bucketList.stream().map(Bucket::getName).toArray())
                .contains(BUCKET_NAME);
    }

    @Test(dependsOnMethods = "list_buckets")
    public void delete_bucket() throws Exception {

        client.deleteBucket(BUCKET_NAME);

        assertThat(client.listBuckets().stream().map(Bucket::getName).toArray())
                .excludes(BUCKET_NAME);
    }

    @Test(dependsOnMethods = "delete_bucket")
    public void prepare_bucket() throws Exception {

        client.createBucket(BUCKET_NAME);

        assertThat(client.listBuckets().stream().map(Bucket::getName).toArray())
                .contains(BUCKET_NAME);
        assertThat(client.listObjects(BUCKET_NAME).getObjectSummaries()).isEmpty();
    }

    @Test(dependsOnMethods = "prepare_bucket")
    public void upload_object() throws Exception {
        assertThat(client.listObjects(BUCKET_NAME).getObjectSummaries()).isEmpty();

        final PutObjectResult result = client.putObject(BUCKET_NAME, "test-object", new ByteArrayInputStream(OBJECT_DATA.getBytes()), new ObjectMetadata());

        assertThat(result).isNotNull();
        assertThat(result.getContentMd5()).isEqualTo(OBJECT_DATA_MD5);
        assertThat(client.listObjects(BUCKET_NAME).getObjectSummaries()).isNotEmpty();
        assertThat(client.listObjects(BUCKET_NAME).getObjectSummaries().get(0).getKey()).isEqualTo("test-object");
    }

    @Test(dependsOnMethods = "upload_object")
    public void download_object() throws Exception {
        assertThat(client.listObjects(BUCKET_NAME).getObjectSummaries().stream().map(S3ObjectSummary::getKey).toArray())
                .isNotEmpty().contains(OBJECT_NAME);

        final S3Object object = client.getObject(BUCKET_NAME, OBJECT_NAME);

        assertThat(object.getKey()).isEqualTo(OBJECT_NAME);
        assertThat(IOUtils.toString(object.getObjectContent())).isEqualTo(OBJECT_DATA);
    }

    @AfterClass(alwaysRun = false)
    public void tear_down() throws Exception {
        client.deleteBucket(BUCKET_NAME);
    }
}
