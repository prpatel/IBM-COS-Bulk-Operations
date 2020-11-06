package com.ibm.os.bulkoperations.service;

import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.model.DeleteObjectsRequest;
import com.ibm.cloud.objectstorage.services.s3.model.DeleteObjectsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncOperations {

    private static final Logger logger = LoggerFactory.getLogger(AsyncOperations.class);

    private final AmazonS3 s3Client;

    @Autowired
    public AsyncOperations(AmazonS3 s3Client) {
        this.s3Client = s3Client;

    }

    @Async
    public CompletableFuture<Integer> deleteFromBucketWithKeys(String bucketName, List keys) {
        logger.info( String.format( "START : S3Service -> deleteFromBucketWithKeys() bucketName : %s ", bucketName ));
        DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
                .withKeys(keys)
                .withQuiet(false);

        DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
        int successfulDeletes = delObjRes.getDeletedObjects().size();
        logger.info( String.format( "START : S3Service -> deleteFromBucketWithKeys() deleted : %s ", successfulDeletes ));
        return  CompletableFuture.completedFuture(Integer.valueOf(successfulDeletes));
    }
}
