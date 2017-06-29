package com.example.yaroslav.scorpionssocial.presenter;


import android.content.Context;
import android.util.Log;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.User;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.FriendsPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.FriendsActivityInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsPresenter extends BasePresenter implements FriendsPresenterInterface {

    private FriendsActivityInterface view;
    private int id;
    private String token;

    private final String TAG = "FriendLog";

    public FriendsPresenter(FriendsActivityInterface view, Context context, int id) {
        super(context);
        this.view = view;
        this.id = id;
        token = App.getApp().getPreferenceManager().getUser().getToken();
    }

    @Override
    public void initFriends() {
        //view.showProgressBar();
        Log.d("KKK",id+"");
        App.getApi().getUserFriends(id,token).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    view.hideProgressBar();
                    view.setUserFriends(response.body());
                } else
                    onFailure(call,new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }
}
