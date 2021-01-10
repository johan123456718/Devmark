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
import com.example.devmark.model.Chat;
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
 * A fragment for current active chats
 */
public class ChatFragment extends Fragment implements ValueEventListener{

    private View rootView;
    private RecyclerView currentChatRecyclerView;
    private UserAdapter adapter;
    private List<User> listOfSenders;
    private List<String> listOfUserid;
    private FirebaseUser firebaseUser;
    private DatabaseReference chatDatabaseReference, userDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        chatDatabaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        listOfUserid = new ArrayList<>();
        listOfSenders = new ArrayList<>();
        currentChatRecyclerView = rootView.findViewById(R.id.currentChatRecyclerView);
        currentChatRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        chatDatabaseReference.addValueEventListener(this);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        listOfUserid.clear();

        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
            Chat chat = dataSnapshot.getValue(Chat.class);

            if(firebaseUser.getUid().equals(chat.getSender())){
                listOfUserid.add(chat.getReciever());
            }

        }

        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOfSenders.clear();
                int count = 0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    for(int i = 0; i < listOfUserid.size(); i++){
                        if(listOfUserid.get(i).equals(user.getId())){
                            count++;
                            if(count == 1){
                                listOfSenders.add(user);
                            }else{
                                count=0;
                            }
                        }
                    }
                }
                adapter = new UserAdapter(rootView.getContext(), listOfSenders);
                currentChatRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
