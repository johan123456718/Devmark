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

import com.example.devmark.R;
import com.example.devmark.model.User;
import com.example.devmark.model.UserAdapter;
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
 * A fragment to find available users to chat with
 */
public class AvailableUserFragment extends Fragment implements ValueEventListener{

    private View rootView;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_availableusers, container, false);

        recyclerView = rootView.findViewById(R.id.availableUserRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        userList = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(firebaseUser != null) {
            databaseReference.addValueEventListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        userList.clear();

        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
            User user = dataSnapshot.getValue(User.class);
            if(!user.getEmail().equals(firebaseUser.getEmail())) {
                userList.add(user);
            }
        }
        adapter = new UserAdapter(rootView.getContext(), userList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
