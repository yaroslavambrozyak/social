package com.example.yaroslav.scorpionssocial.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.adapter.ChatAdapter;
import com.example.yaroslav.scorpionssocial.adapter.FriendAdapter;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Message;
import com.example.yaroslav.scorpionssocial.model.User;
import com.example.yaroslav.scorpionssocial.presenter.BasePresenter;
import com.example.yaroslav.scorpionssocial.presenter.ChatPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.ChatPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.ChatActivityInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.UpdateMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatActivityInterface{

    private ChatPresenterInterface presenter;
    private List<Message> messages;
    private int id;

    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    @BindView(R.id.edit_text)
    EditText text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        messages = new ArrayList<>();
        initDrawer();
        initRecycleView();
        id = getIntent().getIntExtra(BasePresenter.CHAT_ID, 0);
        presenter = new ChatPresenter(this, ChatActivity.this, id);
        presenter.initMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                presenter.launchSettingsActivity();
                return true;
            case R.id.menu_logout:
                presenter.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBindSignalR();
    }

    @OnClick(R.id.send_message)
    public void onSendMessage(){
        Message message = new Message();
        message.setText(text.getText().toString());
        message.setConversationId(id);
        message.setUserId(App.getApp().getPreferenceManager().getUser().getId());
        presenter.sendMessage(message);
        text.setText("");
    }


    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecycleView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        RecyclerView.Adapter adapter = new ChatAdapter(messages);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setMessage(List<Message> data) {
        messages.addAll(data);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(messages.size()-1);
    }

    @Override
    public void showToastError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
