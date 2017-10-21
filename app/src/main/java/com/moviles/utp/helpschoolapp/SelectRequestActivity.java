package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SelectRequestActivity extends Activity implements View.OnClickListener {

    EditText text;
    Button send;
    Button querys;
    Button responses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_request);
        text = findViewById(R.id.editText);
        send = findViewById(R.id.btnSend);
        send.setOnClickListener(this);
        querys = findViewById(R.id.btnQuerys);
        querys.setOnClickListener(this);
        responses = findViewById(R.id.btnResponse);
        responses.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == send) {
            text.setText("");
            text.requestFocus();
        } else if (view == querys) {
            Intent intent = new Intent(this, ListRequestActivity.class);
            startActivity(intent);
        } else if (view == responses) {
            Intent intent = new Intent(this, ResponseActivity.class);
            startActivity(intent);
        }


        //intent.putExtra("name", txtName.getText().toString());

    }
}
