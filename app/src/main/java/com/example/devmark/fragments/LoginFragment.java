package com.example.devmark.fragments;

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

import com.example.devmark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment implements View.OnClickListener{


    private EditText email, password;
    private Button loginButton;
    private FirebaseAuth auth;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_login, container, false);
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        loginButton = rootView.findViewById(R.id.login);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        loginButton.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        String getEmail = email.getText().toString();
        String getPassword = password.getText().toString();

        if(getEmail.isEmpty() && getPassword.isEmpty()){
            email.setError("Missing!");
            password.setError("Missing!");
        }
        else if(getEmail.isEmpty()) {
            email.setError("Missing!");
        }
        else if(getPassword.isEmpty()){
            password.setError("Missing!");
        }else{
            auth.signInWithEmailAndPassword(getEmail, getPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                getFragmentManager().beginTransaction().replace(R.id.menuContainer,
                                        new HomeFragment()).commit();
                            }else{
                                Toast.makeText(getActivity(), "Incorrect password or email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
