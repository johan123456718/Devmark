package com.example.devmark.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for ChatMenuActivity
 */
public class ViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> fragmentTitles;

    public ViewPageAdapter(FragmentManager fm){
        super(fm);
        fragments = new ArrayList<>();
        fragmentTitles = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        fragmentTitles.add(title);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
