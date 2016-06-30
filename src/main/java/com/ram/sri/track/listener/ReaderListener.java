package com.ram.sri.track.listener;

import com.ram.sri.track.input.model.MovieSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

public class ReaderListener implements ItemReadListener<MovieSearch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderListener.class);

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(MovieSearch m) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Read movie- " + m.getMovieTitle() + " with search terms- " + m.getSearchTerm());
        }
    }

    @Override
    public void onReadError(Exception ex) {
    }
}
