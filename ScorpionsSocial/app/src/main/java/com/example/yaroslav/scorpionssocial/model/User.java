package com.example.yaroslav.scorpionssocial.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("id")
    private int id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    @JsonProperty("isOnline")
    private boolean isOnline;

    public User() {

    }

    public User(int id, String email, String userName, String picture, boolean isDeleted, boolean isOnline) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.picture = picture;
        this.isDeleted = isDeleted;
        this.isOnline = isOnline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
