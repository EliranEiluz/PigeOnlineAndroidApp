package com.example.pigeonlineandroidapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.pigeonlineandroidapp.entities.Chat;
import com.example.pigeonlineandroidapp.entities.Message;
import java.util.List;


@Dao
public interface MessagesDao {
    @Insert
    void insert(Message...messages);

    @Query("SELECT * FROM message WHERE chatOwnerId=:id")
    List<Message> index(int id);
}
