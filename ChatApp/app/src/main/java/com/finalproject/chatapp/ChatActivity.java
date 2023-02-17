package com.finalproject.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.finalproject.chatapp.adapters.MessageAdapter;
import com.finalproject.chatapp.adapters.MessageViewModel;
import com.finalproject.chatapp.adapters.UserAdapter;
import com.finalproject.chatapp.adapters.UserViewModel;
import com.finalproject.chatapp.models.Chat;
import com.finalproject.chatapp.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.time.Instant;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, MessageAdapter.IMessageClickListener {
    private String userID, otherUserID, chatID;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private ImageButton sendButton;
    private DatabaseReference messagesDatabase;
    private EditText messageText;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("userID");
        otherUserID = bundle.getString("otherUserID");
        Chat chat = new Chat(userID, otherUserID);
        chatID = chat.getChatID();
        sendButton = findViewById(R.id.btnSend);
        sendButton.setOnClickListener(this);
        messageText = findViewById(R.id.msgText);
        firebaseAuth = FirebaseAuth.getInstance();
        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.setChatID(chatID);
        messageAdapter = new MessageAdapter(this, messageViewModel, getSupportFragmentManager(), this);
        recyclerView = findViewById(R.id.recyclerViewMessage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        messagesDatabase = FirebaseDatabase.getInstance().getReference("Messages").child(chatID);
        recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);

    }

    @Override
    public void onClick(View v) {
        String msg = messageText.getText().toString().trim();
        if(msg != null && !msg.equals(""))
        {
            Message message = new Message(chatID, userID, Utility.getDate(), msg);
            messagesDatabase.child(Utility.getDate()).setValue(message);
            messageText.setText("");
            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void DeleteMessage(Message message) {
        // TODO: Open dialog asking if user is sure they want to delete the message
        messagesDatabase.child(message.getMessageDate()).removeValue();
    }
}