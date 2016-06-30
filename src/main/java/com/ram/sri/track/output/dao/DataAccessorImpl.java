package com.ram.sri.track.output.dao;

import com.mysql.jdbc.StringUtils;
import com.ram.sri.track.Status;
import com.ram.sri.track.exception.ApplicationExecutionException;
import com.ram.sri.track.input.model.MovieSearch;
import com.ram.sri.track.output.aws.DynamoDBAccessor;
import com.ram.sri.track.output.aws.S3Accessor;
import com.ram.sri.track.output.rest.VimeoAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DataAccessorImpl implements IDataAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessorImpl.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String SUFFIX = "/";
    private DynamoDBAccessor awsDbAccessor;
    private VimeoAccessor vimeoAccessor;
    private S3Accessor s3Accessor;

    @Override
    public Status persist(List<? extends MovieSearch> models) throws ApplicationExecutionException {
        return awsDbAccessor.batchSave(models);
    }

    @Override
    public Long fetchHourTotal(String movieTitle, String timestamp) {
        return awsDbAccessor.fetchHourTotal(movieTitle, timestamp);
    }

    @Override
    public String getViews(String searchTerm) {

        String views = null;
        if (StringUtils.isNullOrEmpty(searchTerm)) {
            LOGGER.info("No search terms received to fetch movie views");
        } else {
            views = vimeoAccessor.getResponse(searchTerm);
        }

        return views;
    }

    @Override
    public File getAllViewsInFile() throws ApplicationExecutionException {
        try {
            return awsDbAccessor.getAllViewsInFile();
        } catch (IOException e) {
            LOGGER.error("Error in retrieving views form database and consolidating into a file");
            throw new ApplicationExecutionException(e);
        }
    }

    @Override
    public Status uploadViewsInFile(File file) throws ApplicationExecutionException {

        Calendar cal = Calendar.getInstance();
        int subFolderNumber = cal.get(Calendar.HOUR);
        String folderPath = DATE_FORMAT.format(cal.getTime()) + SUFFIX + subFolderNumber + SUFFIX;

        return s3Accessor.uploadFile(folderPath, file);
    }

    public DynamoDBAccessor getAwsDbAccessor() {
        return awsDbAccessor;
    }

    public void setAwsDbAccessor(DynamoDBAccessor awsDbAccessor) {
        this.awsDbAccessor = awsDbAccessor;
    }

    public VimeoAccessor getVimeoAccessor() {
        return vimeoAccessor;
    }

    public void setVimeoAccessor(VimeoAccessor vimeoAccessor) {
        this.vimeoAccessor = vimeoAccessor;
    }

    public S3Accessor getS3Accessor() {
        return s3Accessor;
    }

    public void setS3Accessor(S3Accessor s3Accessor) {
        this.s3Accessor = s3Accessor;
    }
}
