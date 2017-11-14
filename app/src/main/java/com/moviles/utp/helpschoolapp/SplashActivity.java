package com.moviles.utp.helpschoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.moviles.utp.helpschoolapp.data.storage.UserSessionManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        UserSessionManager session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) finish();
        else {
            startActivity(new Intent(this, ContainerActivity.class));
            finish();
        }
    }
}
