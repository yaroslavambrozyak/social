package com.example.yaroslav.scorpionssocial.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Conversation;
import com.example.yaroslav.scorpionssocial.model.Message;
import com.example.yaroslav.scorpionssocial.model.UserRegistrationInfo;
import com.example.yaroslav.scorpionssocial.network.SignalRService;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.BaseInterface;
import com.example.yaroslav.scorpionssocial.view.ChatActivity;
import com.example.yaroslav.scorpionssocial.view.ConversationActivity;
import com.example.yaroslav.scorpionssocial.view.FriendsActivity;
import com.example.yaroslav.scorpionssocial.view.LoginActivity;
import com.example.yaroslav.scorpionssocial.view.RegistrationActivity;
import com.example.yaroslav.scorpionssocial.view.SettingsActivity;
import com.example.yaroslav.scorpionssocial.view.UserActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BasePresenter implements BaseInterface {

    public static final String USER_ID = "userId";
    public static final String CHAT_ID = "chatId";
    private ArrayList<Integer> conId;
    private Context context;

    protected BasePresenter(Context context) {
        this.context = context;
        conId = new ArrayList<>();
    }

    public void launchLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void launchUserActivity(int id) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER_ID, id);
        context.startActivity(intent);
    }

    public void launchRegistrationActivity() {
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    public void launchSettingsActivity() {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public void launchFriendsActivity(int id) {
        Intent intent = new Intent(context, FriendsActivity.class);
        intent.putExtra(USER_ID, id);
        context.startActivity(intent);
    }

    public void launchConversationActivity() {
        Intent intent = new Intent(context, ConversationActivity.class);
        context.startActivity(intent);

    }

    public void launchChatActivity(int id) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(CHAT_ID, id);
        context.startActivity(intent);
    }

    public void logout() {
        App.getApp().getPreferenceManager().logout();
        launchLoginActivity();
    }

    public void initSignalR() {
        UserRegistrationInfo user = App.getApp().getPreferenceManager().getUser();
        App.getApi().getUserConversation(user.getId(), user.getToken()).enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                if (response.isSuccessful()) {
                    startSignalR(response.body());
                } else if (response.code()==401)
                    launchLoginActivity();
                else
                    onFailure(call, new Throwable(response.message()+response.code()));
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

    private void startSignalR(List<Conversation> body) {
        for (Conversation con : body) {
            conId.add(con.getId());
        }
        Intent intent = new Intent(context, SignalRService.class);
        intent.putIntegerArrayListExtra("id",conId);
        intent.putExtra("token",App.getApp().getPreferenceManager().getUser().getToken());
        context.startService(intent);
    }
}

