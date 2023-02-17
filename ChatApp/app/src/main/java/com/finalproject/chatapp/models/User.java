package com.finalproject.chatapp.models;

import com.finalproject.chatapp.Utility;
import com.google.firebase.database.Exclude;

import java.time.Instant;
import java.util.Date;

public class User {
    private String uID;
    private String firstName, lastName;
    private String displayName;
    private String email, phoneNumber;
    private boolean isOnline;
    private Date lastLoginTime;
    private String lastLoginTimeString;

    public User(){ }

    public User(String uID, String firstName, String lastName, String displayName, String email, String phoneNumber, boolean isOnline) {
        this.uID = uID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.isOnline = isOnline;
        this.phoneNumber = phoneNumber;
        this.email = email;
        lastLoginTime = new Date();
        lastLoginTimeString = Utility.getDate(lastLoginTime);
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
        return Utility.getDate(lastLoginTime);
    }

    public void setLastLoginTimeString(String lastLoginTimeString) {
        this.lastLoginTimeString = lastLoginTimeString;
        lastLoginTime = new Date();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Exclude
    public String getName() {
        if(displayName != null && !displayName.equals(""))
            return displayName;
        return toString();
    }
}
