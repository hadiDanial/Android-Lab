package com.finalproject.chatapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.chatapp.controllers.UserController;
import com.finalproject.chatapp.fragments.UserData;
import com.finalproject.chatapp.models.User;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserData.ISetupUserDetails {
    private FirebaseAuth firebaseAuth;
    private Button loginButton;
    private boolean isNewUser = false;
    private User user;

    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
            DatabaseReference userNameRef = usersDatabase.child(firebaseUser.getUid());
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) { // If user doesn't exist, create new user
                        isNewUser = true;
                        user = new User(firebaseUser.getUid(), "", "", firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhoneNumber(), false, "");
                        usersDatabase.child(firebaseUser.getUid()).setValue(user);
                        openUserDataFragment(user);
                    } else // User exists
                    {
                        user = dataSnapshot.getValue(User.class);
                        user.setLastLoginTime(new Date());
                        UserController.updateStringValue(user.getLastLoginTimeString(), User.DB_LAST_LOGIN_TIME);
                        if (user.getDisplayName() == null || user.getDisplayName().isEmpty()) // No display name set
                            openUserDataFragment(user);
                        else
                        {
                            UserController.saveUserData(getApplication(), user);
                            moveToDashboard();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
                }
            };
            userNameRef.addListenerForSingleValueEvent(eventListener);


            // ...
        } else {
            if (response != null) {
                Toast.makeText(this, "Failed to login/register, " + response.getError(), Toast.LENGTH_LONG).show();
            }
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isNewUser", isNewUser);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.isNewUser = savedInstanceState.getBoolean("isNewUser");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            if (isNewUser)
            {
                reopenUserDataFragment();
            }
            else
            {
                loginButton.setVisibility(View.GONE);
                findViewById(R.id.welcomeLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.continueButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveToDashboard();
                    }
                });
                User user = UserController.getLoggedInUser(getApplication());
                TextView welcomeText = findViewById(R.id.welcomeText);
                if(user != null)
                    welcomeText.setText("Welcome back, " + user.getName() + "!");
                else
                    welcomeText.setText("Welcome back!");
            }
//            FragmentManager supportFragmentManager = getSupportFragmentManager();
//            FragmentTransaction ft = supportFragmentManager.beginTransaction();
//            Fragment f = new LoginFragment(supportFragmentManager);
//            ft.add(R.id.mainContainer, f).addToBackStack("login").commit();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsingAuthUI();
            }
        });
    }

    private void reopenUserDataFragment() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userNameRef = usersDatabase.child(firebaseUser.getUid());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user.getDisplayName() == null || user.getDisplayName().isEmpty()) // No display name set
                        openUserDataFragment(user);
                    else
                        moveToDashboard();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    private void moveToDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void openUserDataFragment(User user) {
        loginButton.setVisibility(View.GONE);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        Fragment f = UserData.newInstance(user, this);
        ft.add(R.id.mainContainer, f).commit();
    }

    private void loginUsingAuthUI() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        AuthMethodPickerLayout myLayout = new AuthMethodPickerLayout.Builder(R.layout.auth_ui_login)
                .setGoogleButtonId(R.id.googleLoginButton)
                .setPhoneButtonId(R.id.phoneLoginButton)
                .setEmailButtonId(R.id.emailLoginButton).build();

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAuthMethodPickerLayout(myLayout)
                .build();
        signInLauncher.launch(signInIntent);
    }

    @Override
    public void setupComplete() {
        isNewUser = false;
    }
}