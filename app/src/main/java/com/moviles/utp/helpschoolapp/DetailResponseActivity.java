package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class DetailResponseActivity extends Activity {


    private static final String URL_WS = "";
    private TextView txtRequestDetail, txtFechaDetail, txtStatusRequest;
    private EditText txtDetailResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_response);

        txtRequestDetail = (TextView)findViewById(R.id.txtRequestDetail);
        txtFechaDetail = (TextView)findViewById(R.id.txtFechaDetail);
        txtStatusRequest = (TextView)findViewById(R.id.txtStatusRequest);
        txtDetailResponse = (EditText)findViewById(R.id.txtDetailResponse);

        //LLAMADA AL WEB SERVICE
    }


}
