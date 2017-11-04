package com.moviles.utp.helpschoolapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
    }
    public void onClick(View view){
        Intent intent =  new Intent(this,ContainerActivity.class);
        startActivity(intent);
    }
}
