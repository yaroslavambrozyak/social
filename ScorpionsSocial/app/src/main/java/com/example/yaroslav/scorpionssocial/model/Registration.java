package com.example.yaroslav.scorpionssocial.model;


public class Registration {

    private String email;
    private String login;
    private String name;
    private String surName;
    private String password;
    private String confirmPassword;

    public Registration(String email, String login, String name, String surName, String password, String confirmPassword) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.surName = surName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Registration() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
