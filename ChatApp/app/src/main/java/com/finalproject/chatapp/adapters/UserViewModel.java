package com.finalproject.chatapp.adapters;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.finalproject.chatapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<User>> users;
    private FirebaseAuth firebaseAuth;
    private ArrayList<User> userArrayList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        users = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        userArrayList = new ArrayList<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");
        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userArrayList.clear(); //initialize the list of the users
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (firebaseUser != null && user != null && user.getuID() != null && !user.getDisplayName().equals(""))
                    {
                        if (!user.getuID().equals(firebaseUser.getUid())) //if the user id is different then current connected user
                            userArrayList.add(user);
//                        else
//                            myID = user.getuID(); //else set myID string value
//                        chatsViewModel.getSelected().observe((LifecycleOwner) context, item -> { //update the userAdapter about change in date and refresh the view
//                            userAdapter.notifyDataSetChanged();
                    }
                }
                Log.i("", userArrayList.toString());
            users.setValue(userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<ArrayList<User>> getUsers() {
        return users;
    }
}

