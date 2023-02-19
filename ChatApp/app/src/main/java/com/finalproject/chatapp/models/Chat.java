package com.finalproject.chatapp.models;

import java.util.Arrays;
import java.util.List;

public class Chat {
    private String firstUserID, secondUserID;
    private String chatID;

    private String lastMessageID;

    public Chat(){ }

    public Chat(String firstUserID, String secondUserID) {
        this.firstUserID = firstUserID;
        this.secondUserID = secondUserID;
        this.chatID = getChatID();
    }

    public String getFirstUserID() {
        return firstUserID;
    }

    public void setFirstUserID(String firstUserID) {
        this.firstUserID = firstUserID;
    }

    public String getSecondUserID() {
        return secondUserID;
    }

    public void setSecondUserID(String secondUserID) {
        this.secondUserID = secondUserID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getLastMessageID() {
        return lastMessageID;
    }

    public void setLastMessageID(String lastMessageID) {
        this.lastMessageID = lastMessageID;
    }

    public String getChatID()
    {
        List<String> ids = Arrays.asList(firstUserID, secondUserID);
        ids.sort(String::compareTo);
        String uid = ids.get(0) + "_" + ids.get(1);
        return uid;
    }
    public static String getChatID(String userID1, String userID2)
    {
        Chat chat = new Chat(userID1, userID2);
        return chat.getChatID();
    }

    @Override
    public String toString() {
        return "Chat{" +
                "firstUserID='" + firstUserID + '\'' +
                ", secondUserID='" + secondUserID + '\'' +
                ", chatID='" + chatID + '\'' +
                ", lastMessageID='" + lastMessageID + '\'' +
                '}';
    }
}
