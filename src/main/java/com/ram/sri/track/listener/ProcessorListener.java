package com.ram.sri.track.listener;

import com.ram.sri.track.input.model.MovieSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

public class ProcessorListener implements ItemProcessListener<MovieSearch, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorListener.class);

    @Override
    public void beforeProcess(MovieSearch movieSearch) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Started processing " + movieSearch.getMovieTitle());
        }
    }

    @Override
    public void afterProcess(MovieSearch m, Object o2) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Completed processing " + m.getMovieTitle());
        }
    }

    @Override
    public void onProcessError(MovieSearch movieSearch, Exception e) {

    }
}
