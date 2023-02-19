package com.finalproject.chatapp.controllers;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.finalproject.chatapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserController {
    private static final String TABLE_NAME = "Users";

    /**
     * Set online status of logged in user.
     * @param value
     */
    public static void setOnlineStatus(boolean value) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(user.getUid()).child(User.DB_IS_ONLINE).setValue(value);
    }

    /**
     * Update a value in a user in Firebase database.
     * @param newValue
     * @param columnName
     */
    public static void updateStringValue(String newValue, String columnName)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(user.getUid()).child(columnName).setValue(newValue);
    }

    /**
     * Update user in Firebase database.
     * @param updatedUser
     */
    public static void updateUser(User updatedUser)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null && updatedUser != null && updatedUser.getuID() != null)
            FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(user.getUid()).setValue(updatedUser);
    }

    /**
     * Log out and clear saved user data.
     * @param application
     */
    public static void logout(Application application)
    {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Save logged-in user data in SharedPreferences (SP).
     * @param application
     * @param user User to save.
     */
    public static void saveUserData(Application application, User user)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(application);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(User.DB_IS_ONLINE, user.isOnline());
        editor.putString(User.DB_DISPLAY_NAME, user.getDisplayName());
        editor.putString(User.DB_FIRST_NAME, user.getFirstName());
        editor.putString(User.DB_LAST_NAME, user.getLastName());
        editor.putString(User.DB_LAST_LOGIN_TIME, user.getLastLoginTimeString());
        editor.putString(User.DB_EMAIL, user.getEmail());
        editor.putString(User.DB_PHONE_NUMBER, user.getPhoneNumber());
        editor.putString(User.DB_STATUS, user.getStatus());
        editor.putString(User.DB_KEY, user.getuID());
        editor.putBoolean("saved", true);
        editor.apply();
    }

    /***
     * Get logged-in user data from SharedPreferences (SP).
     * @param application
     * @return Logged-in user data, or null if no user is logged in.
     */
    public static User getLoggedInUser(Application application)
    {
        User user = new User();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(application);
        if(!sharedPref.getBoolean("saved", false))
            return null;
        user.setOnline(sharedPref.getBoolean(User.DB_IS_ONLINE, false));
        user.setDisplayName(sharedPref.getString(User.DB_DISPLAY_NAME, null));
        user.setFirstName(sharedPref.getString(User.DB_FIRST_NAME, null));
        user.setLastName(sharedPref.getString(User.DB_LAST_NAME, null));
        user.setLastLoginTimeString(sharedPref.getString(User.DB_LAST_LOGIN_TIME, null));
        user.setEmail(sharedPref.getString(User.DB_EMAIL, null));
        user.setPhoneNumber(sharedPref.getString(User.DB_PHONE_NUMBER, null));
        user.setStatus(sharedPref.getString(User.DB_STATUS, null));
        user.setuID(sharedPref.getString(User.DB_KEY, null));
        return user;
    }


}
