package com.example.yaroslav.scorpionssocial.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.yaroslav.scorpionssocial.presenter.MainPresenter;
import com.example.yaroslav.scorpionssocial.presenter.interfaces.MainPresenterInterface;
import com.example.yaroslav.scorpionssocial.view.interfaces.MainActivityInterface;

public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPresenterInterface presenter = new MainPresenter(this, MainActivity.this);
        presenter.checkApp();
        presenter.initSignalR();
    }

    @Override
    public void showNoConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Немає підключення до інтернету")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
