package com.moviles.utp.helpschoolapp.ui.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.HistoricalResponse;
import com.moviles.utp.helpschoolapp.helper.controller.VolleyController;
import com.moviles.utp.helpschoolapp.ui.adapter.HistoricalAdapterRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricalFragment extends Fragment {

    private static String TAG = HistoricalFragment.class.getSimpleName();
    private static final String urlWs = "http://wshelpdeskutp.azurewebsites.net/getLog/";
    private ProgressDialog dialog;
    private List<HistoricalResponse> data;

    public HistoricalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historical, container, false);
        dialog = new ProgressDialog(getContext());
        /*RecyclerView historicalRecycler = (RecyclerView) view.findViewById(R.id.historicalRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        historicalRecycler.setLayoutManager(linearLayoutManager);
        historicalRecycler.setAdapter(new HistoricalAdapterRecyclerView(new ArrayList<HistoricalResponse>(),
                R.layout.cardview_historical, getActivity()));*/
        getHistoricalEvents(urlWs);
        return view;
    }

    private void getHistoricalEvents(String url) {
        showDialog();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        data = new ArrayList<>();
                        try {
                            Log.d(TAG, "Response: " + response.toString());
                            JSONArray jsonArray = new JSONArray(response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject childNode = (JSONObject) jsonArray.getJSONObject(i);
                                data.add(new HistoricalResponse(childNode.optString("dateEvent"),
                                        childNode.optString("nameUser"),
                                        childNode.optString("dateEvent"),
                                        childNode.optString("evnt"),
                                        childNode.optString("status")));
                            }
                            Log.d(TAG, "Response: " + data.get(0).getDate());
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        hideDialog();
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", "GUSTAVO.RAMOS");
                return params;
            }
        };
        //Agrega request a cola de request por Volley
        VolleyController.getInstance(getContext()).addToRequestQueue(request);
    }

    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

}
