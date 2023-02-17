package com.finalproject.chatapp.adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.chatapp.R;
import com.finalproject.chatapp.Utility;
import com.finalproject.chatapp.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final MessageViewModel messageViewModel;
    private final FragmentManager fragmentManager;
    private final Context context;
    private ArrayList<Message> messageArrayList;
    private String activeUserID;
    private IMessageClickListener listener;
    private Observer<ArrayList<Message>> messageObservable = new Observer<ArrayList<Message>>() {
        @Override
        public void onChanged(ArrayList<Message> messages) {
            messageArrayList = messages;
            notifyDataSetChanged();
        }
    };

    public MessageAdapter(Context context, MessageViewModel messageViewModel, FragmentManager fragmentManager, IMessageClickListener listener) {
        this.messageViewModel = messageViewModel;
        messageArrayList = new ArrayList<>();
        this.fragmentManager = fragmentManager;
        this.messageViewModel.getMessages().observe((LifecycleOwner) context, messageObservable);
        this.context = context;
        this.listener = listener;
        activeUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view;
        if (viewType == 1) // Current User
            view = inflater.inflate(R.layout.right_bubble_layout, parent, false);
        else
            view = inflater.inflate(R.layout.left_bubble_layout, parent, false);

        // Return a new holder instance
        MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return (isCurrentUser(messageArrayList.get(position)) ? 1 : 2);
    }

    private boolean isCurrentUser(Message message) {
        return message.getUserID().equals(activeUserID);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setMessage(messageArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageText, messageDate;
        private RelativeLayout layout;
        private Message message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.txtMessage);
            messageDate = itemView.findViewById(R.id.messageDate);
            layout = itemView.findViewById(R.id.rightLayout);
        }

        public void setMessage(Message message) {
            this.message = message;
            messageText.setText(message.getContent());
            messageDate.setText(Utility.getFormattedDate(message.getDate()));
            layout = isCurrentUser(message) ? itemView.findViewById(R.id.rightLayout) : itemView.findViewById(R.id.leftLayout);
            boolean isCurrentUser = isCurrentUser(message);

            int colorFrom, colorTo;
            colorFrom = context.getColor(isCurrentUser ? R.color.rightBubbleBackground : R.color.leftBubbleBackground);
            colorTo = context.getColor(isCurrentUser ? R.color.rightBubbleBackgroundPressed: R.color.leftBubbleBackgroundPressed);

            ValueAnimator colorAnimation = Utility.getValueAnimatorLooped(layout, colorFrom, colorTo, 250);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorAnimation.start();
                }
            });
            layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (isCurrentUser(message)) {
                        listener.DeleteMessage(message);
                        return true;
                    } else return false;
                }
            });
        }


    }

    public interface IMessageClickListener {
        public void DeleteMessage(Message message);
    }
}
