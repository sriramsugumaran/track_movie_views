package com.ram.sri.track.input.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.ram.sri.track.input.model.MovieSearch;

/**
 * This class maps the returned database row to object
 */
public class MovieSearchMapper implements RowMapper<MovieSearch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieSearchMapper.class);

    private static final String MOVIE_TITLE_COLUMN = "movie_title";

    private static final String SEARCH_TERM_COLUMN = "search_term";

    @Override
    public MovieSearch mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        MovieSearch result = new MovieSearch();
        result.setMovieTitle(resultSet.getString(MOVIE_TITLE_COLUMN));
        result.setSearchTerm(resultSet.getString(SEARCH_TERM_COLUMN));
        return result;
    }
}
