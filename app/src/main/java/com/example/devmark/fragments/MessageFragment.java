package com.example.devmark.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.devmark.R;
import com.example.devmark.model.AppliedPostAdapter;
import com.example.devmark.model.AppliedPosts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * A class for handling requests to projects
 */
public class MessageFragment extends Fragment implements ValueEventListener{
    private View rootView;
    private DatabaseReference requestDatabaseReference;
    private List<AppliedPosts> appliedPostList;
    private RecyclerView appliedProjectRecyclerView;
    private AppliedPostAdapter adapter;
    private FirebaseUser firebaseUser;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_message, container, false);
        requestDatabaseReference = FirebaseDatabase.getInstance().getReference("Requests");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        appliedPostList = new ArrayList<>();
        appliedProjectRecyclerView = rootView.findViewById(R.id.appliedProjectRecyclerView);
        appliedProjectRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        swipeRefreshLayout = rootView.findViewById(R.id.refreshAppliedProject);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestDatabaseReference.addValueEventListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        requestDatabaseReference.removeEventListener(this);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        appliedPostList.clear();
        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
            AppliedPosts appliedPost = dataSnapshot.getValue(AppliedPosts.class);
            if(appliedPost != null && firebaseUser != null){
                if(firebaseUser.getUid().equals(appliedPost.getCreator())) {
                    appliedPostList.add(appliedPost);
                }
            }
            adapter = new AppliedPostAdapter(rootView.getContext(), appliedPostList);
            appliedProjectRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
