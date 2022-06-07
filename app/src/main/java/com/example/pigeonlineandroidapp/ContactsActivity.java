package com.example.pigeonlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pigeonlineandroidapp.viewModels.ContactsViewModel;
import com.example.pigeonlineandroidapp.viewModels.ContactsViewModelFactory;

public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel contactsViewModel;
    private String username;
    private ListView contactsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Intent intent = getIntent();
        this.username = intent.getExtras().getString("username");
        this.contactsViewModel = new ViewModelProvider(this, new ContactsViewModelFactory
                (this.username, getApplicationContext())).get(ContactsViewModel.class);
        this.contactsListView = findViewById(R.id.contacts_chatsList);
        final ContactsAdapter contactsAdapter = new ContactsAdapter(getApplicationContext(), contactsViewModel.get().getValue());
        contactsListView.setAdapter(contactsAdapter);

        contactsListView.setClickable(true);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                //intent.putExtra("currentUsername", username);
                //intent.putExtra("contactUsername", contactsAdapter.getItem(position).getChatWith());
                //intent.putExtra("contactDisplayName", contactsAdapter.getItem(position).getDisplayName());
                //intent.putExtra("chatId", contactsAdapter.getItem(position).getId());
                //startActivity(intent);
            }
        });

        this.contactsViewModel.get().observe(this, contacts -> {
            contactsAdapter.setData(contacts);
            //contactsAdapter.notifyDataSetChanged();
        });
    }
}