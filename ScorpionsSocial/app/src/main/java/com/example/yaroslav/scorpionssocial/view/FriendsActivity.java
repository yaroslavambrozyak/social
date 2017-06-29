package com.example.yaroslav.scorpionssocial.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.adapter.FriendAdapter;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.User;
import com.example.yaroslav.scorpionssocial.presenter.BasePresenter;
import com.example.yaroslav.scorpionssocial.presenter.FriendsPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.FriendsPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.FriendsActivityInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsActivity extends BaseActivity implements FriendsActivityInterface {

    private FriendsPresenterInterface presenter;
    private List<User> friends;
    private int ownId;

    @BindView(R.id.recycle)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    RelativeLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);
        friends = new ArrayList<>();
        ownId = App.getApp().getPreferenceManager().getUser().getId();
        initDrawer();
        initToolbar();
        initRecycleView();
        int userId = getIntent().getIntExtra(BasePresenter.USER_ID, 0);
        presenter = new FriendsPresenter(this,FriendsActivity.this, userId);
        presenter.initFriends();
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

    @Override
    public void setUserFriends(List<User> data) {
        Log.d("List",data.size()+"");
        friends.addAll(data);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.findViewById(R.id.progress).setVisibility(View.GONE);
    }

    private void initRecycleView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        RecyclerView.Adapter adapter = new FriendAdapter(friends, new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item) {
                presenter.launchUserActivity(item.getId());
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
