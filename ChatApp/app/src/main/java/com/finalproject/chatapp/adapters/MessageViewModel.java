package com.finalproject.chatapp.adapters;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.chatapp.controllers.MessageController;
import com.finalproject.chatapp.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageViewModel extends AndroidViewModel {
    private final FirebaseUser firebaseUser;
    private String chatID;
    private MutableLiveData<ArrayList<Message>> messages;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Message> messageArrayList;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        messages = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        messageArrayList = new ArrayList<>();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public MutableLiveData<ArrayList<Message>> getMessages() {
        return messages;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
        DatabaseReference messagesDatabase = FirebaseDatabase.getInstance().getReference("Messages").child(chatID);
        messagesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageArrayList.clear(); //initialize the list of the messages
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (firebaseUser != null && message != null && message.getContent() != null)
                    {
                        message.setDate(snapshot.getKey());
                        message.setChatID(chatID);
                        message = MessageController.getMessageWithCorrectTime(message);
                        messageArrayList.add(message);
                    }
                }
                Log.i("", messageArrayList.toString());
                messages.setValue(messageArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}