package com.example.pigeonlineandroidapp.repos;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.pigeonlineandroidapp.LocalDatabase;
import com.example.pigeonlineandroidapp.MessagesDao;
import com.example.pigeonlineandroidapp.entities.Message;
import java.util.List;

public class MessagesRepository {
    private MessagesDao messagesDao;
    private MessagesRepository.MessageListData messageListData;
    private LocalDatabase db;

    public MessagesRepository(Context context, int id) {
        this.db = LocalDatabase.getInstance(context);
        this.messagesDao = db.messagesDao();
        this.messageListData = new MessageListData(id);
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData(int id) {
            super();
            List<Message> messages = messagesDao.index(id);
            setValue(messages);
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
}
