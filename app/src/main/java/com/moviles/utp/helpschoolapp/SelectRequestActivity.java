package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SelectRequestActivity extends Activity implements View.OnClickListener {

    Spinner querysSpinner;
    EditText text;
    Button send;
    Button querys;
    Button responses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_request);
        querysSpinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter data = ArrayAdapter
                .createFromResource(this, R.array.querys_array, android.R.layout.simple_spinner_item);
        querysSpinner.setAdapter(data);
        text = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.btnSend);
        send.setOnClickListener(this);
        querys = (Button) findViewById(R.id.btnQuerys);
        querys.setOnClickListener(this);
        responses = (Button) findViewById(R.id.btnResponse);
        responses.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
/*
        if (view == send) {
            text.setText("");
            text.requestFocus();
        } else if (view == querys) {
            Intent intent = new Intent(this, ListRequestActivity.class);
            startActivity(intent);
        } else if (view == responses) {
            String name = querysSpinner.getSelectedItem().toString();
            Intent intent = new Intent(this, ResponseActivity.class);
            Bundle objBundle = new Bundle();
            objBundle.putString("queryType",name);
            intent.putExtras(objBundle);
            startActivity(intent);
        }
*/

        // /intent.putExtra("name", txtName.getText().toString());

    }
}
