package com.example.yaroslav.scorpionssocial.presenter;

import android.content.Context;
import android.util.Log;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Conversation;
import com.example.yaroslav.scorpionssocial.model.Message;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.ConversationPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.ConversationActivityInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConversationPresenter extends BasePresenter implements ConversationPresenterInterface {

    private ConversationActivityInterface view;
    private int id;
    private String token;
    private final String TAG = "ConTag";

    public ConversationPresenter(ConversationActivityInterface view, Context context) {
        super(context);
        this.view = view;
        id = App.getApp().getPreferenceManager().getUser().getId();
        token = App.getApp().getPreferenceManager().getUser().getToken();
    }

    public void initConversation(){
        App.getApi().getUserConversation(id,token).enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                if (response.isSuccessful()) {
                    view.setConversations(response.body());
                } else
                    onFailure(call,new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    @Override
    public void createConversation(String name) {
        App.getApi().createConversation(token,name).enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if (response.isSuccessful()){
                    view.clearRecycle();
                    initConversation();
                }
            }

            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {

            }
        });
    }

    @Override
    public void addUserToConversation(int conId, int userId) {
        App.getApi().addUserToConversation(conId,userId,token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
