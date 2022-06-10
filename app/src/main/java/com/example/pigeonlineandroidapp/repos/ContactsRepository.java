package com.example.pigeonlineandroidapp.repos;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pigeonlineandroidapp.API.ChatsAPI;
import com.example.pigeonlineandroidapp.API.PostContactParams;
import com.example.pigeonlineandroidapp.AddContactActivity;
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
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            /*
            new Thread(()-> {
                postValue(chatsDao.index(username));
            }).start();
            *

             */
            List<Chat> c =chatsDao.index(username);
            setValue(chatsDao.index(username));
        }
    }

    public LiveData<List<Chat>> getAll() {
        return chatListData;
    }

    public void add(String from, String to, String server, String displayName, AddContactActivity addContactActivity) {
        chatsAPI.sendInvitation(to, from, server, displayName, this, addContactActivity);

    }

    public void postChatHandler(int responseCode, String to, String name, String server, String from, AddContactActivity addContactActivity) {
        if(responseCode == 201) {
            Chat chat = new Chat(to, name, server, from);
            List<Chat> tempList = this.chatListData.getValue();
            tempList.add(chat);
            this.chatListData.setValue(tempList);
            new Thread(() -> {chatsDao.insert(chat);});
            addContactActivity.hadnleSuccess();
        }
        else {
            addContactActivity.handleFailure();
        }
    }

    public void afterInvitationHandler(int responseCode, String to, String name, String server, AddContactActivity addContactActivity) {
        if(responseCode == 201) {
            PostContactParams postContactParams = new PostContactParams();
            postContactParams.setId(to);
            postContactParams.setName(name);
            postContactParams.setServer(server);
            chatsAPI.post(postContactParams, this, this.username, addContactActivity);
        }
        else {
            addContactActivity.handleFailure();
        }
    }
}
