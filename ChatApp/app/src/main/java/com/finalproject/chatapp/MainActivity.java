package com.finalproject.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.finalproject.chatapp.fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = supportFragmentManager.beginTransaction();
            Fragment f = new LoginFragment(supportFragmentManager);
            ft.add(R.id.mainContainer, f).addToBackStack("login").commit();
        }
        else {
// to do
        }
    }
}