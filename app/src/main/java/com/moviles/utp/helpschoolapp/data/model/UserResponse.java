package com.moviles.utp.helpschoolapp.data.model;

/**
 * Created by gustavorm on 21/10/2017.
 */

public class UserResponse {

    private int id;
    private String username;
    private String name;
    private String fatherLastname;
    private String motherLastname;
    private String email;
    private String profile;

    public UserResponse() {
    }

    public UserResponse(String username, String name, String fatherLastname, String motherLastname,
                        String email, String profile) {
        this.username = username;
        this.name = name;
        this.fatherLastname = fatherLastname;
        this.motherLastname = motherLastname;
        this.email = email;
        this.profile = profile;
    }

    public UserResponse(int id, String username, String name, String fatherLastname, String motherLastname,
                        String email, String profile) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.fatherLastname = fatherLastname;
        this.motherLastname = motherLastname;
        this.email = email;
        this.profile = profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherLastname() {
        return fatherLastname;
    }

    public void setFatherLastname(String fatherLastname) {
        this.fatherLastname = fatherLastname;
    }

    public String getMotherLastname() {
        return motherLastname;
    }

    public void setMotherLastname(String motherLastname) {
        this.motherLastname = motherLastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
