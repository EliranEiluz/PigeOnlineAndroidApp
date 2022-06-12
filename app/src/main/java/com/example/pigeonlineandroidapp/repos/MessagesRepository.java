package com.example.pigeonlineandroidapp.repos;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pigeonlineandroidapp.API.ChatsAPI;
import com.example.pigeonlineandroidapp.API.MessagesAPI;
import com.example.pigeonlineandroidapp.LocalDatabase;
import com.example.pigeonlineandroidapp.MessagesDao;
import com.example.pigeonlineandroidapp.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesRepository {
    private MessagesDao messagesDao;
    private MessagesRepository.MessageListData messageListData;
    private LocalDatabase db;
    private MessagesAPI messagesAPI;

    public MessagesRepository(Context context, int id, String token, String contactUsername) {
        this.db = LocalDatabase.getInstance(context);
        this.messagesDao = db.messagesDao();
        this.messagesAPI = new MessagesAPI(context, token);
        this.messageListData = new MessageListData(id);
        this.messagesAPI.get(contactUsername, this);
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData(int id) {
            super();
            setValue(new ArrayList<>());
            new Thread(()-> {
                postValue(messagesDao.index(id));
            }).start();
        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }

    public LiveData<List<Message>> getAll() {
        return this.messageListData;
    }

    public void setMessageListData(int id) {
        this.messageListData = new MessageListData(id);
    }

    public void add(Message message) {
        messagesDao.insert(message);
    }

    public void handleGetMessages(int responseNum, List<Message> messages) {
        if(responseNum == 200) {
            this.messageListData.setValue(messages);
            new Thread(() -> {
                messagesDao.insertAll(messages);
            }).start();
        }
    }
}
