package com.example.devmark.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devmark.R;
import com.example.devmark.ui.ApplyProjectDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Adapter for posts
 */
public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.PostViewHolder> {
    private Context context;
    public PostAdapter(FirebaseRecyclerOptions<Post> options, Context context){
        super(options);
        this.context = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
        holder.creator.setText(model.getCreator());
        holder.projectTitle.setText(model.getProject_title());
        holder.description.setText(model.getDescription());
        holder.requirements.setText(model.getRequirements());
        holder.location.setText(model.getLocation());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser != null) {
                    if (!firebaseUser.getUid().equals(model.getId())) {
                        ApplyProjectDialog applyProjectDialog = new ApplyProjectDialog(context);
                        String title = "Apply to this project";
                        String message = "Do you wish to apply to this project?";

                        applyProjectDialog.createDialog(title, message, firebaseUser.getUid(), model.getId(), model.getProject_title(), model.getDescription());
                        applyProjectDialog.showDialog();
                        
                    } else {
                        Toast.makeText(context, "You can't apply to yourself!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "You need to be logged in to apply to a post", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
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
