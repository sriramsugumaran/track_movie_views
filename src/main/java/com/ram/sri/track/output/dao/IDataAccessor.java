package com.ram.sri.track.output.dao;

import com.ram.sri.track.Status;
import com.ram.sri.track.exception.ApplicationExecutionException;
import com.ram.sri.track.input.model.MovieSearch;

import java.io.File;
import java.util.List;

public interface IDataAccessor {

    /**
     * Persists the given models to the database
     * @param domains
     * @return
     * @throws ApplicationExecutionException
     */
    Status persist(List<? extends MovieSearch> models) throws ApplicationExecutionException;

    /**
     * Retrives the total views for a given movie title & time
     *
     * @param movieTitle
     * @param timestamp
     * @return
     */
    Long fetchHourTotal(String movieTitle, String timestamp);

    /**
     * Retrives the Views in JSON format from the web service
     * @param searchTerm
     * @return
     */
    String getViews(String searchTerm);

    /**
     * Retrieves all views from the database
     * @return
     * @throws ApplicationExecutionException
     */
    File getAllViewsInFile() throws ApplicationExecutionException;

    /**
     * Uploads the file content to the storage server
     * @param file
     * @return
     * @throws ApplicationExecutionException
     */
    Status uploadViewsInFile(File file) throws ApplicationExecutionException;
}
