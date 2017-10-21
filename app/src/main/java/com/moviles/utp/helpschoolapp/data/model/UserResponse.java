package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by gustavorm on 21/10/2017.
 */

public class UserResponse {

    private String username;
    private String password;

    public UserResponse() {
    }

    public UserResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
