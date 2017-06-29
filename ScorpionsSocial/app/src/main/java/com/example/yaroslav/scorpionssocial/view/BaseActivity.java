package com.example.yaroslav.scorpionssocial.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.UserRegistrationInfo;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    protected final Handler handler = new Handler();
    protected static final int DRAWER_CLOSE_DELAY = 250;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                launchSettingsActivity();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (id == R.id.nav_friends) {
                    launchFriendsActivity();
                } else if (id == R.id.nav_message) {
                    launchConversationActivity();
                } else if (id == R.id.nav_settings) {
                    launchSettingsActivity();
                }
            }
        },DRAWER_CLOSE_DELAY);
        return true;
    }

    //todo fix
    public void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationHeader = navigationView.getHeaderView(0);
        TextView name = (TextView) navigationHeader.findViewById(R.id.header_name);
        //TextView email = (TextView) navigationHeader.findViewById(R.id.header_email);
        CircleImageView profileImage = (CircleImageView) navigationHeader.findViewById(R.id.image_view);
        Picasso picasso = App.getApp().getPicasso();
        UserRegistrationInfo user = App.getApp().getPreferenceManager().getUser();
        String url = String.format("http://localhost:8081/api/users/%d/picture", user.getId());
        picasso.load(url).centerInside().resize(175,175).into(profileImage);
        name.setText(user.getName());
        navigationHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchUserActivity();
                    }
                }, DRAWER_CLOSE_DELAY);
            }
        });
    }

    public void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    protected abstract void launchUserActivity();

    protected abstract void launchFriendsActivity();

    protected abstract void launchConversationActivity();

    protected abstract void launchSettingsActivity();

    protected abstract void logout();
}
