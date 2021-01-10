package com.example.devmark.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devmark.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter for the requests to posts
 */
public class AppliedPostAdapter extends RecyclerView.Adapter<AppliedPostAdapter.ViewHolder>{

    private Context context;
    private List<AppliedPosts> listOfAppliedPost;
    private List<Post> listOfPosts;
    private DatabaseReference userDatabaseReference;
    private DatabaseReference postDatabaseReference;
    private DatabaseReference requestDatabaseReference;
    private String post_key, post_key2;

    public AppliedPostAdapter(Context context, List<AppliedPosts> listOfAppliedPost){
        this.context = context;
        this.listOfAppliedPost = listOfAppliedPost;
        this.listOfPosts = new ArrayList<>();
        this.userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        this.postDatabaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        this.requestDatabaseReference = FirebaseDatabase.getInstance().getReference("Requests");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.applied_post_item, parent, false);
        return new AppliedPostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppliedPosts appliedPosts = listOfAppliedPost.get(position);
        holder.appliedProjectName.setText("Project title: "  + appliedPosts.getProject_title());
        holder.appliedDescription.setText("Description: "  + appliedPosts.getDescription());

        userDatabaseReference.child(appliedPosts.getRequester()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                    holder.appliedRequester.setText("Requester: " + user.getUsername());

                    postDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            listOfPosts.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Post post = dataSnapshot.getValue(Post.class);
                                if (post != null) {
                                    if (post.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                        listOfPosts.add(post);
                                    }
                                }
                            }
                            if(position >= 0 && position < listOfPosts.size()){
                                post_key = listOfPosts.get(position).getProject_id();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    holder.applyAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(!user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                postDatabaseReference.child(post_key).child("contributers").push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Applied user to your project", Toast.LENGTH_SHORT).show();
                                        String tmp = listOfAppliedPost.get(position).getRequest_id();
                                        requestDatabaseReference.child(tmp).removeValue();
                                        listOfAppliedPost.remove(position);
                                        listOfPosts.remove(position);
                                    }
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.applyDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_key2 = listOfAppliedPost.get(position).getRequest_id();
                requestDatabaseReference.child(post_key2).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Removed applied user from your request", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfAppliedPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView appliedProjectName, appliedDescription, appliedRequester;
        private Button applyAccept, applyDeny;
        private ImageView image;

        public ViewHolder(View itemView){
            super(itemView);
            appliedProjectName = itemView.findViewById(R.id.appliedProjectName);
            appliedDescription = itemView.findViewById(R.id.appliedDescription);
            appliedRequester = itemView.findViewById(R.id.appliedRequester);
            applyAccept = itemView.findViewById(R.id.applyAccept);
            applyDeny = itemView.findViewById(R.id.applyDeny);
            image = itemView.findViewById(R.id.userImage);
        }

    }
}