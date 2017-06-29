package com.example.yaroslav.scorpionssocial.app;

import android.app.Application;

import com.example.yaroslav.scorpionssocial.network.ApiInterface;
import com.example.yaroslav.scorpionssocial.network.NetworkService;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class App extends Application {

    private static ApiInterface api;
    private PreferenceManager preferenceManager;
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        api = new NetworkService().getApi();
        app = this;
    }

    public static ApiInterface getApi() {
        return api;
    }

    public PreferenceManager getPreferenceManager() {
        if (preferenceManager == null) {
            preferenceManager = new PreferenceManager(this);
        }
        return preferenceManager;
    }

    public static App getApp() {
        return app;
    }

    public Picasso getPicasso(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", app.getPreferenceManager().getUser().getToken() )
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        return new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .build();
    }


}
