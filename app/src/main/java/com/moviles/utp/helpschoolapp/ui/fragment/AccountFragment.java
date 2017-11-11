package com.moviles.utp.helpschoolapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.storage.UserSessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private UserSessionManager session;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) finish();
        Map<String, String> user = session.getUserDetails();
        new UserResponse(user.get(UserSessionManager.KEY_USERNAME),
                user.get(UserSessionManager.KEY_NAME),
                user.get(UserSessionManager.KEY_FATHERLASTNAME),
                user.get(UserSessionManager.KEY_MOTHERLASTNAME),
                user.get(UserSessionManager.KEY_EMAIL),
                user.get(UserSessionManager.KEY_PROFILE));*/
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

}
