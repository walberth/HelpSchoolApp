package com.moviles.utp.helpschoolapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ListRequestActivity extends Activity {

    Spinner querysSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_request);
        querysSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter data = ArrayAdapter
                .createFromResource(this, R.array.number_array, android.R.layout.simple_spinner_item);
        querysSpinner.setAdapter(data);
    }



}
