package com.example.pigeonlineandroidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.pigeonlineandroidapp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText defaultServer = findViewById(R.id.settings_server);
        Button applyBtn = findViewById(R.id.settings_apply_btn);

        applyBtn.setOnClickListener(view -> {
            if(!defaultServer.getText().toString().equals("")) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("defaultServer", defaultServer.getText().toString());
                defaultServer.setText("");
                startActivity(intent);
            }
        });
    }
}