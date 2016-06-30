package com.ram.sri.track.output;

import com.ram.sri.track.Status;
import com.ram.sri.track.exception.ApplicationExecutionException;
import com.ram.sri.track.input.model.MovieSearch;

import com.ram.sri.track.output.dao.IDataAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * This class delegates writing data to Dynamo Db Mapper to persist
 */
public class MovieViewsWriter implements ItemWriter<MovieSearch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieViewsWriter.class);
    private IDataAccessor dataAccessor;

    @Override
    public void write(List<? extends MovieSearch> models) throws ApplicationExecutionException {

        if(Status.FAILURE == dataAccessor.persist(models)) {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Unable to persist the view details");
            }
        }
    }

    public IDataAccessor getDataAccessor() {
        return dataAccessor;
    }

    public void setDataAccessor(IDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }
}
