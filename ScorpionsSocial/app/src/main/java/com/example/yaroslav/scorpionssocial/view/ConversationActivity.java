package com.example.yaroslav.scorpionssocial.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.adapter.ConversationAdapter;
import com.example.yaroslav.scorpionssocial.adapter.FriendAdapter;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.Conversation;
import com.example.yaroslav.scorpionssocial.model.User;
import com.example.yaroslav.scorpionssocial.presenter.ConversationPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.ConversationPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.ConversationActivityInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConversationActivity extends BaseActivity implements ConversationActivityInterface {

    private List<Conversation> conversations;
    private int ownId;
    private ConversationPresenterInterface presenter;

    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        conversations = new ArrayList<>();
        ownId = App.getApp().getPreferenceManager().getUser().getId();
        ButterKnife.bind(this);
        initDrawer();
        initToolbar();
        initRecycleView();
        initFloatingButton();
        presenter = new ConversationPresenter(this, ConversationActivity.this);
        presenter.initConversation();
    }

    private void initFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ConversationActivity.this);
                View dialog = getLayoutInflater().inflate(R.layout.create_conversation, null);
                dialogBuilder.setView(dialog);
                final EditText editText = (EditText) dialog.findViewById(R.id.edit_create_conversation);
                dialogBuilder.setTitle("Створення чату");
                dialogBuilder.setMessage("Введіть назву чату");
                dialogBuilder.setPositiveButton("Створити", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.createConversation(editText.getText().toString());
                    }
                });

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    protected void launchUserActivity() {
        presenter.launchUserActivity(ownId);
    }

    @Override
    protected void launchFriendsActivity() {
        presenter.launchFriendsActivity(ownId);
    }

    @Override
    protected void launchConversationActivity() {
        presenter.launchConversationActivity();
    }

    @Override
    protected void launchSettingsActivity() {
        presenter.launchSettingsActivity();
    }

    @Override
    protected void logout() {
        presenter.logout();
    }

    private void initRecycleView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        RecyclerView.Adapter adapter = new ConversationAdapter(conversations, new ConversationAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(Conversation conversation) {
                presenter.launchChatActivity(conversation.getId());
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setConversations(List<Conversation> data) {
        Log.d("DATA", data.size() + "");
        conversations.clear();
        conversations.addAll(data);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void clearRecycle() {
        recyclerView.removeAllViewsInLayout();
    }
}
