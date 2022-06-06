package com.example.pigeonlineandroidapp.repos;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.pigeonlineandroidapp.ChatsDao;
import com.example.pigeonlineandroidapp.LocalDatabase;
import com.example.pigeonlineandroidapp.entities.Chat;
import java.util.ArrayList;
import java.util.List;

public class ContactsRepository {
    private ChatsDao chatsDao;
    private ChatListData chatListData;
    private String username;
    private LocalDatabase db;

    public ContactsRepository(String username, Context context) {
        this.username = username;
        this.db = LocalDatabase.getInstance(context);
        this.chatsDao = db.chatDao();
        this.chatListData = new ChatListData();

    }

    class ChatListData extends MutableLiveData<List<Chat>> {
        public ChatListData() {
            super();
            List<Chat> chats = chatsDao.index(username);
            setValue(chats);
        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }

    public LiveData<List<Chat>> getAll() {
        return chatListData;
    }

    public void add(Chat chat) {
        chatsDao.insert(chat);
    }
}
