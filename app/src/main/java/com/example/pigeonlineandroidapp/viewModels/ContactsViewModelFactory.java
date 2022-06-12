package com.example.pigeonlineandroidapp.viewModels;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.pigeonlineandroidapp.repos.ContactsRepository;


public class ContactsViewModelFactory implements ViewModelProvider.Factory {
    private String username;
    private Context context;
    private String token;

    public ContactsViewModelFactory(String username, Context context, String token) {
        this.username = username;
        this.context = context;
        this.token = token;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ContactsViewModel(username, context, token);
    }
}
