package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRequestActivity extends Activity implements View.OnClickListener {

    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_request);
        send =  findViewById(R.id.btnSend);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,ListRequestActivity.class);
        //intent.putExtra("name", txtName.getText().toString());
        startActivity(intent);
    }
}
