package com.moviles.utp.helpschoolapp.helper.ui;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by Gustavo Ramos M. on 14/11/2017.
 */

public class Splash extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(1000);
    }
}
