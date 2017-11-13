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
import com.moviles.utp.helpschoolapp.ContainerActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.EventsResponse;
import com.moviles.utp.helpschoolapp.data.model.HistoricalResponse;
import com.moviles.utp.helpschoolapp.helper.controller.VolleyController;
import com.moviles.utp.helpschoolapp.helper.utils.Dates;
import com.moviles.utp.helpschoolapp.helper.utils.FormatDate;
import com.moviles.utp.helpschoolapp.ui.adapter.HistoricalAdapterRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricalFragment extends Fragment {

    private static String TAG = HistoricalFragment.class.getSimpleName();
    private static final String urlWs = "http://wshelpdeskutp.azurewebsites.net/getLog/";
    private ProgressDialog dialog;
    private List<EventsResponse> dateList;
    private RecyclerView historicalRecycler;

    public HistoricalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historical, container, false);
        dialog = new ProgressDialog(getActivity());
        historicalRecycler = (RecyclerView) view.findViewById(R.id.historicalRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        historicalRecycler.setLayoutManager(linearLayoutManager);
        getHistoricalEvents(urlWs);
        return view;
    }

    private void getHistoricalEvents(String url) {
        showDialog();
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dateList = new ArrayList<>();
                        Map<Date, List<HistoricalResponse>> dateMap = new LinkedHashMap<>();
                        try {
                            Log.d(TAG, "Response: " + response.toString());
                            JSONArray jsonArray = new JSONArray(response.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject childNode = (JSONObject) jsonArray.getJSONObject(i);
                                HistoricalResponse historicalResponse = new HistoricalResponse(
                                        Dates.convertDateWithFormat(childNode.optString("dateEvent"), FormatDate.getDateFormatAmerican()),
                                        childNode.optString("nameUser"),
                                        childNode.optString("dateEvent"),
                                        childNode.optString("evnt"),
                                        childNode.optString("status"));
                                if (dateMap.containsKey(historicalResponse.getDate())) {
                                    dateMap.get(historicalResponse.getDate()).add(historicalResponse);
                                } else {
                                    dateMap.put(historicalResponse.getDate(), new ArrayList<>(Arrays.asList(historicalResponse)));
                                }
                            }
                            if (!dateMap.isEmpty()) addMapItemsToList(dateMap);
                            historicalRecycler.setAdapter(new HistoricalAdapterRecyclerView(dateList, R.layout.cardview_historical, getActivity()));
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        } catch (ParseException e) {
                            //Parseo Date error
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
                //params.put("username", ContainerActivity.userResponse.getUsername());
                params.put("username", "GUSTAVO.RAMOS");
                return params;
            }
        };
        //Agrega request a cola de request por Volley
        VolleyController.getInstance(getContext()).addToRequestQueue(request);
    }

    private void showDialog() {
        if (!dialog.isShowing()) {
            dialog.setMessage("Espere por favor...");
            dialog.show();
            dialog.setCancelable(false);
        }
    }

    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    private List<EventsResponse> addMapItemsToList(Map<Date, List<HistoricalResponse>> mapResponse) {
        for (Map.Entry<Date, List<HistoricalResponse>> map : mapResponse.entrySet()) {
            dateList.add(new EventsResponse(map.getKey(), map.getValue()));
        }
        return dateList;
    }
}
