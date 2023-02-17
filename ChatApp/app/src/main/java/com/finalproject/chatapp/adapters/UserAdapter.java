package com.finalproject.chatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.chatapp.ChatActivity;
import com.finalproject.chatapp.R;
import com.finalproject.chatapp.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private final UserViewModel userViewModel;
    private final FragmentManager fragmentManager;
    private final Context context;
    private ArrayList<User> usersArrayList;

    private Observer<ArrayList<User>> userObservable = new Observer<ArrayList<User>>() {
        @Override
        public void onChanged(ArrayList<User> users) {
            usersArrayList = users;
            notifyDataSetChanged();
        }
    };

    public UserAdapter(Context context, UserViewModel userViewModel, FragmentManager fragmentManager) {
        this.userViewModel = userViewModel;
        usersArrayList = new ArrayList<>();
        this.fragmentManager = fragmentManager;
        this.userViewModel.getUsers().observe((LifecycleOwner) context, userObservable);
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View userView = inflater.inflate(R.layout.contact_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new UserAdapter.ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = usersArrayList.get(position);
        holder.setUser(user);
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView status, name, message;
        private User user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.txtUserStatus);
            name = itemView.findViewById(R.id.txtUserName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent.putExtra("otherUserID", user.getuID());
                    context.startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Todo: Open selected user profile
                    return false;
                }
            });
        }

        public void setUser(User user) {
            this.user = user;
            status.setText(user.isOnline() ? "Online" : "Offline");
            name.setText(user.getName());
        }
    }
}
