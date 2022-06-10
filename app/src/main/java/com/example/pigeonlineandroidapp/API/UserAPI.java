package com.example.pigeonlineandroidapp.API;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.pigeonlineandroidapp.MainActivity;
import com.example.pigeonlineandroidapp.R;
import com.example.pigeonlineandroidapp.RegisterActivity;
import com.example.pigeonlineandroidapp.entities.User;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserAPI {
    private Retrofit retrofit;
    private ServiceAPI serviceAPI;

    public UserAPI(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        this.retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.BaseUrl)).
                addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();
        this.serviceAPI = retrofit.create(ServiceAPI.class);
    }

    public void postUser(User user, RegisterActivity registerActivity) {
        Call<String> postUserCall = serviceAPI.postUser(user);
        postUserCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                registerActivity.handleRegisterResponse(response.code(), response.body(), user.getUsername());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                registerActivity.handleRegisterResponse(0, null, null);
            }
        });
    }

    public void Login(String username, String password, MainActivity mainActivity) {
        UserValidation userValidation = new UserValidation();
        userValidation.setUsername(username);
        userValidation.setPassword(password);
        Call<User> loginCall = serviceAPI.Login(userValidation);
        loginCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mainActivity.handleLoginResponse(response.body(), response.code(), username);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mainActivity.handleLoginResponse(null, 0, null);
            }
        });
    }
}
