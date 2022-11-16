package com.example.ex3b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private String gender;
    private EditText firstNameText, lastNameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstNameText = findViewById(R.id.firstName);
        lastNameText = findViewById(R.id.lastName);
    }

    public void onGenderSelected(View view) {
        switch (view.getId())
        {
            case R.id.male:
                gender = "Male";
                break;
            case R.id.female:
                gender = "Female";
                break;
            default:
                gender = "";
                break;
        }
    }

    public void onRegisterButtonClick(View view) {
        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        // Check if empty names or gender...
        if(firstName.equals("") || lastName.equals("") || gender.equals(""))
        {
            Toast.makeText(getApplicationContext(),(CharSequence) "Please fill first and last name and gender!", Toast.LENGTH_LONG).show();
            return;
        }
        //

        Intent intent = new Intent();
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("gender", gender);
        setResult(RESULT_OK, intent);
        finish();
    }
}