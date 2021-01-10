package com.example.devmark.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.devmark.MainActivity;
import com.example.devmark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A fragment for register an account
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText username, email, password, passwordConfirmation;
    private Button registerButton;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_register, container, false);
        username = rootView.findViewById(R.id.username);
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        passwordConfirmation = rootView.findViewById(R.id.passwordConfirmation);
        registerButton = rootView.findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String getUserName = username.getText().toString();
        String getEmail = email.getText().toString();
        String getPassword = password.getText().toString();
        String getPasswordConfirmation = passwordConfirmation.getText().toString();

        if(getUserName.isEmpty() && getEmail.isEmpty() && getPassword.isEmpty() && getPasswordConfirmation.isEmpty()){
            username.setError("Missing!");
            email.setError("Missing!");
            password.setError("Missing!");
            passwordConfirmation.setError("Missing!");
        }else if(getUserName.isEmpty()) {
            username.setError("Missing!");
        }
        else if(getEmail.isEmpty()) {
            email.setError("Missing!");
        }
        else if(getPassword.isEmpty()){
            password.setError("Missing!");

        }else if(getPasswordConfirmation.isEmpty()){
            passwordConfirmation.setError("Missing!");
        }
        else if(getPassword.length() <= 5){
            password.setError("Password must be at least 6 characters long!");
        }

        if(getPassword.equals(getPasswordConfirmation)){
            register(getEmail, getUserName, getPassword);
        }else{
            passwordConfirmation.setError("The password must be the same!");
        }
    }

    private void register(String email, String username, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", username);
                            hashMap.put("email", email);
                            hashMap.put("imageURL", "default");

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(rootView.getContext(), "Register successful! Please check your email for verification (Current in spam folder) ", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                                    getActivity().finish();
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(rootView.getContext(), "Failed to send email verification", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getContext(), "You can't register with this email or password", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        registerButton.setOnClickListener(null);
    }
}
