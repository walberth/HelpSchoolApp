package com.moviles.utp.helpschoolapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.moviles.utp.helpschoolapp.ui.fragment.ListRequestFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.PendingResponseFragment;
import com.moviles.utp.helpschoolapp.ui.fragment.SelectRequestFragment;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigationView();
        //mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        if (bottomNavigationView != null) {
            Menu menu = bottomNavigationView.getMenu();
            pushFragment(new SelectRequestFragment());
            disableDefaultAnimation();
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    }
            );
        }
    }

    protected void selectFragment(MenuItem item) {

        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.action_home:
                pushFragment(new SelectRequestFragment());
                break;
            case R.id.action_dashboard:
                pushFragment(new ListRequestFragment());
                break;
            case R.id.action_notifications:
                pushFragment(new PendingResponseFragment());
                break;
        }
    }

    protected void pushFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragmentTransaction != null) {
                fragmentTransaction.replace(R.id.rootLayout, fragment, null);
                fragmentTransaction.commit();
            }
        }
    }

    private void disableDefaultAnimation() {
        overridePendingTransition(0, 0);
    }

}
