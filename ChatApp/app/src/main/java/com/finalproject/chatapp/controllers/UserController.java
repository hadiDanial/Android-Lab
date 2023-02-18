package com.finalproject.chatapp.controllers;

import com.finalproject.chatapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserController {
    private static final String TABLE_NAME = "Users";

    public static void setOnlineStatus(boolean value) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(user.getUid()).child("online").setValue(value);
    }

    public static void updateStringValue(String newValue, String columnName)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(user.getUid()).child(columnName).setValue(newValue);
    }

    public static void updateUser(User updatedUser)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null && updatedUser != null && updatedUser.getuID() != null)
            FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(user.getUid()).setValue(updatedUser);
    }

}
