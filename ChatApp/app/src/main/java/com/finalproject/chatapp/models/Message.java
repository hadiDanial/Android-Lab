package com.finalproject.chatapp.models;

import java.sql.Date;

public class Message {
    private String chatID;
    private String userID;
    private String messageDate;
    private String content;

    public Message(){}

    public Message(String chatID, String userID, String messageDate, String content) {
        this.chatID = chatID;
        this.userID = userID;
        this.messageDate = messageDate;
        this.content = content;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
