package com.example.pigeonlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate LOGIN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerBtn = findViewById(R.id.main_register_btn);
        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        Button loginBtn = findViewById(R.id.main_login_btn);
        loginBtn.setOnClickListener(view -> {
            EditText userName = findViewById(R.id.main_username);
            EditText password = findViewById(R.id.main_password);
            if(!userName.getText().toString().equals("") && !password.getText().toString().equals("")) {
                // *** validate with server  username and password. ***
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("username", userName.getText().toString());
                startActivity(intent);
            }
            else {
                TextView warningMessage = findViewById(R.id.main_warning_message);
                warningMessage.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = warningMessage.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                warningMessage.setLayoutParams(params);
            }
        });

        /*
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                MainActivity.this, instanceIdResult -> {
                   String token = instanceIdResult.getToken();
                });
         */
    }

}