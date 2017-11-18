package com.moviles.utp.helpschoolapp.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moviles.utp.helpschoolapp.ContainerActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.PendingRequestResponse;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.helper.Enum.ProfileEnum;
import com.moviles.utp.helpschoolapp.helper.controller.VolleyController;
import com.moviles.utp.helpschoolapp.helper.utils.Dates;
import com.moviles.utp.helpschoolapp.helper.utils.FormatDate;
import com.moviles.utp.helpschoolapp.ui.adapter.ListRequestAdapterRecyclerView;
import com.moviles.utp.helpschoolapp.ui.adapter.RecyclerItemTouchHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */

public class ListRequestFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private static final String TAG = "PendingResponseActivity";
    private static final String URL_WS = "http://wshelpdeskutp.azurewebsites.net/listRequest/";
    private static final String URL_WS_REMOVE = "http://wshelpdeskutp.azurewebsites.net/removeReqBAppl/";

    UserResponse userResponse = ContainerActivity.userResponse;

    private String username = userResponse.getUsername();
    private String profileType = userResponse.getProfile();
    private String type = "";
    private View mView;
    private ProgressDialog dialog;
    private ListRequestAdapterRecyclerView listRequestAdapterRecyclerView;
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback;

    public ListRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start inflate fragment_list_request");
        this.setHasOptionsMenu(true);

        mView = inflater.inflate(R.layout.fragment_list_request, container, false);

        if (profileType.equals(ProfileEnum.ADMINISTRATOR_Pending.getType()))
            type = ProfileEnum.ADMINISTRATOR_Pending.getId();
        else
            type = ProfileEnum.REQUESTER_Pending.getId();

        new GetPendingResponse().execute(username, type, mView);

        ShowToolbar("", false, mView);
        itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        return mView;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ListRequestAdapterRecyclerView.ListRequestViewHolder) {
            Integer id = listRequestAdapterRecyclerView.removeItem(position);
            listRequestAdapterRecyclerView.notifyDataSetChanged();
            deleteRequest(URL_WS_REMOVE, id);
        }
    }

    private class GetPendingResponse extends AsyncTask<Object, Void, Void> {
        private ProgressDialog dialog = new ProgressDialog(getActivity());
        private String content;
        private View view;
        private PendingRequestResponse pendingRequestResponse;
        private ArrayList<PendingRequestResponse> pendingRequestResponseList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: start");

            dialog.setMessage("Espere por favor...");
            dialog.show();
            dialog.setCancelable(false);

            Log.d(TAG, "onPreExecute: ends");
        }

        @Override
        protected Void doInBackground(Object... strings) {
            Log.d(TAG, "doInBackground: start");

            postData((String) strings[0], (String) strings[1]);
            view = (View) strings[2];

            Log.d(TAG, "doInBackground: ends");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: start");

            JSONArray jsonResponse;

            try {
                //jsonResponse = new JSONObject(content);
                jsonResponse = new JSONArray(content);

                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject jsonObject = jsonResponse.getJSONObject(i);
                    pendingRequestResponse = new PendingRequestResponse(
                            jsonObject.getString("idRequest"),
                            Integer.parseInt(jsonObject.getString("idRequester")),
                            jsonObject.getString("usernameRequester"),
                            Integer.parseInt(jsonObject.getString("idRequestType")),
                            jsonObject.getString("request"),
                            jsonObject.getString("statusRequest"),
                            Dates.convertTimestampToFormatLocalDate(jsonObject.optString("timeStampCReq"), FormatDate.getDateFormatLocaleUntilSeconds())
                    );
                    pendingRequestResponseList.add(pendingRequestResponse);

                    Log.d(TAG, "onPostExecute: idRequest " + pendingRequestResponse.getIdRequest());
                }

                Log.d(TAG, jsonResponse.toString());

                if (!pendingRequestResponseList.isEmpty()) {
                    dialog.dismiss();

                    RecyclerView listRequestRecycler = (RecyclerView) view.findViewById(R.id.listRequestRecycler);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    listRequestRecycler.setLayoutManager(linearLayoutManager);

                    listRequestAdapterRecyclerView =
                            new ListRequestAdapterRecyclerView(pendingRequestResponseList, R.layout.cardview_pending_response, getActivity());
                    listRequestRecycler.setAdapter(listRequestAdapterRecyclerView);


                    new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(listRequestRecycler);

                } else {
                    dialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("No se encontraron solicitudes pendientes");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Intentar de Nuevo", //TODO: VALIDAR SI VA ESTE MENSAJE
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                Log.d(TAG, "onPostExecute: ends");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void postData(String username, String type) {
            Log.d(TAG, "postData: start");

            URL url = null;

            try {
                url = new URL(URL_WS);
                JSONObject objParams = new JSONObject();
                objParams.put("username", username);
                objParams.put("type", type);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(convertJSONObjectToStringParams(objParams));
                writer.flush();
                writer.close();
                outputStream.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer("");
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line);
                        break;
                    }

                    bufferedReader.close();
                    content = stringBuffer.toString();
                }

                Log.d(TAG, "postData: ends");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private String convertJSONObjectToStringParams(JSONObject params) throws Exception {
            Log.d(TAG, "convertJSONObjectToStringParams: start");

            StringBuilder stringBuilder = new StringBuilder();
            boolean first = true;
            Iterator<String> iterator = params.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = params.get(key);
                if (first) first = false;
                else stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(key, "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }

            Log.d(TAG, "convertJSONObjectToStringParams: ends");
            return stringBuilder.toString();
        }
    }

    private void deleteRequest(String url, final Integer id) {
        //showDialog();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Response 1: " + response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("Removed")) {
                        final android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getContext()).create();
                        alertDialog.setMessage("Solicitud se ha eliminado correctamente.");
                        alertDialog.setCancelable(true);
                        alertDialog.show();
                        final Timer timer2 = new Timer();
                        timer2.schedule(new TimerTask() {
                            public void run() {
                                alertDialog.dismiss();
                                timer2.cancel();
                            }
                        }, 2000);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                //hideDialog();
            }
        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        //hideDialog();
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("vId", id.toString());
                return params;
            }
        };
        //Agrega request a cola de request por Volley
        VolleyController.getInstance(getContext()).addToRequestQueue(request);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: start");
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.filter_menu, menu);
        Log.d(TAG, "onCreateOptionsMenu: ends");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();

        switch (idItem) {
            case R.id.mnuAtendida:
                //item.setChecked(!item.isChecked());
                if (profileType.equals(ProfileEnum.ADMINISTRATOR_Response.getType()))
                    type = ProfileEnum.ADMINISTRATOR_Response.getId();
                else
                    type = ProfileEnum.REQUESTER_Response.getId();
                break;
            case R.id.mnuPendiente:
                //item.setChecked(!item.isChecked());
                if (profileType.equals(ProfileEnum.ADMINISTRATOR_Pending.getType()))
                    type = ProfileEnum.ADMINISTRATOR_Pending.getId();
                else
                    type = ProfileEnum.REQUESTER_Pending.getId();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        new GetPendingResponse().execute(username, type, mView);

        return true;
    }

    public void ShowToolbar(String title, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Listado de solicitudes");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
}