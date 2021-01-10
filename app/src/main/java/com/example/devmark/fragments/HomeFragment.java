package com.example.devmark.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devmark.R;
import com.example.devmark.model.Post;
import com.example.devmark.model.PostAdapter;
import com.example.devmark.ui.CreatePostDialog;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.polyak.iconswitch.IconSwitch;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspiration for window dialog:
 * https://gits-15.sys.kth.se/anderslm/Ble-Gatt/tree/master/app/src/main/java/se/kth/anderslm/ble/uistuff
 *
 * -------
 * A fragment for home
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    private Button createPost;
    private View rootView;
    private FirebaseUser firebaseUser;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private FirebaseRecyclerOptions<Post> options;
    private IconSwitch iconSwitchSQL, iconSwitchCsharp,
            iconSwitchJava, iconSwitchJs, iconSwitchHtml,
            iconSwitchPython, iconSwitchCplus, iconSwitchC, iconSwitchSwift,
            iconSwitchCss, iconSwitchPhp, iconSwitchR;
    private List<String> getAllCheckedRequirements;
    private EditText searchButton;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_home, container, false);
        createPost = rootView.findViewById(R.id.createPost);

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(databaseReference, Post.class)
                    .build();
        adapter = new PostAdapter(options, rootView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        getAllCheckedRequirements = new ArrayList<>();
        searchButton = rootView.findViewById(R.id.searchButton);
        iconInit();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return rootView;
    }

    private void iconInit(){
        iconSwitchSQL = rootView.findViewById(R.id.SQL);
        iconSwitchCsharp = rootView.findViewById(R.id.csharp);
        iconSwitchJava= rootView.findViewById(R.id.java);
        iconSwitchJs= rootView.findViewById(R.id.javascript);
        iconSwitchHtml = rootView.findViewById(R.id.html);
        iconSwitchPython = rootView.findViewById(R.id.python);
        iconSwitchCplus = rootView.findViewById(R.id.cPlus);
        iconSwitchC = rootView.findViewById(R.id.cLanguage);
        iconSwitchSwift = rootView.findViewById(R.id.swift);
        iconSwitchCss = rootView.findViewById(R.id.css);
        iconSwitchPhp = rootView.findViewById(R.id.php);
        iconSwitchR = rootView.findViewById(R.id.R);
    }

    @Override
    public void onStart() {
        super.onStart();
        createPost.setOnClickListener(this);
        adapter.startListening();

        iconSwitchSQL.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("SQL");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("SQL");
                        break;
                }
            }
        });
        iconSwitchCsharp.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("C#");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("C#");
                        break;
                }
            }
        });
        iconSwitchJava.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("Java");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("Java");
                        break;
                }
            }
        });
        iconSwitchJs.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("Javascript");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("Javascript");
                        break;
                }
            }
        });
        iconSwitchHtml.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("HTML");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("HTML");
                        break;
                }
            }
        });
        iconSwitchPython.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("Python");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("Python");
                        break;
                }
            }
        });
        iconSwitchCplus.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("C++");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("C++");
                        break;
                }
            }
        });
        iconSwitchC.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("C");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("C");
                        break;
                }
            }
        });
        iconSwitchSwift.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("Swift");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("Swift");
                        break;
                }
            }
        });

        iconSwitchCss.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("Css");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("Css");
                        break;
                }
            }
        });

        iconSwitchPhp.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("Php");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("Php");
                        break;
                }
            }
        });

        iconSwitchR.setCheckedChangeListener(new IconSwitch.CheckedChangeListener() {
            @Override
            public void onCheckChanged(IconSwitch.Checked current) {
                switch(current){
                    case RIGHT:
                        getAllCheckedRequirements.add("R");
                        break;

                    case LEFT:
                        getAllCheckedRequirements.remove("R");
                        break;
                }
            }
        });
        searchButton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable string) {
                String queryText = string.toString().trim();

                if(!queryText.isEmpty()) {
                    Query query = databaseReference.orderByChild("project_title").startAt(queryText).endAt(queryText+"\uf8ff"); //escape character
                    options = new FirebaseRecyclerOptions.Builder<Post>()
                            .setQuery(query, Post.class)
                            .build();
                    adapter = new PostAdapter(options, rootView.getContext());
                    adapter.startListening();
                    recyclerView.setAdapter(adapter);
                }else{
                    options = new FirebaseRecyclerOptions.Builder<Post>()
                            .setQuery(databaseReference, Post.class)
                            .build();
                    adapter = new PostAdapter(options, rootView.getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createPost:
                if(firebaseUser != null) {
                    CreatePostDialog createPostDialog = new CreatePostDialog();
                    createPostDialog.setTargetFragment(HomeFragment.this, 1);
                    createPostDialog.show(getFragmentManager(), "Create post");
                }else{
                    Toast.makeText(rootView.getContext(), "You need to be logged in to create a post", Toast.LENGTH_SHORT).show();
                }
            break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        createPost.setOnClickListener(null);
        adapter.stopListening();
        iconSwitchSQL.setCheckedChangeListener(null);
        iconSwitchCsharp.setCheckedChangeListener(null);
        iconSwitchJava.setCheckedChangeListener(null);
        iconSwitchJs.setCheckedChangeListener(null);
        iconSwitchHtml.setCheckedChangeListener(null);
        iconSwitchPython.setCheckedChangeListener(null);
        iconSwitchCplus.setCheckedChangeListener(null);
        iconSwitchC.setCheckedChangeListener(null);
        iconSwitchSwift.setCheckedChangeListener(null);
        iconSwitchCss.setCheckedChangeListener(null);
        iconSwitchPhp.setCheckedChangeListener(null);
        iconSwitchR.setCheckedChangeListener(null);
    }
}
