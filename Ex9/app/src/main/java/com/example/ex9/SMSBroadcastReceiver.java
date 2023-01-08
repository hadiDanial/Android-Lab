package com.example.ex9;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
            return;
        SmsMessage messages[] = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        SmsMessage smsMessage = messages[0]; // First chunk of SMS
        if (smsMessage != null) {
            String phoneNumber = smsMessage.getDisplayOriginatingAddress();
            String text = smsMessage.getDisplayMessageBody();
            String data = "SMS received from: " + phoneNumber + "\n" + text;
            Toast.makeText(context, data, Toast.LENGTH_LONG).show();
        }

    }
}
