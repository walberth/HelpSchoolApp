package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by Gustavo Ramos M. on 11/11/2017.
 */

public class HistoricalResponse {

    private String date;
    private String username;
    private String timestamp;
    private String event;
    private String status;

    public HistoricalResponse(String date) {
        this.date = date;
    }

    public HistoricalResponse(String date, String username, String timestamp, String event, String status) {
        this.date = date;
        this.username = username;
        this.timestamp = timestamp;
        this.event = event;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
