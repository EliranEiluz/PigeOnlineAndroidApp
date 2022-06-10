package com.example.pigeonlineandroidapp.API;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.example.pigeonlineandroidapp.ChatsDao;
import com.example.pigeonlineandroidapp.R;
import com.example.pigeonlineandroidapp.entities.Chat;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ChatsAPI {

    private ServiceAPI serviceAPI;
    private ChatsDao chatsDao;
    private Retrofit retrofit;
    private boolean isValid;
    private String token;


    public ChatsAPI(Context context, ChatsDao chatsDao, String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        this.retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.BaseUrl)).
                addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        this.serviceAPI = retrofit.create(ServiceAPI.class);
        this.chatsDao = chatsDao;
        this.token = token;
    }

    public void get(MutableLiveData<List<Chat>> chatsList) {
        Call<List<Chat>> call = this.serviceAPI.getChats(this.token);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                chatsList.setValue(response.body());
                new Thread(() -> {chatsDao.insertAll(chatsList.getValue());});
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {

            }
        });
    }

    public boolean post(MutableLiveData<List<Chat>> chatsList, String to, String name, String server, String from) {
        InvitationParams invitationParams = new InvitationParams();
        invitationParams.setTo(to);
        invitationParams.setFrom(from);
        invitationParams.setServer(server);
        Retrofit tempRetro = new Retrofit.Builder().baseUrl(server).
                addConverterFactory(GsonConverterFactory.create()).build();
        ServiceAPI tempServiceAPI = tempRetro.create(ServiceAPI.class);
        Call<Void> invitationCall = tempServiceAPI.getInvitation(invitationParams, this.token);
        invitationCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201) {
                    PostContactParams postContactParams = new PostContactParams();
                    postContactParams.setId(to);
                    postContactParams.setName(name);
                    postContactParams.setServer(server);
                    Call<Void> postCall = serviceAPI.postChat(postContactParams, token);
                    postCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 201) {
                                Chat chat = new Chat(to, name, server, from);
                                List<Chat> tempList = chatsList.getValue();
                                tempList.add(chat);
                                chatsList.setValue(tempList);
                                new Thread(() -> {chatsDao.insert(chat);});
                                isValid = true;
                            }
                            else {
                                isValid = false;
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            isValid = false;
                        }
                    });
                }
                else {
                    isValid = false;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isValid = false;
            }
        });
        return this.isValid;
    }
}

