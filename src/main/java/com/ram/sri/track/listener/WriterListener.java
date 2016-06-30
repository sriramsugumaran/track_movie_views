package com.ram.sri.track.listener;

import com.ram.sri.track.input.model.MovieSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class WriterListener implements ItemWriteListener<MovieSearch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterListener.class);

    @Override
    public void onWriteError(Exception e, List list) {
    }

    @Override
    public void beforeWrite(List<? extends MovieSearch> list) {
        for (MovieSearch m : list) {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Started saving data for title- " + m.getMovieTitle() + " been saved");
            }
        }
    }

    @Override
    public void afterWrite(List<? extends MovieSearch> movielist) {

        for (MovieSearch m : movielist) {
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info("Data for title- " + m.getMovieTitle() + " been saved");
            }
        }
    }
}
