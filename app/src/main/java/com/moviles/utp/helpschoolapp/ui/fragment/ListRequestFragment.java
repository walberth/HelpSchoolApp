package com.moviles.utp.helpschoolapp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moviles.utp.helpschoolapp.R;

/**
 * Created by Gustavo Ramos M. on 31/10/2017.
 */

public class ListRequestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_request, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //startAnimations();
    }

    /*private void startAnimations(){
        fadeIn(R.i);
        fadeIn(imageViewMeetup,1000,500);
        fadeIn(imageViewFacebook,1000,700);
        fadeIn(imageViewTwitter,1000,900);
        fadeIn(imageViewYoutube,1000,1000);
    }*/

}
