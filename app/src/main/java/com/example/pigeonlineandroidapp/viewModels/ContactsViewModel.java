package com.example.pigeonlineandroidapp.viewModels;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.pigeonlineandroidapp.entities.Chat;
import com.example.pigeonlineandroidapp.repos.ContactsRepository;
import java.util.List;
import java.util.Objects;

public class ContactsViewModel extends ViewModel {
    private ContactsRepository repository;
    private LiveData<List<Chat>> chats;

    public ContactsViewModel(String username, Context context) {
        this.repository = new ContactsRepository(username, context);
        this.chats = repository.getAll();
    }

    public LiveData<List<Chat>> get() {
        return this.chats;
    }

    public boolean add(String from, String to, String server, String displayName) {
       return repository.add(from, to, server, displayName);
    }

}
