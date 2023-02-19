package com.finalproject.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finalproject.chatapp.Dashboard;
import com.finalproject.chatapp.Utility;
import com.finalproject.chatapp.controllers.UserController;
import com.finalproject.chatapp.databinding.FragmentUserDataBinding;
import com.finalproject.chatapp.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private final ISetupUserDetails listener;

    private User user;
    private FragmentUserDataBinding binding;
    private boolean firstTextEntered = false, secondTextEntered = false, thirdTextEntered = false;

    public UserData(User user, ISetupUserDetails listener) {
        this.user = user;
        this.listener = listener;
    }

    public static UserData newInstance(User user, ISetupUserDetails listener) {
        UserData fragment = new UserData(user, listener);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTextWatchers();
        binding.btnDataDone.setEnabled(false);
        binding.btnDataDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setDisplayName(binding.displayName.getText().toString().trim());
                user.setFirstName(binding.firstName.getText().toString().trim());
                user.setLastName(binding.lastName.getText().toString().trim());
                user.setStatus(binding.status.getText().toString().trim());
                user.setLastLoginTime(Utility.getCurrentDate());
                UserController.updateUser(user);
                listener.setupComplete();
                Intent intent = new Intent(getContext(), Dashboard.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        binding.displayName.setText(user.getDisplayName());
        binding.firstName.setText(user.getFirstName());
        binding.lastName.setText(user.getLastName());
        firstTextEntered = user.getDisplayName().isEmpty(); secondTextEntered = user.getFirstName().isEmpty(); thirdTextEntered = user.getLastName().isEmpty();
    }

    private void setTextWatchers() {
        binding.firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { firstTextEntered = s.length() > 0 ? true : false; updateDoneButtonEnabled(); }
        });
        binding.lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { secondTextEntered = s.length() > 0 ? true : false; updateDoneButtonEnabled(); }
        });
        binding.displayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { thirdTextEntered = s.length() > 0 ? true : false; updateDoneButtonEnabled(); }
        });
    }

    private void updateDoneButtonEnabled() {
        if(firstTextEntered && secondTextEntered && thirdTextEntered)
            binding.btnDataDone.setEnabled(true);
        else
            binding.btnDataDone.setEnabled(false);
    }

    public interface ISetupUserDetails{
        public void setupComplete();
    }
}