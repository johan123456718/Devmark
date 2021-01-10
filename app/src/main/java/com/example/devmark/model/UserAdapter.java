package com.example.devmark.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devmark.MessageActivity;
import com.example.devmark.R;

import java.util.List;

/**
 * Adapter for users
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<User> listOfUsers;

    public UserAdapter(Context context, List<User> listOfUsers){
        this.context = context;
        this.listOfUsers = listOfUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.availableuser_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = listOfUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                Log.d("TAG", "onClick: " + user.getId());
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username, email;
        private ImageView image;


        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.availableUserUsername);
            email = itemView.findViewById(R.id.availableUserEmail);
            image = itemView.findViewById(R.id.userImage);
        }

    }
}
