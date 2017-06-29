package com.example.yaroslav.scorpionssocial.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.UserRegistrationInfo;
import com.example.yaroslav.scorpionssocial.presenter.BasePresenter;
import com.example.yaroslav.scorpionssocial.presenter.UserPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.UserPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.UserActivityInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserActivity extends BaseActivity implements UserActivityInterface {

    private UserPresenterInterface presenter;
    private int ownId;
    private static final int RESULT_IMAGE = 1;
    private Uri imageUri;

    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.phone_number)
    TextView phone;
    @BindView(R.id.user_image_main)
    ImageView image;
    @BindView(R.id.progress)
    ProgressBar progress;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        initDrawer();
        initToolbarUser();
        Intent intent = getIntent();
        int userId = intent.getIntExtra(BasePresenter.USER_ID, 0);
        presenter = new UserPresenter(this, UserActivity.this, userId);
        presenter.initUserProfile(image);
        ownId = App.getApp().getPreferenceManager().getUser().getId();
    }

    public void refresh(){
        presenter.initUserProfile(image);
    }

    @OnClick(R.id.user_image_main)
    public void onUserImageClick() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("image","IN");
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            presenter.changeImage(imageUri);
        }

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
    public void setUserBirthDay(String birthDay) {
        birthday.setText(birthDay);
    }

    @Override
    public void setUserPhone(String phoneNumber) {
        phone.setText(phoneNumber);
    }

    @Override
    public void setUserName(String name) {
        Log.d("LOGGG", name);
        toolbar.setTitle(name);

    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.app_bar).setVisibility(View.INVISIBLE);
        birthday.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.app_bar).setVisibility(View.VISIBLE);
        birthday.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showToastError() {
        Toast.makeText(this, "Сталася помилка", Toast.LENGTH_LONG).show();
    }

    private void initToolbarUser() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
}
