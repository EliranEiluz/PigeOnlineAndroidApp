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

import com.example.pigeonlineandroidapp.API.UserAPI;
import com.example.pigeonlineandroidapp.entities.User;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private UserAPI userAPI;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate LOGIN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.userAPI = new UserAPI(this.getApplicationContext());
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
                this.userAPI.Login(userName.getText().toString(), password.getText().toString(), this);
            }
            else {
                TextView warningMessage = findViewById(R.id.main_warning_message);
                warningMessage.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = warningMessage.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                warningMessage.setLayoutParams(params);
            }
        });
    }

    public void handleLoginResponse(User user, int responseCode, String userName) {
        if(responseCode == 200) {
            this.token = user.getUsername();
        }
        else {
            this.token = null;
        }
        if(this.token != null) {
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("username", userName);
            intent.putExtra("token", "Bearer " + this.token);
            startActivity(intent);
        }
        else {
            TextView warningMessage = findViewById(R.id.main_warning_message);
            warningMessage.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = warningMessage.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            warningMessage.setLayoutParams(params);
        }
    }

}