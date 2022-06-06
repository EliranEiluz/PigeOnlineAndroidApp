package com.example.pigeonlineandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.pigeonlineandroidapp.entities.Chat;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Chat> {
    LayoutInflater inflater;

    public ContactsAdapter(Context context, List<Chat> chats) {
        super(context, R.layout.contact_item, chats);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Chat chat = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_item, parent, false);
        }
        TextView displayName = convertView.findViewById(R.id.contact_item_displayName);
        displayName.setText(chat.getDisplayName());

        TextView date = convertView.findViewById(R.id.contact_item_date);
        date.setText(chat.getDate());

        TextView lastMessage = convertView.findViewById(R.id.contact_item_lastMessage);
        lastMessage.setText(chat.getLastMessage());

        return convertView;
    }
}
