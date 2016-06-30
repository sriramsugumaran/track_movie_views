package com.ram.sri.track.output.aws;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.ram.sri.track.Status;
import com.ram.sri.track.exception.ApplicationConfigurationException;
import com.ram.sri.track.exception.ApplicationExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * This class performs storing data in AWS S3
 */
public class S3Accessor extends AbstractAWSCredentials {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Accessor.class);
    private static final String BUCKET_NAME = "s3bucket-sst67gy";
    private static S3Accessor instance = null;
    private static AmazonS3 client;

    private S3Accessor() throws ApplicationConfigurationException {

        try {
            client = new AmazonS3Client(getCredentials());
            Region usEast1 = Region.getRegion(Regions.US_EAST_1);
            client.setRegion(usEast1);
        } catch (Exception e) {
            LOGGER.error("Unable to configure AWS S3 Client");
            throw new ApplicationConfigurationException(e);
        }
    }

    public static S3Accessor getInstance() throws ApplicationConfigurationException {
        if (instance == null) {
            instance = new S3Accessor();
        }
        return instance;
    }


    public Status uploadFile(String folderPath, File file) throws ApplicationExecutionException {

        Status uploadStatus = Status.FAILURE;

        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Uploading file- " + file.getAbsolutePath() + " to S3 in progress");
        }

        try {
            client.putObject(BUCKET_NAME, folderPath + file.getName(), file);
            uploadStatus = Status.SUCCESS;
        } catch(Exception e) {
            LOGGER.error("Error in uploading file- " + file.getAbsolutePath() + " to S3");
            throw new ApplicationExecutionException(e);
        }

        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Completed uploading file- " + file.getAbsolutePath() + " to S3");
        }

        return uploadStatus;
    }
}
