package com.finalproject.chatapp.models;

import java.util.Arrays;
import java.util.List;

public class Chat {
    private User firstUser, secondUser;
    private String uID;

    public Chat(){ }

    public Chat(User firstUser, User secondUser, String uID) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.uID = uID;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getuID()
    {
        List<String> ids = Arrays.asList(firstUser.getuID(), secondUser.getuID());
        ids.sort(String::compareTo);
        String uid = ids.get(0) + ids.get(1);
        return uid;

    }

}
