package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by JuanCarlos on 11/11/2017.
 */

public class ListRequestType {

    private int id;
    private String requestName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public ListRequestType(int id, String requestName) {

        this.id = id;
        this.requestName = requestName;
    }

    @Override
    public String toString() {
        return requestName;
    }


}
