package com.example.devmark.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.devmark.R;
import com.example.devmark.model.DialogListener;
import com.example.devmark.ui.CreatePostDialog;

import java.util.List;

/**
 * Inspiration for window dialog:
 * https://gits-15.sys.kth.se/anderslm/Ble-Gatt/tree/master/app/src/main/java/se/kth/anderslm/ble/uistuff
 */
public class HomeFragment extends Fragment implements View.OnClickListener, DialogListener {
    private Button createPost;
    private View rootView;
    private TextView textViewProjectTitle, textViewDescription, textViewRequirements, textViewLocation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_home, container, false);
        createPost = rootView.findViewById(R.id.createPost);
        textViewProjectTitle = rootView.findViewById(R.id.projectTitle);
        textViewDescription = rootView.findViewById(R.id.description);
        textViewRequirements = rootView.findViewById(R.id.requirements);
        textViewLocation = rootView.findViewById(R.id.country);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        createPost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createPost:
                CreatePostDialog createPostDialog = new CreatePostDialog();
                createPostDialog.setTargetFragment(HomeFragment.this, 1);
                createPostDialog.show(getFragmentManager(), "Create post");
            break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        createPost.setOnClickListener(null);
    }

    @Override
    public void applyTextContent(String projectTitle, String description, List<String> requirements, String country) {
        textViewProjectTitle.setText(projectTitle);
        textViewDescription.setText(description);
        StringBuilder requirementsString = new StringBuilder();
        for(int i = 0; i < requirements.size(); i++) {
            requirementsString.append(requirements.get(i)).append(", ");
        }
        textViewRequirements.setText(requirementsString.toString());
        textViewLocation.setText(country);
    }
}
