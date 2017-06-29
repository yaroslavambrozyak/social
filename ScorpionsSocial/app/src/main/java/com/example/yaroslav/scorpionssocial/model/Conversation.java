package com.example.yaroslav.scorpionssocial.model;


public class Conversation {
    private int id;
    private String name;

    public Conversation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Conversation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
