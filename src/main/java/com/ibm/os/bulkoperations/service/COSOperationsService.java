package com.ibm.os.bulkoperations.service;

import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.DeleteObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.DeleteObjectsResult;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectListing;
import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class COSOperationsService {

    private static final Logger logger = LoggerFactory.getLogger(COSOperationsService.class);

    private final AmazonS3 s3Client;

    @Autowired
    AsyncOperations asyncOperations;

    @Autowired
    public COSOperationsService(AmazonS3 s3Client) {
        this.s3Client = s3Client;

    }

    public void deleteAllInBucketWithPrefix(String bucketName, String prefix) {
        logger.info( String.format( "START : S3Service -> deleteAllInBucketWithPrefix() bucketName : %s & prefix :  %s",bucketName,prefix ));

        ObjectListing listing = s3Client.listObjects( bucketName, prefix );
        long fileCount = 0;
        List<CompletableFuture<Integer>> futures = new ArrayList<CompletableFuture<Integer>>();
        while (listing != null && listing.getObjectSummaries().size()>0) {
            System.out.println("noOfFiles : "+fileCount);
            List<S3ObjectSummary> summaries = listing.getObjectSummaries();
            fileCount += summaries.size();
            futures.add(asyncOperations.deleteFromBucketWithKeys(bucketName, summaries));
            listing = s3Client.listNextBatchOfObjects( listing);
        }
        // if we want to wait for all of them to complete, not sure this is necessary
        // CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        logger.info( String.format( "END : S3Service -> deleteAllInBucketWithPrefix() bucketName : %s & prefix : %s",bucketName,prefix ));

    }
}
