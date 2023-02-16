package com.finalproject.chatapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.finalproject.chatapp.databinding.FragmentEnterNumberBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterNumberFragment extends Fragment {

    private final IEnterNumberListener listener;
    private FragmentEnterNumberBinding binding;

    public EnterNumberFragment(IEnterNumberListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentEnterNumberBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText phoneNumberEditText = binding.phoneNumber;
        final Button loginButton = binding.loginButton;
        final ProgressBar loadingProgressBar = binding.loading;
        loadingProgressBar.setVisibility(View.GONE);
        binding.phoneNumber.setEnabled(true);
        binding.loginButton.setEnabled(true);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                binding.phoneNumber.setEnabled(false);
                binding.loginButton.setEnabled(false);
                // TODO: Firebase Login here
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        listener.signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getActivity(),"Verification failed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        listener.setVerificationID(verificationId);
                        listener.replaceFragment();
                    }
                };
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber("+972"+binding.phoneNumber.getText().toString())       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(getActivity())                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
//                loginViewModel.login(phoneNumberEditText.getText().toString(),
//                        passwordEditText.getText().toString());
            }
        });


    }

    public interface IEnterNumberListener{
        public void signInWithPhoneAuthCredential(PhoneAuthCredential credential);
        public void setVerificationID(String verificationId);
        public void replaceFragment();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}