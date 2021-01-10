package com.example.devmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devmark.fragments.HomeFragment;
import com.example.devmark.fragments.LoginFragment;
import com.example.devmark.fragments.MessageFragment;
import com.example.devmark.fragments.ProfileFragment;
import com.example.devmark.fragments.RegisterFragment;
import com.example.devmark.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Inspiration for NavigationView, there are 3 parts:
 * https://www.youtube.com/watch?v=fGcMLu1GJEc&list=RDCMUC_Fh8kvtkVPkeihBs42jGcA&index=6
 * https://www.youtube.com/watch?v=zYVEMCiDcmY&list=RDCMUC_Fh8kvtkVPkeihBs42jGcA&index=2
 * https://www.youtube.com/watch?v=bjYstsO1PgI&list=RDCMUC_Fh8kvtkVPkeihBs42jGcA&index=1
 * --------------
 * A class for handling the menu, activities and fragments
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private FirebaseUser firebaseUser;
    private TextView navUsername;
    private ImageView navImage;
    private DatabaseReference reference;
    private Menu menu;
    private MenuItem logoutItem, loginItem, registerItem;
    private View navHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.theMenu);
        navigationView = findViewById(R.id.nav_view);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null) {
             reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        }
        navHeader = navigationView.getHeaderView(0);
        navUsername = navHeader.findViewById(R.id.navUsername);
        navImage = navHeader.findViewById(R.id.navImage);

        menu = navigationView.getMenu();
        loginItem = menu.findItem(R.id.login);
        logoutItem = menu.findItem(R.id.logout);
        registerItem = menu.findItem(R.id.register);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.openDrawer,R.string.closeDrawer);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.menuContainer,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        if(firebaseUser != null){
            logoutItem.setVisible(true);
            loginItem.setVisible(false);
            registerItem.setVisible(false);
        }else{
            logoutItem.setVisible(false);
            loginItem.setVisible(true);
            registerItem.setVisible(true);
            navImage.setVisibility(View.GONE);
        }
        if(firebaseUser != null) {
            reference.addValueEventListener(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        drawerLayout.removeDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment currentFragment = null;
        switch(item.getItemId()){

            case R.id.home:
                currentFragment = new HomeFragment();
            break;

            case R.id.request:
                if (firebaseUser != null) {
                    currentFragment = new MessageFragment();
                }else{
                    Toast.makeText(this, "In order to access 'requests', you need to be signed in!", Toast.LENGTH_SHORT).show();
                }
            break;

            case R.id.chat:
                if (firebaseUser != null) {
                    Intent intent = new Intent(this, ChatMenuActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "In order to access 'chat', you need to be signed in!", Toast.LENGTH_SHORT).show();
                }
            break;

            case R.id.profile:
                if (firebaseUser != null) {
                    currentFragment = new ProfileFragment();
                }else{
                    Toast.makeText(this, "In order to access 'profile', you need to be signed in!", Toast.LENGTH_SHORT).show();
                }
            break;

            case R.id.login:
                currentFragment = new LoginFragment();
            break;

            case R.id.register:
                currentFragment = new RegisterFragment();
            break;

            case R.id.logout:
                GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
                googleSignInClient.signOut();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(getIntent());
            break;

        }
        if(currentFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.menuContainer,
                    currentFragment).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.getValue(User.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            navUsername.setText(account.getDisplayName());
        }else if(user != null) {
            navUsername.setText(user.getUsername());
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}