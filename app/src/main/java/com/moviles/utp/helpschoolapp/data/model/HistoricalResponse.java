package com.moviles.utp.helpschoolapp.data.model;

import java.util.Date;

/**
 * Created by Gustavo Ramos M. on 11/11/2017.
 */

public class HistoricalResponse {

    private Date date;
    private String username;
    private String typeRequest;
    private String timestamp;
    private String action;
    private String event;
    private String status;

    public HistoricalResponse(Date date, String username, String typeRequest, String timestamp,
                              String action, String event, String status) {
        this.date = date;
        this.username = username;
        this.typeRequest = typeRequest;
        this.timestamp = timestamp;
        this.action = action;
        this.event = event;
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
