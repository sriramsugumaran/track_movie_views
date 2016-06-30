package com.ram.sri.track.input.model;

public class MovieSearch {

    private String movieTitle;
    private String searchTerm;
    private Long numViews;
    private Long hourTotal;
    private Long deltaLastHour;
    private String timestamp;
    private String views;
    private String source;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Long getHourTotal() {
        return hourTotal;
    }

    public void setHourTotal(Long hourTotal) {
        this.hourTotal = hourTotal;
    }

    public Long getDeltaLastHour() {
        return deltaLastHour;
    }

    public void setDeltaLastHour(Long deltaLastHour) {
        this.deltaLastHour = deltaLastHour;
    }

    public Long getNumViews() {
        return numViews;
    }

    public void setNumViews(Long numViews) {
        this.numViews = numViews;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Movie Search [MovieTitle=" + movieTitle + ", SearchTerm=" + searchTerm + "]";
    }
}
