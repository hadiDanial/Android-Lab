package com.finalproject.chatapp.controllers;

import com.finalproject.chatapp.Utility;
import com.finalproject.chatapp.models.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageController {
    private static final String TABLE_NAME = "Messages";
    private static final char OLD_CHAR = '.', NEW_CHAR = '!';
    public static void saveMessage(Message message)
    {
        DatabaseReference messagesDatabase = FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(message.getChatID());
        messagesDatabase.child(Utility.getCurrentDateString().replace(OLD_CHAR, NEW_CHAR)).setValue(message);
    }

    public static Message getMessageWithCorrectTime(Message message) {
        String date = message.getDate();
        message.setDate(date.replace(NEW_CHAR, OLD_CHAR));
        return message;
    }

    public static void deleteMessage(Message message) {
        DatabaseReference messagesDatabase = FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(message.getChatID());
        messagesDatabase.child(message.getDate().replace(OLD_CHAR, NEW_CHAR)).removeValue();
    }
    public static void clearChat(String chatId)
    {
        FirebaseDatabase.getInstance().getReference(TABLE_NAME).child(chatId).removeValue();
    }

}
