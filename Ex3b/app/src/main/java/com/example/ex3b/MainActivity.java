package com.example.ex3b;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String firstName, lastName, gender;
    private TextView helloTextView;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloTextView = findViewById(R.id.helloText);
        registerButton = findViewById(R.id.registerButton);
    }


    public void onRegisterButtonClick(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        firstName = data.getStringExtra("firstName");
        lastName = data.getStringExtra("lastName");
        gender = data.getStringExtra("gender");

        String newText = "Welcome back, " + (gender.equals("Male") ? "Mr." : "Ms.") + " " +firstName + " " + lastName + "!";
        helloTextView.setText(newText);
        registerButton.setText("Again...");
    }
}