package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by walbe on 11/4/2017.
 */

public class ListRequestResponse {
    private String requestType;
    private String labelRequest;
    private String status;

    public ListRequestResponse(String requestType, String labelRequest, String status) {
        this.requestType = requestType;
        this.labelRequest = labelRequest;
        this.status = status;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getLabelRequest() {
        return labelRequest;
    }

    public void setLabelRequest(String labelRequest) {
        this.labelRequest = labelRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
