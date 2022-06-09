package com.example.pigeonlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String currentUsername = intent.getExtras().getString("currentUsername");
        String contactUsername = intent.getExtras().getString("contactUsername");
        String contactDisplayName = intent.getExtras().getString("contactDisplayName");
        int chatID = Integer.parseInt(intent.getExtras().getString("chatId"));

        TextView displayNameTV = findViewById(R.id.chat_displayName);
        displayNameTV.setText(contactDisplayName);

        ListView messagesLV = findViewById(R.id.chat_messagesList);
        Button sendBtn = findViewById(R.id.send_button);
        EditText messageEt = findViewById(R.id.message_input);
        sendBtn.setOnClickListener(item -> {
            if (messageEt.getText().toString().equals("")) {
                return;
            }
            // send.
        });

    }
}