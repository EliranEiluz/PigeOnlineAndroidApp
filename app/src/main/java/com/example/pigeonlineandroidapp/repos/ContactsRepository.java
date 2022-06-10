package com.example.pigeonlineandroidapp.repos;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pigeonlineandroidapp.API.ChatsAPI;
import com.example.pigeonlineandroidapp.ChatsDao;
import com.example.pigeonlineandroidapp.LocalDatabase;
import com.example.pigeonlineandroidapp.entities.Chat;
import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {
    private ChatsDao chatsDao;
    private ChatListData chatListData;
    private String username;
    private ChatsAPI chatsAPI;
    private LocalDatabase db;


    public ContactsRepository(String username, Context context, String token) {
        this.username = username;
        this.db = LocalDatabase.getInstance(context);
        this.chatsDao = db.chatDao();
        this.chatListData = new ChatListData();
        this.chatsAPI = new ChatsAPI(context, this.chatsDao, token);
    }

    class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();
            new Thread(()-> {
                postValue(chatsDao.index(username));
            }).start();
        }

    }

    public LiveData<List<Chat>> getAll() {
        if(chatListData.getValue() == null) {
            chatListData.setValue(new ArrayList<>());
        }
        return chatListData;
    }

    public boolean add(String from, String to, String server, String displayName) {
        return chatsAPI.post(this.chatListData, to, displayName, server, from);
    }
}
