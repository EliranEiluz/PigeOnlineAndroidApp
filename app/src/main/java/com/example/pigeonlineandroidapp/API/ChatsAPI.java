package com.example.pigeonlineandroidapp.API;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import com.example.pigeonlineandroidapp.AddContactActivity;
import com.example.pigeonlineandroidapp.ChatsDao;
import com.example.pigeonlineandroidapp.R;
import com.example.pigeonlineandroidapp.entities.Chat;
import com.example.pigeonlineandroidapp.repos.ContactsRepository;

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

    public void get(ContactsRepository repository) {
        Call<List<Chat>> call = this.serviceAPI.getChats(this.token);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                repository.handleGetChats(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {

            }
        });
    }

    public void post(PostContactParams postContactParams, ContactsRepository contactsRepository, String username, AddContactActivity addContactActivity) {
        Call<Void> postContactCall = serviceAPI.postChat(postContactParams, this.token);
        postContactCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                contactsRepository.postChatHandler(response.code(), postContactParams.getId(),
                        postContactParams.getName(), postContactParams.getServer(), username, addContactActivity);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                contactsRepository.postChatHandler(0, postContactParams.getId(),
                        postContactParams.getName(), postContactParams.getServer(), username, addContactActivity);
            }
        });

    }

    public void sendInvitation(String to, String from, String server, String name,ContactsRepository contactsRepository, AddContactActivity addContactActivity) {
        InvitationParams invitationParams = new InvitationParams();
        invitationParams.setTo(to);
        invitationParams.setFrom(from);
        invitationParams.setServer(server);
        Retrofit tempRetro = new Retrofit.Builder().baseUrl(server).
                addConverterFactory(GsonConverterFactory.create()).build();
        ServiceAPI tempServiceAPI = tempRetro.create(ServiceAPI.class);
        Call<Void> invitationCall = tempServiceAPI.getInvitation(invitationParams);
        invitationCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                contactsRepository.afterInvitationHandler(response.code(), to, name, server, addContactActivity);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                contactsRepository.afterInvitationHandler(0, to, name, server, addContactActivity);
            }
        });
    }

    public void declareOnline(String appToken) {
        Call<Void> declareOnlineCall = this.serviceAPI.declareOnline(appToken, this.token);
        declareOnlineCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}

