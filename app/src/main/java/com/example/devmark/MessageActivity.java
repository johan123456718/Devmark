package com.example.devmark;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devmark.model.Chat;
import com.example.devmark.model.MessageAdapter;
import com.example.devmark.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Inspiration:
 * https://www.youtube.com/watch?v=BJkzVc2D0iY&list=PLzLFqCABnRQftQQETzoVMuteXzNiXmnj8&index=6
 * ------------
 * A class for handling chat conversation.
 */
public class MessageActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private Toolbar toolBar;
    private TextView username;

    private FirebaseUser firebaseUser;
    private DatabaseReference userDatabaseReference, chatDatabaseReference, getChatDatabaseReference;
    private List<Chat> listOfChats;
    private RecyclerView messageRecyclerView;
    private MessageAdapter adapter;
    private EditText messageText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        toolBar = findViewById(R.id.toolBar);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(getIntent().getStringExtra("userid"));

        chatDatabaseReference = FirebaseDatabase.getInstance().getReference();
        getChatDatabaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        username = findViewById(R.id.usernameMessage);
        messageText = findViewById(R.id.messageText);
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        listOfChats = new ArrayList<>();

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        toolBar.setNavigationOnClickListener(this);
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null) {
                    username.setText(user.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getChatDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOfChats.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    String reciever = getIntent().getStringExtra("userid");
                    String sender = firebaseUser.getUid();
                    if(chat != null) {
                        if (chat.getSender().equals(reciever) && chat.getReciever().equals(sender)) {
                            listOfChats.add(chat);
                        }
                        if(chat.getSender().equals(sender) && chat.getReciever().equals(reciever)){
                            listOfChats.add(chat);
                        }
                    }
                }
                adapter = new MessageAdapter(MessageActivity.this, listOfChats);
                messageRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        messageText.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        toolBar.setNavigationOnClickListener(null);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEND){
            String message = messageText.getText().toString();
            if(message.equals("")){
               Toast.makeText(this, "You can't send a empty string!", Toast.LENGTH_SHORT).show();
            }else{
                HashMap<String, String> chatList = new HashMap<>();
                chatList.put("sender", firebaseUser.getUid());
                chatList.put("reciever", getIntent().getStringExtra("userid"));
                chatList.put("message", message);

                chatDatabaseReference.child("Chats").push().setValue(chatList);
            }
            messageText.setText("");
        }
        return true;
    }
}
