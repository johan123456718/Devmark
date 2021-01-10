package com.example.devmark.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devmark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * An adapter for chats
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private Context context;
    private List<Chat> listOfChats;

    private static final int RECIEVER = 1;
    private static final int SENDER = 0;
    private FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> listOfChats){
        this.context = context;
        this.listOfChats = listOfChats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == SENDER){
            view = LayoutInflater.from(context).inflate(R.layout.chatbubble_rightitem, parent, false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.chatbubble_leftitem, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = listOfChats.get(position);
        if(chat != null) {
            holder.message.setText(chat.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(!listOfChats.get(position).getSender().equals(firebaseUser.getUid())){
            return RECIEVER;
        }else{
            return SENDER;
        }
    }

    @Override
    public int getItemCount() {
        return listOfChats.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView message;

        public ViewHolder(View itemView){
            super(itemView);
            message = itemView.findViewById(R.id.popMessage);
        }

    }
}
