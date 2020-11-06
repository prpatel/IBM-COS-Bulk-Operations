
package com.ibm.os.bulkoperations.config;

import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${s3.endpoint}")
    private String endpoint;
    @Value("${s3.location}")
    private String location;
    @Value("${access_key_id}")
    private String access_key_id;
    @Value("${secret_access_key}")
    private String secret_access_key;
    @Value("${bucket_name}")
    public String bucketName;
    @Value("${prefix}")
    public String prefix;

    private final static Logger logger = LoggerFactory.getLogger( S3Config.class );


    @Bean
    public AmazonS3 client() {
        logger.info( "access_key_id : " + access_key_id );

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration( new AwsClientBuilder.EndpointConfiguration( endpoint, location ) )
                .withCredentials( new AWSStaticCredentialsProvider( new BasicAWSCredentials( access_key_id, secret_access_key ) ) )
                .withPathStyleAccessEnabled( true ).build();
    }
}
