package com.moviles.utp.helpschoolapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moviles.utp.helpschoolapp.data.model.DetailResponse;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.helper.controller.VolleyController;
import com.moviles.utp.helpschoolapp.helper.utils.Dates;
import com.moviles.utp.helpschoolapp.helper.utils.FormatDate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Walberth Gutierrez Telles on 11/4/2017.
 */

public class DetailResponseActivity extends AppCompatActivity implements View.OnClickListener {

    UserResponse userResponse = ContainerActivity.userResponse;
    private static final String URL_WS = "http://wshelpdeskutp.azurewebsites.net/listResponseByRequestId/";
    private static final String URL_WS_SEND = "http://wshelpdeskutp.azurewebsites.net/creationAnsReqBExec/";
    private static final String TAG = "DetailResponseActivity";
    private TextView txtRequestDetail, txtFechaDetail, txtStatusRequest;
    private EditText txtDetailResponse;
    private ProgressDialog dialog;
    private DetailResponse mDetailResponse;
    private String requestId;
    private EditText txtWriteResponse;
    private Button btnSendResponse;
    private String username = userResponse.getUsername();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_response);

        requestId = getIntent().getStringExtra("requestId");

        dialog = new ProgressDialog(DetailResponseActivity.this);

        txtRequestDetail = (TextView) findViewById(R.id.txtRequestDetail);
        txtFechaDetail = (TextView) findViewById(R.id.txtFechaDetail);
        txtStatusRequest = (TextView) findViewById(R.id.txtStatusRequest);
        txtWriteResponse = (EditText) findViewById(R.id.txtWriteResponse);

        txtDetailResponse = (EditText) findViewById(R.id.txtDetailResponse);
        txtDetailResponse.setEnabled(false);

        btnSendResponse = (Button) findViewById(R.id.btnSendResponse);
        btnSendResponse.setOnClickListener(this);

        //getDetailResponse(URL_WS)
        wsCallback();

        //Log.d(TAG, "onCreate: status " + mDetailResponse.getStatus());
        //Log.d(TAG, "onCreate: requestType " + mDetailResponse.getRequestType());

        ShowToolbar("", true);
    }

    public interface VolleyCallback{
        void onSuccessResponse(String result);
    }

    @Override
    public void onClick(View v) {
        sendResponse(URL_WS_SEND);
    }

    public void wsCallback(){
        dialog.setMessage("Espere Por favor..");
        dialog.show();
        getDetailResponse(URL_WS,
                new DetailResponseActivity.VolleyCallback(){
                    @Override
                    public void onSuccessResponse(String result) {
                        try{
                            JSONObject jsonResponse = new JSONObject(result.toString());
                            mDetailResponse = new DetailResponse(
                                    jsonResponse.optString("status"),
                                    jsonResponse.optString("idRequestType"),
                                    jsonResponse.optString("timeStampCReq"),
                                    jsonResponse.getString("descriptionRequest")
                                    /*(jsonResponse.optString("descriptionResponseRequest").equals(""))
                                    ? jsonResponse.optString("description")
                                            : jsonResponse.optString("descriptionResponseRequest")*/
                           );

                            txtStatusRequest.setText(mDetailResponse.getStatus());
                            txtRequestDetail.setText(mDetailResponse.getRequestType());
                            txtFechaDetail.setText(mDetailResponse.getDateTime());
                            txtDetailResponse.setText(mDetailResponse.getDescription());
                            dialog.hide();
                        } catch (JSONException ex) {
                            dialog.hide();
                            Log.e(TAG, "onSuccessResponse: error " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void getDetailResponse(String url, final VolleyCallback callback) {


        //Object queue = VolleyController.getInstance(DetailResponseActivity.this).getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callback.onSuccessResponse(response);
//                            Log.d(TAG, "onResponse: " + response.toString());
//                            JSONObject jsonResponse = new JSONObject(response.toString());
//                            Log.d(TAG, String.valueOf(jsonResponse));
//                            mDetailResponse = new DetailResponse(
//                                    jsonResponse.optString("status"),
//                                    jsonResponse.optString("idRequestType"),
//                                    jsonResponse.optString("timeStampCReq"),
//                                    jsonResponse.getString("descriptionRequest")
//                                    /*(jsonResponse.optString("descriptionResponseRequest").equals(""))
//                                    ? jsonResponse.optString("description")
//                                            : jsonResponse.optString("descriptionResponseRequest")*/
//                            );
//
//                            Log.d(TAG, "onResponse: status " + mDetailResponse.getStatus());
//                            Log.d(TAG, "onResponse: request " + mDetailResponse.getRequestType());
//                            Log.d(TAG, "onResponse: dateTime " + mDetailResponse.getDateTime());
//                            Log.d(TAG, "onResponse: description " + mDetailResponse.getDescription());
//
//                            txtStatusRequest.setText(mDetailResponse.getStatus());
//                            txtRequestDetail.setText(mDetailResponse.getRequestType());
//                            txtFechaDetail.setText(mDetailResponse.getDateTime());
//                            txtDetailResponse.setText(mDetailResponse.getDescription());
//
//                            dialog.hide();
                        } catch (Exception ex) {
                            Log.e(TAG, "onResponse: error throw " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: error throw " + error.getMessage());
                        //dialog.hide();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idRequester", requestId); //SEND THE ID FOR THE REQUEST
                return params;
            }
        };
        VolleyController.getInstance(DetailResponseActivity.this).addToRequestQueue(request);
    }

    private void sendResponse(String url){
        //String response;
        dialog.setMessage("Enviando Información...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: " + response.toString());
                            JSONObject jsonResponse = new JSONObject(response.toString());
                            response = jsonResponse.optString("status");

                            dialog.hide();

                            if(response.equals("Created")){
                                Log.d(TAG, "onResponse: status " + response);
                                txtDetailResponse.setText("");
                            } else {
                                Toast.makeText(DetailResponseActivity.this, "No se grabo la respuesta", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Log.e(TAG, "onResponse: error throw " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: error throw " + error.getMessage());
                        dialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idReq", requestId); //SEND THE ID FOR THE REQUEST
                params.put("nameExec", username);
                params.put("description", txtWriteResponse.getText().toString());
                params.put("status", mDetailResponse.getStatus());
                return params;
            }
        };
        VolleyController.getInstance(DetailResponseActivity.this).addToRequestQueue(request);
    }

    public void ShowToolbar(String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Respuesta de Solicitud");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, ContainerActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
}