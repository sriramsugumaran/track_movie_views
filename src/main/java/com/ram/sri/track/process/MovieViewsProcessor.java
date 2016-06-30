package com.ram.sri.track.process;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ram.sri.track.input.model.Datum;
import com.ram.sri.track.input.model.MovieSearch;
import com.ram.sri.track.input.model.RootObject;
import com.ram.sri.track.input.model.Stats;
import com.ram.sri.track.output.dao.IDataAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * This class computes the various fields that will be presisted in Dynamo DB
 */
public class MovieViewsProcessor implements ItemProcessor<MovieSearch, MovieSearch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieViewsProcessor.class);
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    private static final String SOURCE = "Vimeo";
    private static final String SPACE = " ";
    private IDataAccessor dataAccessor;

    @Override
    public MovieSearch process(MovieSearch movieSearch) throws Exception {

        movieSearch = computeDeltaHour(movieSearch);
        movieSearch = updateMovieSearchWithViews(dataAccessor.getViews(movieSearch.getSearchTerm()), movieSearch);
        movieSearch.setSource(SOURCE);
        return movieSearch;
    }

    protected MovieSearch computeDeltaHour(MovieSearch movieSearch) {

        Calendar cal = Calendar.getInstance();
        int currentMinute = cal.get(Calendar.HOUR);
        int previousMinute = currentMinute - 1;
        String searchTimestamp = FORMATTER.format(cal.getTime()) + SPACE + previousMinute;

        Long prevHourTotal = dataAccessor.fetchHourTotal(movieSearch.getMovieTitle(), searchTimestamp);

        if (prevHourTotal != null) {
            movieSearch.setDeltaLastHour(Math.abs(movieSearch.getHourTotal() - prevHourTotal));

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Previous hour total of " + prevHourTotal + " found for title "
                        + movieSearch.getMovieTitle() + ", timestamp " + searchTimestamp);
            }
        } else {
            movieSearch.setDeltaLastHour(0L);

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("No record found for title " + movieSearch.getMovieTitle()
                        + ", timestamp " + searchTimestamp);
            }
        }

        movieSearch.setTimestamp(FORMATTER.format(cal.getTime()) + SPACE + currentMinute);

        return movieSearch;
    }

    protected RootObject mapJsonToObject(String jsonResponse) {

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(jsonResponse));
        reader.setLenient(true);
        return gson.fromJson(reader, RootObject.class);
    }

    protected MovieSearch updateMovieSearchWithViews(String views, MovieSearch movieSearch) {

        if (views != null) {
            movieSearch.setViews(views);

            RootObject root = mapJsonToObject(views);
            List<Datum> dataList = root.getData();
            long numViews = 0;
            long totalViews = 0;
            for (Datum data : dataList) {
                Stats stats = data.getStats();
                totalViews += stats.getPlays();
                numViews++;
            }

            movieSearch.setNumViews(numViews);
            movieSearch.setHourTotal(totalViews);
        } else {
            LOGGER.info("No views retrieved from the server");
        }

        return movieSearch;
    }

    public IDataAccessor getDataAccessor() {
        return dataAccessor;
    }

    public void setDataAccessor(IDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

}
