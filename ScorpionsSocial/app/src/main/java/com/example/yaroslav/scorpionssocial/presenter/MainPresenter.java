package com.example.yaroslav.scorpionssocial.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Conversation;
import com.example.yaroslav.scorpionssocial.model.UserRegistrationInfo;
import com.example.yaroslav.scorpionssocial.network.SignalRService;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.MainPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.MainActivityInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends BasePresenter implements MainPresenterInterface {

    private MainActivityInterface view;
    private Context context;


    public MainPresenter(MainActivityInterface view, Context context) {
        super(context);
        this.view = view;
        this.context = context;
    }

    public void checkApp() {
        if (!checkConnection())
            view.showNoConnection();
        else if (userStored())
            launchUserActivity(App.getApp().getPreferenceManager().getUser().getId());
        else
            launchLoginActivity();
    }

    private boolean checkConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    private boolean userStored() {
        return App.getApp().getPreferenceManager().getUser() != null;
    }
}

