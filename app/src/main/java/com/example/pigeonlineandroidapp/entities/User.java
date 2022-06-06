package com.example.pigeonlineandroidapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pigeonlineandroidapp.R;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @PrimaryKey
    private String username;

    private String password;

    private String displayName;

    private int picture;

    private List<Chat> chats;

    private String serverURL;

    public User(String username, String password, String displayName, String serverURL) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.picture = R.drawable.im3;;
        this.serverURL = serverURL;
        this.chats = new ArrayList<>();
    }

    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.picture = R.drawable.im3;
        this.serverURL = "http://localhost:5010";
        this.chats = new ArrayList<>();
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getUsername() {
        return username;
    }
}
