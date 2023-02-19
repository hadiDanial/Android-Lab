package com.finalproject.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.finalproject.chatapp.adapters.UserAdapter;
import com.finalproject.chatapp.adapters.UserViewModel;
import com.finalproject.chatapp.controllers.UserController;
import com.finalproject.chatapp.fragments.Profile;
import com.finalproject.chatapp.fragments.UserData;
import com.finalproject.chatapp.models.User;
import com.finalproject.chatapp.services.NetworkBroadcastReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity implements UserViewModel.ISetActiveUser {
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private RelativeLayout container;
    private User user;
    private NetworkBroadcastReceiver networkBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        UserAdapter userAdapter = new UserAdapter(this, userViewModel, getSupportFragmentManager());
        userViewModel.setListener(this);
        recyclerView = findViewById(R.id.recyclerViewContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
        container = findViewById(R.id.dashboardContainer);
        networkBroadcastReceiver = new NetworkBroadcastReceiver(getSupportFragmentManager(), R.id.dashboardContainer, this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.profile:
            {
                //loginButton.setVisibility(View.GONE);
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = supportFragmentManager.beginTransaction();
                Fragment f = Profile.newInstance(user);
                ft.add(R.id.dashboardContainer, f).addToBackStack("profile").commit();
                break;
            }
            case R.id.Logout:
            {
                UserController.logout(getApplication());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            case R.id.Exit:
            {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setActiveUser(User activeUser) {
        this.user = activeUser;
    }
}