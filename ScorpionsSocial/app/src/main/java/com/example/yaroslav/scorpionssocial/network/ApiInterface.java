package com.example.yaroslav.scorpionssocial.network;

import com.example.yaroslav.scorpionssocial.model.AuthorizationResult;
import com.example.yaroslav.scorpionssocial.model.Conversation;
import com.example.yaroslav.scorpionssocial.model.Message;
import com.example.yaroslav.scorpionssocial.model.PersonalInfo;
import com.example.yaroslav.scorpionssocial.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiInterface {

    // account controller **********************************
    //login
    @FormUrlEncoded
    @POST("token")
    Call<AuthorizationResult> login(@Field("username") String userName, @Field("password") String password,
                                    @Field("grant_type") String grant_type);

    //register
    @FormUrlEncoded
    @POST("api/account/register")
    Call<ResponseBody> createUser(@Field("Username") String userLogin, @Field("Email") String email,
                                  @Field("Password") String password, @Field("ConfirmPassword") String confirmPassword);

    //menu_default controller ************************************************
    //menu_default info
    @GET("api/users/{id}")
    Call<User> getUser(@Path("id") int userId, @Header("Authorization") String token);

    // menu_default friends
    @GET("api/users/{id}/friends")
    Call<List<User>> getUserFriends(@Path("id") int userId, @Header("Authorization") String token);

    @GET("api/users/{id}/conversations")
    Call<List<Conversation>> getUserConversation(@Path("id") int userId, @Header("Authorization") String token);

    //menu_default picture
    @GET("api/users/{id}/picture")
    Call<ResponseBody> getUserPicture(@Path("id") int userId, @Header("Authorization") String token);

    //menu_default picture post

    @Multipart
    @POST("api/users/{id}/picture")
    Call<ResponseBody> postUserPicture(@Path("id") int userId, @Header("Authorization") String token,
                                       @Part MultipartBody.Part file, @Part("description") RequestBody description);

    @GET("api/users/identify")
    Call<User> getUser(@Header("Authorization") String token);


    //conversation controller ***************************

    // create conversation
    @FormUrlEncoded
    @POST("api/conversations")
    Call<Conversation> createConversation(@Header("Authorization") String token, @Field("name") String conversationName);

    //users in conversation

    //messages in conversations
    @GET("api/conversations/{id}/messages")
    Call<List<Message>> getConversationMessages(@Path("id") int conversationId, @Header("Authorization") String token);

    //add
    @FormUrlEncoded
    @POST("api/conversations/{conId}/users/add/{userId}")
    Call<Void> addUserToConversation(@Path("conId") int conversationId, @Path("userId") int userId,
                                     @Header("Authorization") String token);

    //delete


    //messages controller
    @FormUrlEncoded
    @POST("api/messages")
    Call<Void> createMessage(@Header("Authorization") String token, @Field("attachment") String attachment,
                             @Field("sendDate") String sendDate, @Field("conversationId") int conversationId,
                             @Field("text") String text, @Field("userId") int userId);

    //personalinfo controller ***********************************************
    //menu_default personal info
    @GET("api/personalinfo/{id}")
    Call<PersonalInfo> getUserPersonalInfo(@Path("id") int userId, @Header("Authorization") String token);

    //change personalinfo
    @FormUrlEncoded
    @PUT("api/personalinfo/{id}")
    Call<Void> changeUserPersonalInfo(@Path("id") int userId, @Header("Authorization") String token,
                                      @Field("birthDate") String birthDate, @Field("firstName") String firstName,
                                      @Field("lastName") String lastName, @Field("phonenumber") String phoneNumber);


}

