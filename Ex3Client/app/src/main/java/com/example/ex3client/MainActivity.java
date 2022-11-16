package com.example.ex3client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editPhone, editWebsite, editEmail, editRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmail = findViewById(R.id.emailText);
        editPhone = findViewById(R.id.editPhone);
        editRegister = findViewById(R.id.registerText);
        editWebsite = findViewById(R.id.editWebsite);
    }

    public void onCallClick(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + editPhone.getText().toString()));
        startActivity(intent);
    }

    public void onSurfClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url = editWebsite.getText().toString();
        if(!url.startsWith("http"))
            url = "http://" + url;
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void onEmailClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + editEmail.getText().toString()));
        startActivity(intent);
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent();
        intent.setAction("com.action.ex3.register");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String firstName = data.getStringExtra("firstName");
        String lastName = data.getStringExtra("lastName");
        String gender = data.getStringExtra("gender");
        String value = (gender.equals("Male") ? "Mr" : "Ms") + " " + firstName + " " + lastName;
        editRegister.setText(value);
    }
}
