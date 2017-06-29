package com.example.yaroslav.scorpionssocial.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkService {

    private String baseURL = "http://localhost:8081/";
    private ApiInterface api;

    public NetworkService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        api = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getApi(){
        return api;
    }
}
