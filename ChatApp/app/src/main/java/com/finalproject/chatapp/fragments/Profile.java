package com.finalproject.chatapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.finalproject.chatapp.ChatActivity;
import com.finalproject.chatapp.R;
import com.finalproject.chatapp.Utility;
import com.finalproject.chatapp.controllers.MessageController;
import com.finalproject.chatapp.controllers.UserController;
import com.finalproject.chatapp.models.Chat;
import com.finalproject.chatapp.models.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    private final User user;
    private View clearConversation, sendMessage, editButton, editActionViews, cancelChanges, saveChanges;
    private EditText displayNameText, firstNameText, lastNameText, statusText, phoneNumberText;
    private TextView onlineStatus;
    private View[] viewsForOtherUsers, viewsForLoggedInUser;
    private boolean isLoggedInUserProfile;

    public Profile(User user) {
        this.user = user;
        Log.i("", "Profile: " + user);
    }

    public static Profile newInstance(User user) {
        Profile fragment = new Profile(user);
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        String loggedInID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        isLoggedInUserProfile = loggedInID.equals(user.getuID());
        displayNameText  = view.findViewById(R.id.profileName);
        firstNameText = view.findViewById(R.id.profileFirstNameEdit);
        lastNameText = view.findViewById(R.id.profileLastNameEdit);
        statusText = view.findViewById(R.id.profileStatusEdit);
        phoneNumberText = view.findViewById(R.id.profilePhoneNumber);
        onlineStatus = view.findViewById(R.id.profileOnlineStatus);

        sendMessage = view.findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedInUserProfile) return;
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("userID", loggedInID);
                intent.putExtra("otherUserID", user.getuID());
                getContext().startActivity(intent);
            }
        });
        editButton = view.findViewById(R.id.editProfileDetails);
        editActionViews = view.findViewById(R.id.editActionsView);
        editActionViews.setVisibility(View.GONE);
        cancelChanges = view.findViewById(R.id.cancelChanges);
        saveChanges = view.findViewById(R.id.saveChanges);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setPhoneNumber(phoneNumberText.getText().toString().trim());
                user.setFirstName(firstNameText.getText().toString().trim());
                user.setLastName(lastNameText.getText().toString().trim());
                user.setDisplayName(displayNameText.getText().toString().trim());
                UserController.updateUser(user);
                setProfileEditable(false);
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0); // Hide keyboard
            }
        });
        cancelChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileEditable(false);
                setProfileDetails();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileEditable(true);
            }
        });

        clearConversation = view.findViewById(R.id.cardClearConversation);
        clearConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener positiveButtonAction = (dialog, id) -> MessageController.
                        clearChat(Chat.getChatID(FirebaseAuth.getInstance().getCurrentUser().getUid(), user.getuID()));

                DialogInterface.OnClickListener negativeButtonAction = (dialog, id) -> dialog.cancel();
                String alertMessage = "Are you sure you want to delete this conversation?";
                String positiveButtonText = "Delete", negativeButtonText = "Cancel";
                boolean isCancelable = true;

                Utility.generateAlertDialog(positiveButtonAction, negativeButtonAction, alertMessage, positiveButtonText, negativeButtonText, isCancelable, getContext());
            }
        });

        setVisibility();

        setProfileDetails();
    }

    private void setProfileDetails() {
        displayNameText.setText(user.getDisplayName());
        lastNameText.setText(user.getLastName());
        firstNameText.setText(user.getFirstName());
        statusText.setText(user.getStatus());
        phoneNumberText.setText(user.getPhoneNumber());
    }

    private void setProfileEditable(boolean enabled) {
        int bgColor = enabled ? R.color.white : R.color.transparent, textColor = enabled ? R.color.black : R.color.white;
        displayNameText.setEnabled(enabled);
        firstNameText.setEnabled(enabled);
        lastNameText.setEnabled(enabled);
        statusText.setEnabled(enabled);
        phoneNumberText.setEnabled(enabled);

        if(enabled)
            editActionViews.setVisibility(View.VISIBLE);
        else
            editActionViews.setVisibility(View.GONE);
    }

    private void setVisibility() {
        if(isLoggedInUserProfile)
        {
            editButton.setVisibility(View.VISIBLE);
            clearConversation.setVisibility(View.GONE);
            sendMessage.setVisibility(View.GONE);
            onlineStatus.setVisibility(View.GONE);
        }
        else
        {
            editButton.setVisibility(View.GONE);
            clearConversation.setVisibility(View.VISIBLE);
            sendMessage.setVisibility(View.VISIBLE);
            onlineStatus.setText(user.isOnline() ? "Online" : "Offline");
        }
    }
}