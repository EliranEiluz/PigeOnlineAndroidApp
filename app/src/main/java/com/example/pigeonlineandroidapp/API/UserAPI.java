package com.example.pigeonlineandroidapp.API;

import android.content.Context;

import com.example.pigeonlineandroidapp.R;
import com.example.pigeonlineandroidapp.entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private Retrofit retrofit;
    private ServiceAPI serviceAPI;
    private String token;

    public UserAPI(Context context) {
        this.retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.BaseUrl)).
                addConverterFactory(GsonConverterFactory.create()).build();
        this.serviceAPI = retrofit.create(ServiceAPI.class);
        this.token = null;
    }

    public String postUser(User user) {
        Call<User> postUserCall = serviceAPI.postUser(user);
        postUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    User user = response.body();
                    token = user.getUsername();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return token;
    }

    public String Login(String username, String password) {
        UserValidation userValidation = new UserValidation();
        userValidation.setUsername(username);
        userValidation.setPassword(password);
        Call<User> loginCall = serviceAPI.Login(userValidation);
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    User user = response.body();
                    token = user.getUsername();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return token;
    }
}
