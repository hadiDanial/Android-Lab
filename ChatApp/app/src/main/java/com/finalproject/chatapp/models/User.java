package com.finalproject.chatapp.models;

import java.time.Instant;

public class User {
    private String uID;
    private String firstName, lastName;
    private String displayName;
    private boolean isOnline;
    private Instant lastLoginTime;
    private String lastLoginTimeString;

    public User(){ }

    public User(String uID, String firstName, String lastName, String displayName, boolean isOnline) {
        this.uID = uID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.isOnline = isOnline;
        lastLoginTime = Instant.now();
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLastLoginTimeString() {
        return lastLoginTime.toString();
    }

    public void setLastLoginTimeString(String lastLoginTimeString) {
        this.lastLoginTimeString = lastLoginTimeString;
        lastLoginTime = Instant.parse(lastLoginTimeString);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
