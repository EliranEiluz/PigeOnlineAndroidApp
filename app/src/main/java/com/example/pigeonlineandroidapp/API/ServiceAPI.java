package com.example.pigeonlineandroidapp.API;


import com.example.pigeonlineandroidapp.entities.Chat;
import com.example.pigeonlineandroidapp.entities.Message;
import com.example.pigeonlineandroidapp.entities.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceAPI {
    @GET("api/contacts")
    Call<List<Chat>> getChats();

    @POST("api/contacts")
    Call<Void> postChat(@Body PostContactParams params);

    @POST("api/Users/Login")
    Call<User> Login(@Body UserValidation userValidation);

    @POST("api/Users")
    Call<String> postUser(@Body User user);

    @GET("api/contacts/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String id);

    @POST("api/contacts/{id}/messages")
    Call<Void> postMessage(@Path("id") String id, @Body MessageContent messageContent);

    @POST("api/transfer")
    Call<Void> transfer(@Body TransferParams transferParams);

    @POST("api/invitations")
    Call<Void> getInvitation(@Body InvitationParams invitationParams);

}
