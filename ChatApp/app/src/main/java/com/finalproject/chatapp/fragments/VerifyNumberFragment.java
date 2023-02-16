package com.finalproject.chatapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.finalproject.chatapp.databinding.FragmentVerifyNumberBinding;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyNumberFragment extends Fragment {

    private final IVerifyNumberListener listener;
    private FragmentVerifyNumberBinding binding;

    public VerifyNumberFragment(IVerifyNumberListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentVerifyNumberBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText phoneNumberEditText = binding.otp;
        final Button loginButton = binding.verifyButton;
        final ProgressBar loadingProgressBar = binding.loading;



        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        phoneNumberEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(listener.getVerificationId(), binding.otp.toString());
                listener.signInWithPhoneAuthCredential(credential);
            }
        });
    }

    public interface IVerifyNumberListener {
        void signInWithPhoneAuthCredential(PhoneAuthCredential credential);
        String getVerificationId();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}