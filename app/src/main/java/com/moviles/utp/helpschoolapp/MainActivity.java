package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    Button login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login =  findViewById(R.id.btnLogin);
        login.setOnClickListener(this);
    }


    /*@Override
    public void OnClick(View view){
        EditText txtName = (EditText) findViewById(R.id.txtUser);
        Intent intent = new Intent(this, VerificationActivity.class);
        intent.putExtra("name", txtName.getText().toString());
        startActivityForResult(intent, 123);
    }*/

    @Override
    public void onClick(View view) {
       // EditText txtName = (EditText) findViewById(R.id.txtUser);
        Intent intent = new Intent(this, SelectRequestActivity.class);
        //intent.putExtra("name", txtName.getText().toString());
        startActivity(intent);
    }
}
