package com.finalproject.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.finalproject.chatapp.adapters.MessageAdapter;
import com.finalproject.chatapp.adapters.MessageViewModel;
import com.finalproject.chatapp.controllers.MessageController;
import com.finalproject.chatapp.controllers.UserController;
import com.finalproject.chatapp.models.Chat;
import com.finalproject.chatapp.models.Message;
import com.finalproject.chatapp.services.NetworkBroadcastReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, MessageAdapter.IMessageClickListener {
    private String userID, otherUserID, chatID;
    private RecyclerView recyclerView;
    private ImageButton sendButton;
    private DatabaseReference messagesDatabase;
    private EditText messageText;
    private MessageAdapter messageAdapter;
    private NetworkBroadcastReceiver networkBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendButton = findViewById(R.id.btnSend);
        messageText = findViewById(R.id.msgText);

        recyclerView = findViewById(R.id.recyclerViewMessage);
        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("userID");
        otherUserID = bundle.getString("otherUserID");

        Chat chat = new Chat(userID, otherUserID);
        chatID = chat.getChatID();
        sendButton.setOnClickListener(this);

        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.setChatID(chatID);
        messageAdapter = new MessageAdapter(this, messageViewModel, getSupportFragmentManager(), this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);

        messagesDatabase = FirebaseDatabase.getInstance().getReference("Messages").child(chatID);
        networkBroadcastReceiver = new NetworkBroadcastReceiver(getSupportFragmentManager(), R.id.chatContainer, this, findViewById(R.id.writeMessageLayout));

        UserController.setOnDisconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserController.setOnlineStatus(true);
        networkBroadcastReceiver.register();
    }

    @Override
    protected void onPause() {
        UserController.setOnlineStatus(false);
        networkBroadcastReceiver.unregister();
        super.onPause();
    }

    // Send a new message
    @Override
    public void onClick(View v) {
        String msg = messageText.getText().toString().trim();
        if(msg != null && !msg.equals(""))
        {
            Message message = new Message(chatID, userID, Utility.getCurrentDateString(), msg);
            MessageController.saveMessage(message);
            messageText.setText("");
            recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
        }
    }

    /**
     *  Delete message
     * @param message Message to delete
     */
    @Override
    public void DeleteMessage(Message message) {
        // Open dialog asking if user is sure they want to delete the message
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this message?");
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton("Delete", (dialog, id) -> MessageController.deleteMessage(message));
        alertDialogBuilder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        alertDialogBuilder.create().show();
    }
}