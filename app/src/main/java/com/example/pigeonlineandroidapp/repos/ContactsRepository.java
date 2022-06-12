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
        this.chatsAPI = new ChatsAPI(context, this.chatsDao, token);
        this.chatListData = new ChatListData();
        this.chatsAPI.get(this);
    }

    class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();
            setValue(new ArrayList<>());
            new Thread(()-> {
                postValue(chatsDao.index(username));
            }).start();
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
            new Thread(() -> {chatsDao.insert(chat);}).start();
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

    public void handleGetChats(int responseNum, List<Chat> chatsList) {
        if(responseNum == 200) {
            for(Chat chat : chatsList) {
                chat.setChatOwner(username);
            }
            this.chatListData.setValue(chatsList);
            new Thread(() -> {
                chatsDao.insertAll(chatsList);
            }).start();
        }
    }

    public void update(int id, String lastMessage, String date) {
        Chat chat = this.chatsDao.getChat(id);
        chat.setLastMessage(lastMessage);
        chat.setDate(date);
        new Thread(() ->{this.chatsDao.insert(chat);}).start();
        List<Chat> chats = this.chatListData.getValue();
        for(Chat c : chats) {
            if(c.getId() == id) {
                c.setDate(date);
                c.setLastMessage(lastMessage);
            }
        }
        this.chatListData.setValue(chats);
    }
}
