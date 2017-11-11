package com.moviles.utp.helpschoolapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.moviles.utp.helpschoolapp.model.User;
import com.moviles.utp.helpschoolapp.ui.fragment.AccountFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.DoRequestFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.HistoricalFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.ListRequestFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/////////import com.example.juanc.platziandroid.View.fragment.HomeFragment;

import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.data.storage.UserSessionManager;

import java.util.HashMap;
import java.util.Map;

public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = "ContainerActivity";
    private UserSessionManager session;
    public static UserResponse userResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        userResponse = getSessionSharedPreferences();
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.do_request);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {

            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.profile:
                        Log.d(TAG, "onTabSelected: start profile tab");

                        AccountFragment accountFragment = new AccountFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        Log.d(TAG, "onTabSelected: ends profile tabs");
                        break;
                    case R.id.requests:
                        Log.d(TAG, "onTabSelected: start request tab");

                        ListRequestFragment listRequestFragment = new ListRequestFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, listRequestFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        Log.d(TAG, "onTabSelected: ends request tab");
                        break;
                    case R.id.do_request:
                        Log.d(TAG, "onTabSelected: start do_request tab");

                        DoRequestFragment doRequestFragment = new DoRequestFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, doRequestFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        Log.d(TAG, "onTabSelected: end do_request tab");
                        break;
                    case R.id.historical:
                        Log.d(TAG, "onTabSelected: start historical tab");

                        HistoricalFragment historicalFragment = new HistoricalFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, historicalFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();

                        Log.d(TAG, "onTabSelected: end historical tab");
                        break;
                }
            }
        });
    }

    private UserResponse getSessionSharedPreferences() {
        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) finish();
        Map<String, String> user = session.getUserDetails();
        return new UserResponse(user.get(UserSessionManager.KEY_USERNAME),
                user.get(UserSessionManager.KEY_NAME),
                user.get(UserSessionManager.KEY_FATHERLASTNAME),
                user.get(UserSessionManager.KEY_MOTHERLASTNAME),
                user.get(UserSessionManager.KEY_EMAIL),
                user.get(UserSessionManager.KEY_PROFILE));
    }
}
