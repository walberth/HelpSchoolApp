package com.moviles.utp.helpschoolapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.do_request);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {

            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.profile:
                        AccountFragment accountFragment = new AccountFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.requests:
                        ListRequestFragment listRequestFragment = new ListRequestFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, listRequestFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.do_request:
                        DoRequestFragment doRequestFragment = new DoRequestFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, doRequestFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.historical:
                        HistoricalFragment historicalFragment = new HistoricalFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, historicalFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });
    }
}
