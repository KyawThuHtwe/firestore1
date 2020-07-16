package com.cu.firestore1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String NAME_KEY="Name";
    private static final String EMAIL_KEY="Email";
    private static final String PHONE_KEY="Phone";

    FirebaseFirestore db;
    TextView textDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         db=FirebaseFirestore.getInstance();
         textDisplay=findViewById(R.id.text);
addNewContact();
    }

    private void addNewContact(){
        Map<String,Object> newContact=new HashMap<>();
        newContact.put(NAME_KEY,"John");
        newContact.put(EMAIL_KEY,"john@gmail.com");
        newContact.put(PHONE_KEY,"0500050000");
        db.collection("PhoneBook").document("Contact").set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        Log.d("TAG",e.toString());
                    }
                });

    }

    private void ReadSingleContact(){
        DocumentReference user=db.collection("PhoneBook").document("Contact");
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();
                    StringBuilder fields=new StringBuilder("");
                    fields.append("Name: ").append(doc.get("Name"));
                    fields.append("\nEmail: ").append(doc.get("Email"));
                    fields.append("\nPhone: ").append(doc.get("Phone"));
                    textDisplay.setText(fields.toString());

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void UpdateData(){
        DocumentReference contact=db.collection("PhoneBook").document("Contact");
        contact.update("NAME_KEY","Kenny");
        contact.update("EMAIL_KEY","kenny@gmail.com");
        contact.update("PHONE_KEY","090911419")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"Updated Successful",Toast.LENGTH_SHORT).show();

                    }
                });

    }
}