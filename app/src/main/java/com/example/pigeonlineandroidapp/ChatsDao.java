package com.example.pigeonlineandroidapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pigeonlineandroidapp.entities.Chat;

import java.util.List;

@Dao
public interface ChatsDao {

    @Insert
    void insert(Chat...chats);

    @Query("SELECT * FROM chat WHERE chatOwner=:id")
    List<Chat> index(String id);
}
