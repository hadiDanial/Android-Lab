package com.finalproject.chatapp.adapters;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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
    private ArrayList<User> userArrayList;
    private User activeUser;

    private FirebaseAuth firebaseAuth;
    private ISetActiveUser listener;

    public UserViewModel(@NonNull Application application) {
        super(application);
        users = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        userArrayList = new ArrayList<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Get all users from database + subscribe to changes in users
        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userArrayList.clear(); //initialize the list of the users
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    user.setuID(snapshot.getKey());
                    if (firebaseUser != null && user != null && user.getuID() != null)
                    {
                        if (!user.getuID().equals(firebaseUser.getUid())) // Add user to list of users
                            userArrayList.add(user);
                        else{ // Don't add self to list - set as active user
                            activeUser = user;
                            listener.setActiveUser(activeUser);
                        }
                    }
                }
                Log.i("", userArrayList.toString());
            users.setValue(userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(), "Failed to get all users!", Toast.LENGTH_LONG).show();
            }
        });

    }
    public interface ISetActiveUser{
        void setActiveUser(User activeUser);
    }

    public void setListener(ISetActiveUser listener){ this.listener = listener; }
    public MutableLiveData<ArrayList<User>> getUsers() {
        return users;
    }
}

