package com.example.pigeonlineandroidapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pigeonlineandroidapp.R;

@Entity
public class Message {

    @PrimaryKey(autoGenerate=true)
    private int id;

    private String from;

    private String content;

    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(int senderPicture) {
        this.senderPicture = senderPicture;
    }

    public int getChatOwnerId() {
        return chatOwnerId;
    }

    public void setChatOwnerId(int chatOwnerId) {
        this.chatOwnerId = chatOwnerId;
    }

    private int senderPicture;

    private int chatOwnerId;

    public Message(int id, String from, String content, String date, int chatOwnerId) {
        this.id = id;
        this.from = from;
        this.content = content;
        this.date = date;
        this.senderPicture = R.drawable.im3;
        this.chatOwnerId = chatOwnerId;
    }
}
