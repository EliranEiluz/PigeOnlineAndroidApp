package com.example.pigeonlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.pigeonlineandroidapp.viewModels.ContactsViewModel;
import com.example.pigeonlineandroidapp.viewModels.ContactsViewModelFactory;

public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel contactsViewModel;
    private String username;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Intent intent = getIntent();
        this.username = intent.getExtras().getString("username");
        this.contactsViewModel = new ViewModelProvider(this, new ContactsViewModelFactory
                (this.username, getApplicationContext())).get(ContactsViewModel.class);
        this.lv = findViewById(R.id.contacts_chatsList);
        final ContactsAdapter contactsAdapter = new ContactsAdapter(getApplicationContext(), contactsViewModel.get().getValue());
        lv.setAdapter(contactsAdapter);
        //this.contactsViewModel.get().observe(this, );
    }
}