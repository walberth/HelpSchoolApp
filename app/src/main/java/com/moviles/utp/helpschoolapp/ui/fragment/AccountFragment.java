package com.moviles.utp.helpschoolapp.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moviles.utp.helpschoolapp.ContainerActivity;
import com.moviles.utp.helpschoolapp.LoginActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.data.storage.UserSessionManager;
import com.moviles.utp.helpschoolapp.model.User;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private TextView username, fullname, email, job;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        UserResponse userResponse = ContainerActivity.userResponse;
        username = (TextView) view.findViewById(R.id.username);
        username.setText(userResponse.getUsername());
        fullname = (TextView) view.findViewById(R.id.fullname);
        fullname.setText(userResponse.getName() + " " + userResponse.getFatherLastname() + " " + userResponse.getMotherLastname());
        email = (TextView) view.findViewById(R.id.email);
        email.setText(userResponse.getEmail());
        job = (TextView) view.findViewById(R.id.job);
        job.setText(userResponse.getProfile());
        return view;
    }

}
