package com.example.pigeonlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Button addBtn = findViewById(R.id.contact_add_btn);
        addBtn.setOnClickListener(item -> {
            TextView warningMessage = findViewById(R.id.contact_warning_message);
            EditText identifier = findViewById(R.id.contact_identifier);
            EditText displayName = findViewById(R.id.contact_display);
            EditText server = findViewById(R.id.contact_server);
            if((identifier.getText().toString().equals("")) ||
                    (displayName.getText().toString().equals("")) ||
                    (server.getText().toString().equals(""))) {
                warningMessage.setText("All fields are required !");
                warningMessage.setVisibility(View.VISIBLE);
                return;
            }
            Intent intentLst = getIntent();
            ArrayList<String> identifiersLst =  (ArrayList<String>) intentLst.
                    getSerializableExtra("identifiers_list");
            for (String contact_usr : identifiersLst) {
                if (contact_usr.equals(identifier.getText().toString())) {
                    warningMessage.setText("You already have this contact.");
                    warningMessage.setVisibility(View.VISIBLE);
                    return;
                }
            }

            // Check with the server if exist -> and Add.

            Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
            startActivity(intent);
        });
    }
}