package com.finalproject.chatapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.finalproject.chatapp.Dashboard;
import com.finalproject.chatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationService extends Service {
    private static final String CHANNEL_ID_1 = "ChatAppNotification", CHANNEL_ID_2 = "UserNotification";
    private Notification notification1, notification2;

    private long numUsers;
    private boolean initialCountSet = false;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID_1, CHANNEL_ID_1, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel1);
        NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_2, CHANNEL_ID_2, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel2);
        FirebaseDatabase.getInstance().getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                if(!initialCountSet)
                {
                    numUsers = count;
                    initialCountSet = true;
                }
                else
                {
                    if(count > numUsers)
                    {
                        numUsers = count;
                        startForeground(2, notification2);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, Dashboard.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification1 = new Notification.Builder(this, CHANNEL_ID_1).setContentTitle("ChatApp")
                .build();

        notification2 = new Notification.Builder(this, CHANNEL_ID_2).setContentTitle("ChatApp - New User!")
                .setContentText("A new user joined! Talk to them today!")
                .setSmallIcon(R.drawable.ic_sendmessage)
                .setContentIntent(pendingIntent)
                .setStyle(new Notification.BigTextStyle())
                .build();

        startForeground(1, notification1);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
