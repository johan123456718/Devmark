package com.example.devmark.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.devmark.R;
import com.example.devmark.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A fragment for checking the current logged in user's profile
 */
public class ProfileFragment extends Fragment implements ValueEventListener {

    private View rootView;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private TextView usernameProfile, emailProfile, isVerifiedText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_profile, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        usernameProfile = rootView.findViewById(R.id.usernameProfile);
        emailProfile = rootView.findViewById(R.id.emailProfile);
        isVerifiedText = rootView.findViewById(R.id.isVerified);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.addValueEventListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.getValue(User.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(rootView.getContext());
        if(account != null){
            usernameProfile.setText(account.getDisplayName());
            emailProfile.setText(account.getEmail());
            isVerifiedText.setVisibility(View.INVISIBLE);
        }else if(user != null) {
            usernameProfile.setText(user.getUsername());
            emailProfile.setText(user.getEmail());
            if (firebaseUser.isEmailVerified()) {
                isVerifiedText.setText("Is verified: Yes");
            } else {
                isVerifiedText.setText("Is verified: No");
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
