package com.example.yaroslav.scorpionssocial.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PersonalInfo {

    @JsonProperty("id")
    private int id;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    @JsonProperty("isBanned")
    private boolean isBanned;
    @JsonProperty("birthDate")
    private Date birthDate;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("registrationDate")
    private Date registrationDate;

    public PersonalInfo(int id, boolean isAdmin, boolean isBanned, Date birthDate, String firstName, String lastName, String phoneNumber, Date registrationDate) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.isBanned = isBanned;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
    }

    public PersonalInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
