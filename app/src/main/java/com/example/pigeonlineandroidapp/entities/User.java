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

    private String image;

    private String displayName;


    //private List<Chat> chats;

    //private String serverURL;

    public String getUsername() {
        return username;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //public List<Chat> getChats() {
        //return chats;
    //}

    //public void setChats(List<Chat> chats) {
      //  this.chats = chats;
    //}

    //public String getServerURL() {
      //  return serverURL;
    //}

    //public void setServerURL(String serverURL) {
       // this.serverURL = serverURL;
    //}



    public User() {
    }

}