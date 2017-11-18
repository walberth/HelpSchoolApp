package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moviles.utp.helpschoolapp.data.model.DetailResponse;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.helper.controller.VolleyController;
import com.moviles.utp.helpschoolapp.ui.fragment.ListRequestFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String URL_GET_DETAIL_RESPONSE = "http://wshelpdeskutp.azurewebsites.net/listResponseByRequestId/";
    private static final String URL_GET_DETAIL_REQUEST = "http://wshelpdeskutp.azurewebsites.net/listResponseByRequestId/";
    private static final String URL_WS_EDIT_REQUEST = "http://wshelpdeskutp.azurewebsites.net/updateReqBAppl/";
    private static final String URL_WS_EDIT_RESPONSE = "http://wshelpdeskutp.azurewebsites.net/updateAnsReqBExec/";
    private static final String TAG = "EditActivity";
    UserResponse userResponse = ContainerActivity.userResponse;
    private TextView txtEditResquest;
    private Button btnEditRequest;
    private ProgressDialog dialog;
    private String profile = userResponse.getProfile();
    private DetailResponse mDetailResponse;
    private String requestId;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        requestId = getIntent().getStringExtra("requestId");

        dialog = new ProgressDialog(EditActivity.this);

        String URL;
        //Traer el id del request acá
        if(profile.equals("DIRECTIVO"))
            URL = URL_GET_DETAIL_RESPONSE;
        else
            URL = URL_GET_DETAIL_REQUEST;

        txtEditResquest = (TextView) findViewById(R.id.txtEditResquest);
        btnEditRequest = (Button) findViewById(R.id.btnEditRequest);
        btnEditRequest.setOnClickListener(this);

        getDetailToEdit(URL);
        ShowToolbar("",true);
    }

    @Override
    public void onClick(View v) {
        String URL;
        if(profile.equals("DIRECTIVO"))
            URL = URL_WS_EDIT_RESPONSE;
        else
            URL = URL_WS_EDIT_REQUEST;

        updateRequest(URL);
    }

    private void getDetailToEdit(String url) {
        dialog.setMessage("Espere Por favor..");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: " + response.toString());

                            JSONObject jsonResponse = new JSONObject(response.toString());
                            mDetailResponse = new DetailResponse(
                                    jsonResponse.optString("status"),
                                    jsonResponse.optString("idRequestType"),
                                    jsonResponse.optString("timeStampCReq"),
                                    jsonResponse.optString("descriptionResponseRequest").equals("")
                                            ? jsonResponse.optString("descriptionRequest")
                                            : jsonResponse.optString("descriptionResponseRequest")
                            );

                            /*Log.d(TAG, "onResponse: status " + mDetailResponse.getStatus());
                            Log.d(TAG, "onResponse: request " + mDetailResponse.getRequestType());
                            Log.d(TAG, "onResponse: dateTime " + mDetailResponse.getDateTime());*/
                            Log.d(TAG, "onResponse: description " + mDetailResponse.getDescription());

                            txtEditResquest.setText(mDetailResponse.getDescription());

                            dialog.hide();
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
                params.put("idRequester", requestId); //SEND THE ID FOR THE REQUEST
                return params;
            }
        };
        VolleyController.getInstance(EditActivity.this).addToRequestQueue(request);
    }

    public void updateRequest(String url) {
        dialog.setMessage("Espere Por favor..");
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

                            if(response.equals("Updated")){
                                Log.d(TAG, "onResponse: status " + response);

                                txtEditResquest.setText("");
//                                ListRequestFragment listRequestFragment = new ListRequestFragment();
//                                getSupportFragmentManager().beginTransaction().replace(R.id.container, listRequestFragment)
//                                        .setCustomAnimations(R.animator.slide_left_enter, R.animator.slide_left_exit,
//                                                R.animator.slide_right_enter, R.animator.slide_right_exit)
//                                        //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                                        .addToBackStack(null).commit();

                            } else {
                                Toast.makeText(EditActivity.this, "No se actualizo el mensaje", Toast.LENGTH_LONG).show();
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

                if(profile.equals("DIRECTIVO"))
                    params.put("idReq", requestId);
                else
                    params.put("vId", requestId);

                params.put("vDescription", txtEditResquest.getText().toString()); //SEND THE ID FOR THE REQUEST

                return params;
            }
        };
        VolleyController.getInstance(EditActivity.this).addToRequestQueue(request);
    }
    public void ShowToolbar(String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
