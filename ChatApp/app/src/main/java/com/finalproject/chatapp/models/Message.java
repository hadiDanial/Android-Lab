package com.finalproject.chatapp.models;

import com.google.firebase.database.Exclude;

public class Message {
    private String chatID;
    private String userID;
    private String date;
    private String content;

    public Message() {
    }

    public Message(String chatID, String userID, String date, String content) {
        this.chatID = chatID;
        this.userID = userID;
        this.date = date;
        this.content = content;
    }

    @Exclude
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

    @Exclude
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
