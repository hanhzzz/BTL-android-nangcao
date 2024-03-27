package com.example.btl_adr_nangcao.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseFirebaseClass extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    public String TAG = "hanhzzz";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set firebase
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
}


