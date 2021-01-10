package com.example.devmark.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A dialog for creating a request and send it to firebase database
 */
public class ApplyProjectDialog {

    private Context context;
    private AlertDialog.Builder builder;
    private DatabaseReference requestDatabaseReference;

    public ApplyProjectDialog(Context context){
        this.context = context;
        builder = new AlertDialog.Builder(context);
        requestDatabaseReference = FirebaseDatabase.getInstance().getReference("Requests");
    }

    public void createDialog(String title, String message, String requester, String creator, String projectTitle, String description){
        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, String> requestList = new HashMap<>();
                        requestList.put("creator", creator);
                        requestList.put("requester", requester);
                        requestList.put("project_title", projectTitle);
                        String post_key = requestDatabaseReference.push().getKey();
                        requestList.put("request_id", post_key);
                        requestList.put("description", description);
                        requestDatabaseReference.child(post_key).setValue(requestList);
                    }
                });

        builder.create();
    }

    public void showDialog(){
        if(builder != null) {
            builder.show();
        }
    }
}
