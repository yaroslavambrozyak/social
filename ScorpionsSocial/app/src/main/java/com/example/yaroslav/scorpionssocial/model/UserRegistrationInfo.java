package com.example.yaroslav.scorpionssocial.model;

public class UserRegistrationInfo {

    private int id;
    private String token;
    private String name;
    private String email;

    public UserRegistrationInfo(int id, String token, String name, String email) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.email = email;
    }

    public UserRegistrationInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
