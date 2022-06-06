package com.example.pigeonlineandroidapp.viewModels;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.pigeonlineandroidapp.repos.ContactsRepository;


public class ContactsViewModelFactory implements ViewModelProvider.Factory {
    private String username;
    private Context context;

    public ContactsViewModelFactory(String username, Context context) {
        this.username = username;
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ContactsViewModel(username, context);
    }
}
