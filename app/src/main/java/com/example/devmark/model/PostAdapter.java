package com.example.devmark.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devmark.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.PostViewHolder> {

    public PostAdapter(FirebaseRecyclerOptions<Post> options){
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
        holder.creator.setText(model.getCreator());
        holder.projectTitle.setText(model.getProject_title());
        holder.description.setText(model.getDescription());
        holder.requirements.setText(model.getRequirements());
        holder.location.setText(model.getLocation());
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts, parent, false);
        return new PostAdapter.PostViewHolder(view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        TextView creator, projectTitle, description, requirements, location;

        public PostViewHolder(View itemView){
            super(itemView);
            creator = itemView.findViewById(R.id.creator);
            projectTitle = itemView.findViewById(R.id.projectTitle);
            description = itemView.findViewById(R.id.description);
            requirements = itemView.findViewById(R.id.requirements);
            location = itemView.findViewById(R.id.location);
        }
    }
}
