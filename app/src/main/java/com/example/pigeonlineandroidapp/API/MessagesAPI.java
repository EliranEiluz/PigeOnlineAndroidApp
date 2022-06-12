package com.example.pigeonlineandroidapp.API;

import android.content.Context;

import com.example.pigeonlineandroidapp.ChatActivity;
import com.example.pigeonlineandroidapp.ChatsDao;
import com.example.pigeonlineandroidapp.MessagesDao;
import com.example.pigeonlineandroidapp.R;
import com.example.pigeonlineandroidapp.entities.Message;
import com.example.pigeonlineandroidapp.repos.MessagesRepository;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MessagesAPI {
    private ServiceAPI serviceAPI;
    private Retrofit retrofit;
    private String token;

    public MessagesAPI(Context context, String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        this.retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.BaseUrl)).
                addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        this.serviceAPI = retrofit.create(ServiceAPI.class);
        this.token = token;
    }

    public void get(String username, MessagesRepository messagesRepository) {
        Call<List<Message>> getMessagesCall = this.serviceAPI.getMessages(username, this.token);
        getMessagesCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messagesRepository.handleGetMessages(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                messagesRepository.handleGetMessages(0, null);
            }
        });
    }

    public void postMessage() {

    }
}
