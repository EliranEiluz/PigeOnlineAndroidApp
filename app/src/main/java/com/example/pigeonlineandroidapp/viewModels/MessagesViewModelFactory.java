package com.example.pigeonlineandroidapp.viewModels;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.pigeonlineandroidapp.repos.MessagesRepository;


public class MessagesViewModelFactory implements ViewModelProvider.Factory {
    private int chatID;
    private Context context;

    public MessagesViewModelFactory(int chatID, Context context) {
        this.chatID = chatID;
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MessagesViewModel(context, chatID);
    }
}
