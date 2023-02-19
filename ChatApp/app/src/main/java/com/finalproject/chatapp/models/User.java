package com.finalproject.chatapp.models;

import com.finalproject.chatapp.Utility;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;

public class User implements Serializable {
    private String uID;
    private String firstName, lastName;
    private String displayName;
    private String email, phoneNumber;
    private boolean isOnline;
    private Instant lastLoginTime;
    private String status;
    private String lastLoginTimeString;

    public static final String DB_FIRST_NAME = "firstName";
    public static final String DB_LAST_NAME = "lastName";
    public static final String DB_DISPLAY_NAME = "displayName";
    public static final String DB_EMAIL = "email";
    public static final String DB_PHONE_NUMBER = "phoneNumber";
    public static final String DB_IS_ONLINE = "online";
    public static final String DB_LAST_LOGIN_TIME = "lastLoginTimeString";
    public static final String DB_STATUS = "status";
    public static final String DB_KEY = "uID";


    public User(){ }

    public User(String uID, String firstName, String lastName, String displayName, String email, String phoneNumber, boolean isOnline, String status) {
        this.uID = uID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.isOnline = isOnline;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        lastLoginTime = Utility.getCurrentDate();
        lastLoginTimeString = lastLoginTime.atOffset(ZoneOffset.UTC).toString();
    }

    @Exclude
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
        if(firstName == null) {this.firstName = ""; return;}
        if(firstName.isEmpty()) return;
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName == null) {this.lastName = ""; return;}
        if(lastName.isEmpty()) return;
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        if(displayName == null) {this.displayName = ""; return;}
        if(displayName.isEmpty()) return;
        this.displayName = displayName;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLastLoginTimeString() {
        if(lastLoginTime==null) return "";
        return lastLoginTime.toString();
    }

    public void setLastLoginTimeString(String lastLoginTimeString) {
        this.lastLoginTimeString = lastLoginTimeString;
        lastLoginTime = Utility.getDateFromString(lastLoginTimeString);
    }

    public void setLastLoginTime(Instant lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        lastLoginTime.atOffset(ZoneOffset.UTC).toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) {this.phoneNumber = ""; return;}
        if(phoneNumber.isEmpty()) return;
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
