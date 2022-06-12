package com.example.pigeonlineandroidapp.viewModels;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.pigeonlineandroidapp.entities.Message;
import com.example.pigeonlineandroidapp.repos.MessagesRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel {
    private MessagesRepository repository;
    private LiveData<List<Message>> messages;


    public MessagesViewModel(Context context, int id, String token, String contactUsername) {
        this.repository = new MessagesRepository(context, id, token, contactUsername);
        this.messages = repository.getAll();
    }

    public LiveData<List<Message>> get() {
        return this.messages;
    }
/*
    public void setNewChat(int id) {
        this.repository.setMessageListData(id);
    }
    public void setContact(String contact) {
        this.repository.setContact(contact);
    }
 */

    public void add(Message message, String contactServer) {
        this.repository.add(message, contactServer);
    }
}
