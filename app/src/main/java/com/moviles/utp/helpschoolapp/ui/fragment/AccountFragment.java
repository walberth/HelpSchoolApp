package com.moviles.utp.helpschoolapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.moviles.utp.helpschoolapp.ContainerActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private TextView username, fullname, email, job;
    private Button logout;

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
        logout = (Button) view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContainerActivity.session.logoutSession();
            }
        });
        return view;
    }

}
