package com.ram.sri.track.output.aws;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.ram.sri.track.Status;
import com.ram.sri.track.exception.ApplicationConfigurationException;
import com.ram.sri.track.exception.ApplicationExecutionException;
import com.ram.sri.track.input.model.MovieSearch;
import com.ram.sri.track.output.domain.DDBTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class performs database related calls with Dynamo DB database
 */
public class DynamoDBAccessor extends AbstractAWSCredentials {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBAccessor.class);
    private static DynamoDBAccessor instance = null;
    private static AmazonDynamoDBClient client;
    private static DynamoDBMapper dbmapper;

    private DynamoDBAccessor() throws ApplicationConfigurationException {
        try {
            client = new AmazonDynamoDBClient(getCredentials());
            dbmapper = new DynamoDBMapper(client);

        } catch (Exception e) {
            LOGGER.error("Unable to configure AWS DynamoDB Client");
            throw new ApplicationConfigurationException(e);
        }
    }

    public static DynamoDBAccessor getInstance() throws ApplicationConfigurationException {
        if (instance == null) {
            instance = new DynamoDBAccessor();
        }
        return instance;
    }

    public Status batchSave(List<? extends MovieSearch> models) throws ApplicationExecutionException {

        Status status = Status.FAILURE;
        List<DDBTable> domains = convertModelToDomain(models);

        List<DynamoDBMapper.FailedBatch> failedBatches = null;

        try {
            failedBatches = dbmapper.batchSave(domains);
            status = Status.SUCCESS;
        }catch(Exception e) {
            LOGGER.error("Error in persisting data");
            throw new ApplicationExecutionException(e);
        }

        if (failedBatches != null && !failedBatches.isEmpty()) {
            for (DynamoDBMapper.FailedBatch batch : failedBatches) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Failed to persist data " + batch.getException());
                }
            }
        }

        return status;
    }

    public Long fetchHourTotal(String movieTitle, String timestamp) {

        Long hourTotal = null;
        DDBTable existingDBData = dbmapper.load(DDBTable.class, movieTitle, timestamp);
        if (existingDBData != null) {
            hourTotal = existingDBData.getHourTotal();
        }

        return hourTotal;
    }

    public File getAllViewsInFile() throws IOException {

        List<DDBTable> scanResult = dbmapper.scan(DDBTable.class, new DynamoDBScanExpression());

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fetched all views from the database");
        }

        File file = File.createTempFile("consolidated_views", ".json");

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Temporary file to hold consolidated views is created at location- " + file.toString());
        }

        Writer writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
        PrintWriter out = new PrintWriter(writer);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Appending views to the temp file in progress");
        }

        try {
            for (DDBTable table : scanResult) {
                out.print(table.getViews());
                out.flush();
                writer.flush();
            }
        } finally {
            writer.close();
            out.close();

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Completed writing all views to file");
            }
        }

        return file;
    }

    private List<DDBTable> convertModelToDomain(List<? extends MovieSearch> models) {
        if (models != null && models.size() > 0) {
            List<DDBTable> domains = new ArrayList<DDBTable>();

            for (MovieSearch m : models) {
                DDBTable dbrecord = new DDBTable();
                dbrecord.setTitle(m.getMovieTitle());
                dbrecord.setSearchTerm(m.getSearchTerm());
                dbrecord.setTimestamp(m.getTimestamp());
                dbrecord.setNumViews(m.getNumViews());
                dbrecord.setHourTotal(m.getHourTotal());
                dbrecord.setDeltaLastHour(m.getDeltaLastHour());
                dbrecord.setViews(m.getViews());
                domains.add(dbrecord);
            }

            return domains;
        } else
            return null;
    }


}
