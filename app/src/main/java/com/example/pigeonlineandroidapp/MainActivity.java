package com.example.pigeonlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button registerBtn = findViewById(R.id.main_register_btn);
        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        /*
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                MainActivity.this, instanceIdResult -> {
                   String token = instanceIdResult.getToken();
                });
         */
    }
}