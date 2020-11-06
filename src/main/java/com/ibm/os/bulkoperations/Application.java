package com.ibm.os.bulkoperations;


import com.ibm.os.bulkoperations.config.S3Config;
import com.ibm.os.bulkoperations.service.COSOperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
    private static final Logger log = LoggerFactory.getLogger( Application.class);

    @Autowired
    private S3Config s3Config;
    @Autowired
    COSOperationsService service;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        log.info( "START : COSBulkOperations deleteAllInBucketWithPrefix()" );
        service.deleteAllInBucketWithPrefix(s3Config.bucketName,s3Config.prefix);
        log.info( "END : COSBulkOperations deleteAllInBucketWithPrefix()" );
        return null;
    }
}
