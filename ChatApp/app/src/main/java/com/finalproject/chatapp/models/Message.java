package com.finalproject.chatapp.models;

import java.sql.Date;

public class Message {
    private Chat chat;
    private User user;
    private Date messageDate;
    private String content;

    public Message(){}

    public Message(Chat chat, User user, Date messageDate, String content) {
        this.chat = chat;
        this.user = user;
        this.messageDate = messageDate;
        this.content = content;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
