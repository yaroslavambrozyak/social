package com.example.yaroslav.scorpionssocial.presenter.interfaces;


import android.net.Uri;
import android.widget.ImageView;

public interface UserPresenterInterface extends BaseInterface {
    void initUserProfile(ImageView imageView);
    void changeImage(Uri imageUri);
}
