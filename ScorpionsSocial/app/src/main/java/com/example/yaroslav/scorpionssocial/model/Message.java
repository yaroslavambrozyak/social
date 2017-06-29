package com.example.yaroslav.scorpionssocial.model;


import java.util.Date;


public class Message {

    private int id;
    private String attachment;
    private Date sendDate;
    private int conversationId;
    private String text;
    private int userId;

    public Message(int id, String attachment, Date sendDate, int conversationId, String text, int userId) {
        this.id = id;
        this.attachment = attachment;
        this.sendDate = sendDate;
        this.conversationId = conversationId;
        this.text = text;
        this.userId = userId;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
