package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by Walberth Gutierrez Telles on 11/1/2017.
 */

public class PendingRequestResponse {
    private String idRequest;
    private int idRequester;
    private String usernameRequester;
    private int idRequestType;
    private String request;
    private String statusRequest;
    private String timeStampCReq;

    public PendingRequestResponse(String idRequest, int idRequester, String usernameRequester, int idRequestType,
                                  String request, String statusRequest, String timeStampCReq) {
        this.idRequest = idRequest;
        this.idRequester = idRequester;
        this.usernameRequester = usernameRequester;
        this.idRequestType = idRequestType;
        this.request = request;
        this.statusRequest = statusRequest;
        this.timeStampCReq = timeStampCReq;
    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdRequester() {
        return idRequester;
    }

    public void setIdRequester(int idRequester) {
        this.idRequester = idRequester;
    }

    public String getUsernameRequester() {
        return usernameRequester;
    }

    public void setUsernameRequester(String usernameRequester) {
        this.usernameRequester = usernameRequester;
    }

    public int getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(int idRequestType) {
        this.idRequestType = idRequestType;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getTimeStampCReq() {
        return timeStampCReq;
    }

    public void setTimeStampCReq(String timeStampCReq) {
        this.timeStampCReq = timeStampCReq;
    }
}
