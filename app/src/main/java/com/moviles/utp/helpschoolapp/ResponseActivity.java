package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseActivity extends Activity {

    TextView text;
    TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_reply);
        text = (TextView) findViewById(R.id.textView3);
        dateText = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        Bundle objBundle = intent.getExtras();
        String data = objBundle.getString("queryType");
        text.setText("Solicitud: " + data);
        dateText.setText("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }
}
