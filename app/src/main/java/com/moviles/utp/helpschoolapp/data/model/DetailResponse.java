package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by Walberth Gutierrez Telles on 11/12/2017.
 */

public class DetailResponse {
    private String status;
    private String requestType;
    private String dateTime;
    private String description;

    public DetailResponse(String status, String requestType, String dateTime, String description) {
        this.status = status;
        this.requestType = requestType;
        this.dateTime = dateTime;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
