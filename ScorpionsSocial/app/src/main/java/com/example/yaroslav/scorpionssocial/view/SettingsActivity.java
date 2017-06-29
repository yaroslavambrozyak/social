package com.example.yaroslav.scorpionssocial.view;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yaroslav.scorpionssocial.R;
import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.PersonalInfo;
import com.example.yaroslav.scorpionssocial.presenter.SettingsPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.SettingsPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.SettingsActivityInterface;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsActivityInterface {

    private SettingsPresenterInterface presenter;
    private int id;

    @BindView(R.id.settings_name)
    EditText name;
    @BindView(R.id.settings_surname)
    EditText surname;
    @BindView(R.id.settings_date)
    EditText date;
    @BindView(R.id.settings_phone)
    EditText phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initDrawer();
        initToolbar();
        id = App.getApp().getPreferenceManager().getUser().getId();
        presenter = new SettingsPresenter(this, SettingsActivity.this, id);
        presenter.initSettings();
    }

    @OnClick(R.id.settings_date)
    public void onDataClick() {
        DialogFragment dialogFragment = new DataPicker();
        dialogFragment.show(getFragmentManager(), "aaa");
    }

    @Override
    protected void launchUserActivity() {
        presenter.launchUserActivity(id);
    }

    @Override
    protected void launchFriendsActivity() {
        presenter.launchFriendsActivity(id);
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
    public void setHintName(String message) {
        name.setHint(message);
    }

    @Override
    public void setHintSurName(String message) {
        surname.setHint(message);
    }

    @Override
    public void setHintBirthDay(String message) {
        date.setHint(message);
    }

    @Override
    public void setHintPhone(String message) {
        phone.setHint(message);
    }

    @Override
    public void showToastError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    private void changeSettings() {
        PersonalInfo personalInfo = new PersonalInfo();
        if (name.getText().toString().isEmpty()) {
            personalInfo.setFirstName(name.getHint().toString());
        } else {
            personalInfo.setFirstName(name.getText().toString());
        }
        if (surname.getText().toString().isEmpty()) {
            personalInfo.setLastName(surname.getHint().toString());
        } else {
            personalInfo.setLastName(surname.getText().toString());
        }
        if (date.getText().toString().isEmpty()) {
            personalInfo.setBirthDate(new Date(date.getHint().toString()));
        } else {
            personalInfo.setBirthDate(new Date(date.getText().toString()));
        }

        if (phone.getText().toString().isEmpty()) {
            personalInfo.setPhoneNumber(phone.getHint().toString());
        } else {
            personalInfo.setPhoneNumber(phone.getText().toString());
        }

        presenter.setSettings(personalInfo);
    }
}
