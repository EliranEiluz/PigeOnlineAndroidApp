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
    private String appToken = null;
    private String defaultServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate LOGIN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.defaultServer = this.getString(R.string.BaseUrl);
        this.userAPI = new UserAPI(this.getApplicationContext(), this.defaultServer);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                MainActivity.this, instanceIdResult -> {
                    this.appToken = instanceIdResult.getToken();
                });
        Button registerBtn = findViewById(R.id.main_register_btn);
        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("defaultServer", this.defaultServer);
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
            EditText userNameET = findViewById(R.id.main_username);
            EditText passwordET = findViewById(R.id.main_password);
            userNameET.setText("");
            passwordET.setText("");
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("username", userName);
            intent.putExtra("token", "Bearer " + this.token);
            intent.putExtra("appToken", this.appToken);
            intent.putExtra("defaultServer", this.defaultServer);
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

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("defaultServer")) {
                this.defaultServer = intent.getExtras().getString("defaultServer");
            }
        }
    }

}