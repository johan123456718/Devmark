package com.example.devmark.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.devmark.R;
import com.example.devmark.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.polyak.iconswitch.IconSwitch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Inspiration for making the dialog
 * https://youtu.be/ARezg1D9Zd0
 * -------
 * Inspiration for google maps
 * https://www.youtube.com/watch?v=YCFPClPjDIQ
 * --------
 * Information on how to get the location of the country
 * https://developer.android.com/reference/android/location/Geocoder
 */
public class CreatePostDialog extends AppCompatDialogFragment implements DialogInterface.OnClickListener, OnMapReadyCallback{
    private EditText editTextProjectTitle, editDescription, editOtherRequirements;
    private SupportMapFragment supportMapFragment;
    private String countryLocation;
    private IconSwitch iconSwitchSQL, iconSwitchCsharp,
            iconSwitchJava, iconSwitchJs, iconSwitchHtml,
            iconSwitchPython, iconSwitchCplus, iconSwitchC, iconSwitchSwift,
            iconSwitchCss, iconSwitchPhp, iconSwitchR;
    private List<String> getAllCheckedRequirements;
    private View view;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.create_post_window, null);

        supportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        if(supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }

        editTextProjectTitle = view.findViewById(R.id.title);
        editDescription = view.findViewById(R.id.description);
        editOtherRequirements = view.findViewById(R.id.other);
        getAllCheckedRequirements = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        iconInit();

        builder.setView(view)
                .setTitle("Create your post here")
                .setNegativeButton("Cancel", this)
                .setPositiveButton("Done", this);

        return builder.create();
    }

    private void iconInit(){
        iconSwitchSQL = view.findViewById(R.id.requireSQL);
        iconSwitchCsharp = view.findViewById(R.id.requireCsharp);
        iconSwitchJava= view.findViewById(R.id.requireJava);
        iconSwitchJs= view.findViewById(R.id.requireJavascript);
        iconSwitchHtml = view.findViewById(R.id.requireHtml);
        iconSwitchPython = view.findViewById(R.id.python);
        iconSwitchCplus = view.findViewById(R.id.requireCplus);
        iconSwitchC = view.findViewById(R.id.requireClanguage);
        iconSwitchSwift = view.findViewById(R.id.requireSwift);
        iconSwitchCss = view.findViewById(R.id.requiredCss);
        iconSwitchPhp = view.findViewById(R.id.requirePhp);
        iconSwitchR = view.findViewById(R.id.requireR);
    }


    @Override
    public void onStart() {
        super.onStart();
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
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case BUTTON_NEGATIVE:
                dialog.dismiss();
            break;

            case BUTTON_POSITIVE:
                String projectTitle = editTextProjectTitle.getText().toString();
                String description = editDescription.getText().toString();
                String otherRequirements = editOtherRequirements.getText().toString();

                if(!otherRequirements.isEmpty()) {
                    getAllCheckedRequirements.add(otherRequirements);
                }
                FirebaseUser firebaseUser = auth.getCurrentUser();
                String userId = firebaseUser.getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        String userName = user.getUsername();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");

                        StringBuilder sb = new StringBuilder();
                        for(String requirement : getAllCheckedRequirements){
                            sb.append(requirement);
                            sb.append(", ");
                        }

                        HashMap<String, String> list = new HashMap<>();
                        list.put("id", userId);
                        list.put("creator", userName);
                        list.put("project_title", projectTitle);
                        String id = databaseReference.push().getKey();
                        list.put("project_id", id);
                        list.put("description", description);
                        list.put("requirements", sb.toString());
                        list.put("location", countryLocation);


                        databaseReference.child(id).setValue(list).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(view.getContext(), "Post successful!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(view.getContext(), "Post unsuccessful!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                });

            dialog.dismiss();
            break;
        }
    }

        @Override
        public void onDestroyView() {
            super.onDestroyView();

            if(supportMapFragment != null){
                getFragmentManager().beginTransaction().remove(supportMapFragment).commit();
            }

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(latLng);

                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                try {
                    List<Address> getFromLocationClicked = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    countryLocation = getFromLocationClicked.get(0).getCountryName() + " " + getFromLocationClicked.get(0).getAdminArea();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                googleMap.clear();

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                googleMap.addMarker(markerOptions);
            }
        });
    }
}
