package com.example.pigeonlineandroidapp.repos;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;


import com.example.pigeonlineandroidapp.API.MessagesAPI;
import com.example.pigeonlineandroidapp.ChatsDao;
import com.example.pigeonlineandroidapp.ContactsActivity;
import com.example.pigeonlineandroidapp.LocalDatabase;
import com.example.pigeonlineandroidapp.MessagesDao;
import com.example.pigeonlineandroidapp.entities.Chat;
import com.example.pigeonlineandroidapp.entities.Message;
import com.example.pigeonlineandroidapp.viewModels.ContactsViewModel;
import com.example.pigeonlineandroidapp.viewModels.ContactsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MessagesRepository {
    private MessagesDao messagesDao;
    private MessagesRepository.MessageListData messageListData;
    private LocalDatabase db;
    private MessagesAPI messagesAPI;
    private String contactUsername;
    private int chatOwnerId;
    private String username;
    private String token;
    private Context context;

    public MessagesRepository(Context context, int id, String token, String contactUsername, String username, String defaultServer) {
        this.db = LocalDatabase.getInstance(context);
        this.messagesDao = db.messagesDao();
        this.messagesAPI = new MessagesAPI(context, token, defaultServer);
        this.messageListData = new MessageListData(id);
        this.messagesAPI.get(contactUsername, this);
        this.contactUsername = contactUsername;
        this.chatOwnerId = id;
        this.token = token;
        this.username = username;
        this.context = context;
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

    /*
    public void setMessageListData(int id) {
        this.messageListData = new MessageListData(id);
    }
    public void setContact(String contact) {
        this.contactUsername = contact;
    }
    */

    public void add(Message message, String contactServer) {
        //messagesDao.insert(message);
        this.messagesAPI.transfer(message.getFrom(), this.contactUsername,
                message.getContent(),contactServer, this, message);
    }

    public void handleGetMessages(int responseNum, List<Message> messages) {
        if(responseNum == 200) {
            this.messageListData.setValue(messages);
            new Thread(() -> {
                messagesDao.insertAll(messages);
            }).start();
        }
    }

    public void handlePostMessage(int responseNum, Message message) {
        if(responseNum == 201) {
            List<Message> tempList = this.messageListData.getValue();
            tempList.add(message);
            for(Message m : tempList) {
                m.setChatOwnerId(this.chatOwnerId);
            }
            this.messageListData.setValue(tempList);
            new Thread(() -> {
                messagesDao.insert(message);
                ChatsDao chatsDao = db.chatDao();
                Chat chat = chatsDao.getChat(this.chatOwnerId);
                chat.setLastMessage(message.getContent());
                chat.setDate(message.getDate());
                chatsDao.insert(chat);
            }).start();

        }
    }

    public void afterTransfer(int responseNum, String contactUsername, String content, Message message) {
        if(responseNum == 201) {
            this.messagesAPI.postMessage(contactUsername, content,this, message);
        }
    }

    public void addMessageFromFireBase(Message message) {
        List<Message> messages = this.messageListData.getValue();
        messages.add(message);
        this.messageListData.setValue(messages);
    }
}
