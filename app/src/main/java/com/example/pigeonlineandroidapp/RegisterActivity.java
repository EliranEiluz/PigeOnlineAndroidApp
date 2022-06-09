package com.example.pigeonlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate REGISTER");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button loginBtn = findViewById(R.id.register_login_btn);
        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Button registerBtn = findViewById(R.id.register_register_btn);
        registerBtn.setOnClickListener(view -> {
            TextView warningMessage = findViewById(R.id.register_warning_message);
            EditText userName = findViewById(R.id.register_username);
            ViewGroup.LayoutParams params = warningMessage.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            if(userName.getText().toString().equals("")) {
                warningMessage.setLayoutParams(params);
                warningMessage.setVisibility(View.VISIBLE);
                warningMessage.setText("The username field cannot be empty.");
                return;
            }
            EditText password = findViewById(R.id.register_password);
            if(password.getText().toString().equals("") ||
                            !Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$",
                            password.getText().toString())) {
                warningMessage.setLayoutParams(params);
                warningMessage.setVisibility(View.VISIBLE);
                warningMessage.setText("Choose at least 8 characters- one numeric digit, one uppercase and one lowercase letter.");
                return;
            }
            EditText validatePassword = findViewById(R.id.register_validatePass);
            if(!password.getText().toString().equals(validatePassword.getText().toString())) {
                warningMessage.setLayoutParams(params);
                warningMessage.setVisibility(View.VISIBLE);
                warningMessage.setText("The validate password field must be equal to the password field.");
                return;
            }
            EditText displayName = findViewById(R.id.register_displayName);
            if(displayName.getText().toString().equals("")) {
                warningMessage.setLayoutParams(params);
                warningMessage.setVisibility(View.VISIBLE);
                warningMessage.setText("The displayName field cannot be empty.");
                return;
            }

            // Check Img.
            // *** validate with server if username already exist. ***
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("username", userName.getText().toString());
            startActivity(intent);

        });

    }

}