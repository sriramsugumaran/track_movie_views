package com.ram.sri.track.output.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ddb-table-sst67gy")
public class DDBTable {

    private String title;
    private String searchTerm;
    private String timestamp;
    private String source;
    private Long numViews;
    private Long hourTotal;
    private Long deltaLastHour;
    private String views;

    @DynamoDBHashKey(attributeName = "title")
    public String getTitle() {
        return title;
    }

    @DynamoDBRangeKey(attributeName = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @DynamoDBAttribute(attributeName = "search_term")
    public String getSearchTerm() {
        return searchTerm;
    }

    @DynamoDBAttribute(attributeName = "source")
    public String getSource() {
        return source;
    }

    @DynamoDBAttribute(attributeName = "delta_last_hour")
    public Long getDeltaLastHour() {
        return deltaLastHour;
    }

    @DynamoDBAttribute(attributeName = "hour_total")
    public Long getHourTotal() {
        return hourTotal;
    }

    @DynamoDBAttribute(attributeName = "num_views")
    public Long getNumViews() {
        return numViews;
    }

    @DynamoDBAttribute(attributeName = "views")
    public String getViews() {
        return views;
    }

    public void setDeltaLastHour(Long deltaLastHour) {
        this.deltaLastHour = deltaLastHour;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setHourTotal(Long hourTotal) {
        this.hourTotal = hourTotal;
    }

    public void setNumViews(Long numViews) {
        this.numViews = numViews;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
