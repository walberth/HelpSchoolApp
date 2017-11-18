package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by JuanCarlos on 14/11/2017.
 */

public class SendRequest {
    private String confirmation;

    public SendRequest(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
