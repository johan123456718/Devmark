package com.example.devmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.devmark.fragments.ChatFragment;
import com.example.devmark.fragments.HomeFragment;
import com.example.devmark.fragments.LoginFragment;
import com.example.devmark.fragments.MessageFragment;
import com.example.devmark.fragments.ProfileFragment;
import com.example.devmark.fragments.RegisterFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.theMenu);
        navigationView = findViewById(R.id.nav_view);
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
            case R.id.message:
               currentFragment = new MessageFragment();
            break;

            case R.id.chat:
                currentFragment = new ChatFragment();
            break;

            case R.id.profile:
                currentFragment = new ProfileFragment();
            break;

            case R.id.login:
                currentFragment = new LoginFragment();
            break;

            case R.id.register:
                currentFragment = new RegisterFragment();
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
}