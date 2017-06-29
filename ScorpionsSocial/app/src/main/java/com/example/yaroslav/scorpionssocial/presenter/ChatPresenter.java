package com.example.yaroslav.scorpionssocial.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Message;
import com.example.yaroslav.scorpionssocial.network.SignalRService;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.ChatPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.ChatActivityInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.UpdateMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatPresenter extends BasePresenter implements ChatPresenterInterface, UpdateMessage {


    private ChatActivityInterface view;
    private int id;
    private String token;
    private Context context;
    private final String TAG = "ChatLog";
    public SignalRService signalR;

    public ChatPresenter(ChatActivityInterface view, Context context, int id) {
        super(context);
        this.context = context;
        this.view = view;
        this.id = id;
        token = App.getApp().getPreferenceManager().getUser().getToken();
        bindSignalR();
    }

    public void unBindSignalR(){
        context.unbindService(sCon);
    }

    private void bindSignalR() {
        Intent intent = new Intent(context, SignalRService.class);
        context.bindService(intent,sCon,Context.BIND_AUTO_CREATE);
    }

    private final ServiceConnection sCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
            signalR = binder.getSignalR();
            signalR.setUpdate(ChatPresenter.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void initMessage() {
        App.getApi().getConversationMessages(id,token).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()){
                    view.setMessage(response.body());
                }
                else
                    onFailure(call,new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    @Override
    public void sendMessage(Message message) {
        signalR.sendMessage(message);
    }

    @Override
    public void updateMessage() {
        initMessage();
    }
}
