package com.example.devmark;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.devmark.fragments.AvailableUserFragment;
import com.example.devmark.fragments.ChatFragment;
import com.example.devmark.model.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * Activity for the chats
 */
public class ChatMenuActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new ChatFragment(), "Your chat");
        viewPageAdapter.addFragment(new AvailableUserFragment(), "Users to contact");
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewPager.setAdapter(null);
        tabLayout.setupWithViewPager(null);
    }
}
