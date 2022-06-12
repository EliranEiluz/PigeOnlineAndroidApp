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

    public MessagesAPI(Context context, String token, String defaultServer) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        this.retrofit = new Retrofit.Builder().baseUrl(defaultServer).
                addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        this.serviceAPI = retrofit.create(ServiceAPI.class);
        this.token = token;
    }

    public void get(String contactUsername, MessagesRepository messagesRepository) {
        Call<List<Message>> getMessagesCall = this.serviceAPI.getMessages(contactUsername, this.token);
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

    public void postMessage(String contactUsername, String content, MessagesRepository messagesRepository, Message message) {
        MessageContent messageContent = new MessageContent();
        messageContent.setContent(content);
        Call<Void> postMessageCall = this.serviceAPI.postMessage(contactUsername, messageContent, this.token);
        postMessageCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                messagesRepository.handlePostMessage(response.code(), message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                messagesRepository.handlePostMessage(0, message);
            }
        });
    }

    public void transfer(String from, String to, String content, String server, MessagesRepository messagesRepository, Message message) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit tempRetrofit = new Retrofit.Builder().baseUrl(server).
                addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        ServiceAPI tempServiceAPI = tempRetrofit.create(ServiceAPI.class);
        TransferParams transferParams = new TransferParams();
        transferParams.setFrom(from);
        transferParams.setTo(to);
        transferParams.setContent(content);
        Call<Void> transferCall = tempServiceAPI.transfer(transferParams);
        transferCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                messagesRepository.afterTransfer(response.code(), to, content, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                messagesRepository.afterTransfer(0, to, content, message);
            }
        });
    }
}
