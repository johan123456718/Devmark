package com.example.devmark.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A class for login
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText email, password;
    private Button loginButton;
    private FirebaseAuth auth;
    private SignInButton googleLoginButton;
    private GoogleSignInClient googleSignInClient;
    private View rootView;
    private static final int RC_SIGN_IN = 1; //REQUEST_CODE
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_login, container, false);
        email = rootView.findViewById(R.id.email);
        password = rootView.findViewById(R.id.password);
        loginButton = rootView.findViewById(R.id.login);
        googleLoginButton = rootView.findViewById(R.id.googleLogin);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();

        googleSignInClient = GoogleSignIn.getClient(rootView.getContext(), googleSignInOptions);
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
        googleLoginButton.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        loginButton.setOnClickListener(null);
        googleLoginButton.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:
                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();

                if (getEmail.isEmpty() && getPassword.isEmpty()) {
                    email.setError("Missing!");
                    password.setError("Missing!");
                } else if (getEmail.isEmpty()) {
                    email.setError("Missing!");
                } else if (getPassword.isEmpty()) {
                    password.setError("Missing!");
                } else {
                    auth.signInWithEmailAndPassword(getEmail, getPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getActivity(), "Incorrect password or email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            break;

            case R.id.googleLogin:
                Intent loginIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(loginIntent, RC_SIGN_IN);
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> loginTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acc = loginTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
                auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();
                            addGoogleAccountInDatabase(acc, userId);
                            Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                            getActivity().finish();
                            startActivity(intent);
                        }else{
                            Toast.makeText(rootView.getContext(), "Sign in unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (ApiException e) {
                Toast.makeText(rootView.getContext(), "Sign in unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Adds account if registered through googleLogin
     */
    private void addGoogleAccountInDatabase(GoogleSignInAccount acc, String userId){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild(userId)) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("username", acc.getDisplayName());
                    hashMap.put("email", acc.getEmail());
                    hashMap.put("imageURL", "default");
                    databaseReference.setValue(hashMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
