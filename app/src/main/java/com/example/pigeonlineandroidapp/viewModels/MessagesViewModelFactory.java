package com.example.pigeonlineandroidapp.viewModels;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.pigeonlineandroidapp.repos.MessagesRepository;


public class MessagesViewModelFactory implements ViewModelProvider.Factory {
    private int chatID;
    private Context context;
    private String token;
    private String contactUsername;

    public MessagesViewModelFactory(int chatID, Context context, String token, String contactUsername) {
        this.chatID = chatID;
        this.context = context;
        this.token = token;
        this.contactUsername = contactUsername;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MessagesViewModel(context, chatID, token, contactUsername);
    }
}
