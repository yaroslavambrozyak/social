package com.example.yaroslav.scorpionssocial.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.yaroslav.scorpionssocial.model.UserRegistrationInfo;

public class PreferenceManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "preferences";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_TOKEN = "user_token";

    @SuppressLint("CommitPrefEdits")
    public PreferenceManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void saveUser(UserRegistrationInfo user) {
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_TOKEN, user.getToken());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.commit();
    }

    public UserRegistrationInfo getUser() {
        if (sharedPreferences.getInt(KEY_USER_ID, 0) != 0) {
            int id = sharedPreferences.getInt(KEY_USER_ID, 0);
            String token = sharedPreferences.getString(KEY_USER_TOKEN, null);
            String name = sharedPreferences.getString(KEY_USER_NAME,null);
            return new UserRegistrationInfo(id, token,name,"email");
        }
        return null;
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
