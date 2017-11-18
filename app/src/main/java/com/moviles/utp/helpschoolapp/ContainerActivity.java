package com.moviles.utp.helpschoolapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.moviles.utp.helpschoolapp.ui.fragment.AccountFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.AnsweredRequestFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.DoRequestFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.HistoricalFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.ListRequestFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.data.storage.UserSessionManager;
import java.util.Map;

public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = "ContainerActivity";
    public static UserSessionManager session;
    public static UserResponse userResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        userResponse = getSessionSharedPreferences();
        Log.d(TAG,userResponse.getProfile());

        if(userResponse.getProfile().equals("DIRECTIVO")){
            BottomBar bottomBarDirective = (BottomBar) findViewById(R.id.bottombar_directive);
            BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
            bottomBar.setVisibility(View.GONE);
            Log.d(TAG,"Bottombar" + bottomBarDirective);
            //bottomBarDirective.setDefaultTab(R.id.profile);
            bottomBarDirective.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(int tabId) {
                    switch (tabId){
                        case R.id.profile_directive:
                            AccountFragment accountFragment = new AccountFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                            break;
                        case R.id.list_request_directive:
                            ListRequestFragment listRequestFragment = new ListRequestFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, listRequestFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                          break;
                        case R.id.historical_directive:
                            HistoricalFragment historicalFragment= new HistoricalFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, historicalFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                          break;
                    }
                }
            });
        }else{
            BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
            BottomBar bottomBarDirective = (BottomBar) findViewById(R.id.bottombar_directive);
            bottomBarDirective.setVisibility(View.GONE);
            bottomBar.setDefaultTab(R.id.profile);
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {

               @Override
                public void onTabSelected(int tabId) {
                   Log.e(TAG, String.valueOf(tabId));
                    switch (tabId) {
                        case R.id.profile:
                            AccountFragment accountFragment = new AccountFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                            break;
                        case R.id.do_request:
                            DoRequestFragment doRequestFragment = new DoRequestFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, doRequestFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                            break;
                        case R.id.requests:
                             ListRequestFragment listRequestFragment= new ListRequestFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, listRequestFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                            break;

                       case R.id.historical:
                            HistoricalFragment historicalFragment1= new HistoricalFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, historicalFragment1)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                            break;
                    }
                }
            });
        }

        //BottomBar bottomBarDirective = (BottomBar) findViewById(R.id.bottombar_directive);

    }

    public UserResponse getSessionSharedPreferences() {
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
