package com.example.pigeonlineandroidapp.viewModels;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pigeonlineandroidapp.AddContactActivity;
import com.example.pigeonlineandroidapp.entities.Chat;
import com.example.pigeonlineandroidapp.entities.Message;
import com.example.pigeonlineandroidapp.repos.ContactsRepository;
import java.util.List;
import java.util.Objects;

public class ContactsViewModel extends ViewModel {
    private ContactsRepository repository;
    private LiveData<List<Chat>> chats;

    public ContactsViewModel(String username, Context context, String token) {
        this.repository = new ContactsRepository(username, context, token);
        this.chats = repository.getAll();
    }

    public LiveData<List<Chat>> get() {
        return this.chats;
    }

    public void add(String from, String to, String server, String displayName, AddContactActivity addContactActivity) {
        this.repository.add(from, to, server, displayName, addContactActivity);
    }

    public void updateChat(int id) {
        this.repository.update(id);
    }

    public void updateNewMessage(Message message, String chatOwner) {
        this.repository.updateNewMessage(message, chatOwner);
    }

}
