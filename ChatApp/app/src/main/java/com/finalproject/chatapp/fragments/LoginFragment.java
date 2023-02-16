package com.finalproject.chatapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements EnterNumberFragment.IEnterNumberListener, VerifyNumberFragment.IVerifyNumberListener {
    private final FragmentManager supportFragmentManager;
    private String verificationId;

    public LoginFragment(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    public static LoginFragment newInstance(FragmentManager supportFragmentManager) {
        LoginFragment fragment = new LoginFragment(supportFragmentManager);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        Fragment f = new EnterNumberFragment(this);
        ft.add(R.id.loginDataContainer, f).addToBackStack("login").commit();
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FB", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                            HashMap<String, String> map = new HashMap<>();
                            map.put("K1", "Data");
                            map.put("Test", "Hello");
                            databaseReference.setValue(map);

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("FB", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public String getVerificationId() {
        return verificationId;
    }

    @Override
    public void setVerificationID(String verificationId) {
        this.verificationId = verificationId;
    }

    @Override
    public void replaceFragment() {
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        Fragment f = new VerifyNumberFragment(this);
        ft.replace(R.id.loginDataContainer, f).addToBackStack("login").commit();
    }
}