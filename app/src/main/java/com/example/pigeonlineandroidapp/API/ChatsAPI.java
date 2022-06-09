package com.example.pigeonlineandroidapp.API;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.example.pigeonlineandroidapp.ChatsDao;
import com.example.pigeonlineandroidapp.R;
import com.example.pigeonlineandroidapp.entities.Chat;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsAPI {

    private ServiceAPI serviceAPI;
    ChatsDao chatsDao;
    Retrofit retrofit;
    boolean isValid;

    public ChatsAPI(Context context, ChatsDao chatsDao) {
        this.retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.BaseUrl)).
                addConverterFactory(GsonConverterFactory.create()).build();
        this.serviceAPI = retrofit.create(ServiceAPI.class);
        this.chatsDao = chatsDao;
    }

    public void get(MutableLiveData<List<Chat>> chatsList) {
        Call<List<Chat>> call = this.serviceAPI.getChats();
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
        Call<Void> invitationCall = this.serviceAPI.getInvitation(invitationParams);
        invitationCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201) {
                    PostContactParams postContactParams = new PostContactParams();
                    postContactParams.setId(to);
                    postContactParams.setName(name);
                    postContactParams.setServer(server);
                    Call<Void> postCall = serviceAPI.postChat(postContactParams);
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

