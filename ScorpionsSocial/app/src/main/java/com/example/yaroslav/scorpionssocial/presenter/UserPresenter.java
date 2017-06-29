package com.example.yaroslav.scorpionssocial.presenter;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.example.yaroslav.scorpionssocial.app.App;
import com.example.yaroslav.scorpionssocial.model.PersonalInfo;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.UserPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.UserActivityInterface;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter extends BasePresenter implements UserPresenterInterface {

    private UserActivityInterface view;
    private int id;
    private String token;
    private Context context;
    private Uri imageUri;
    private boolean isPersonalInfoReady;
    private boolean isPictureReady;

    private final String TAG = "UserTag";

    public UserPresenter(UserActivityInterface view, Context context, int id) {
        super(context);
        this.context = context;
        this.view = view;
        this.id = id;
        token = App.getApp().getPreferenceManager().getUser().getToken();
    }

    public void initUserProfile(ImageView imageView) {
        userPersonalInfoRequest();
        userPictureRequest(imageView);
    }

    @Override
    public void changeImage(Uri imageUri) {
        File file = new File(getPath(imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        String descriptionString = "";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        App.getApi().postUserPicture(id,token,body,description).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                    view.refresh();
                else
                    onFailure(call,new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });


    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    private void userPersonalInfoRequest() {
        view.showProgressBar();
        App.getApi().getUserPersonalInfo(id, token).enqueue(new Callback<PersonalInfo>() {
            @Override
            public void onResponse(Call<PersonalInfo> call, Response<PersonalInfo> response) {
                if (response.isSuccessful()) {
                    setUserPersonalInfo(response.body());
                    App.getApp().getPreferenceManager().getUser()
                            .setName(response.body().getFirstName() + " " + response.body().getLastName());
                    isPersonalInfoReady = true;
                    hideProgressBar();
                } else if (response.code() == 401)
                    launchLoginActivity();
                else
                    onFailure(call, new Throwable(response.message()));
            }

            @Override
            public void onFailure(Call<PersonalInfo> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                view.showToastError();
            }
        });
    }

    private void userPictureRequest(ImageView imageView) {
        Picasso picasso = App.getApp().getPicasso();
        String url = String.format("http://localhost:8081/api/users/%d/picture", id);
        picasso.load(url).resize(800, 600).centerCrop().into(imageView);
        isPictureReady = true;
        hideProgressBar();
    }

    private void setUserPersonalInfo(PersonalInfo personalInfo) {
        SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        view.setUserName(personalInfo.getFirstName() + " " + personalInfo.getLastName());
        if (personalInfo.getBirthDate() != null)
            view.setUserBirthDay(ss.format(personalInfo.getBirthDate()));
        else
            view.setUserBirthDay("Не вказано");
        if (personalInfo.getPhoneNumber() != null)
            view.setUserPhone(personalInfo.getPhoneNumber());
        else
            view.setUserPhone("Не вказано");
    }

    private synchronized void hideProgressBar() {
        if (isPersonalInfoReady && isPictureReady)
            view.hideProgressBar();
    }
}
