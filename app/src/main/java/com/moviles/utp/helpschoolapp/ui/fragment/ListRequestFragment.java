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
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moviles.utp.helpschoolapp.ContainerActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.PendingRequestResponse;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.helper.Enum.ProfileEnum;
import com.moviles.utp.helpschoolapp.ui.adapter.ListRequestAdapterRecyclerView;
import org.json.JSONArray;
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
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */

public class ListRequestFragment extends Fragment {
    private static final String TAG = "PendingResponseActivity";
    private static final String URL_WS = "http://wshelpdeskutp.azurewebsites.net/listRequest/";

    UserResponse userResponse = ContainerActivity.userResponse;

    private String username = userResponse.getUsername();
    private String profileType = userResponse.getProfile();
    private String type = "";
    private View mView;

    public ListRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start inflate fragment_list_request");
        this.setHasOptionsMenu(true);

        mView = inflater.inflate(R.layout.fragment_list_request, container, false);

        if(profileType.equals(ProfileEnum.ADMINISTRATOR_Pending.getType()))
            type = ProfileEnum.ADMINISTRATOR_Pending.getId();
        else
            type = ProfileEnum.REQUESTER_Pending.getId();

        new GetPendingResponse().execute(username, type, mView);

        ShowToolbar("", false, mView);

        return mView;
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
                            jsonObject.getString("timeStampCReq")
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

                    ListRequestAdapterRecyclerView listRequestAdapterRecyclerView =
                            new ListRequestAdapterRecyclerView(pendingRequestResponseList, R.layout.cardview_pending_response, getActivity());
                    listRequestRecycler.setAdapter(listRequestAdapterRecyclerView);

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

        switch (idItem){
            case R.id.mnuAtendida:
                //item.setChecked(!item.isChecked());
                if(profileType.equals(ProfileEnum.ADMINISTRATOR_Response.getType()))
                    type = ProfileEnum.ADMINISTRATOR_Response.getId();
                else
                    type = ProfileEnum.REQUESTER_Response.getId();
                break;
            case R.id.mnuPendiente:
                //item.setChecked(!item.isChecked());
                if(profileType.equals(ProfileEnum.ADMINISTRATOR_Pending.getType()))
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

    public void ShowToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    public class EditRequest extends AsyncTask<Object, Void, Void>{
        private ProgressDialog dialog = new ProgressDialog(getActivity());
        AlertDialog.Builder alertEditText = new AlertDialog.Builder(getActivity());

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Espere por favor...");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final EditText editText = new EditText(getContext());

            alertEditText.setMessage("Enter Your Message");
            alertEditText.setTitle("Enter Your Title");
            alertEditText.setView(editText);

            alertEditText.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //What ever you want to do with the value
                    Editable YouEditTextValue = editText.getText();
                    //OR
                    //String YouEditTextValue = editText.getText().toString();
                }
            });

            alertEditText.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });

            alertEditText.show();
        }

        public void pruebaDialog(){
            AlertDialog.Builder alertEditText = new AlertDialog.Builder(getActivity());
            final EditText editText = new EditText(getContext());
            alertEditText.setMessage("Enter Your Message");
            alertEditText.setTitle("Enter Your Title");
            alertEditText.setView(editText);

            alertEditText.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //What ever you want to do with the value
                    Editable YouEditTextValue = editText.getText();
                    //OR
                    //String YouEditTextValue = editText.getText().toString();
                }
            });

            alertEditText.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // what ever you want to do with No option.
                }
            });

            alertEditText.show();
        }

        @Override
        protected Void doInBackground(Object... objects) {
            return null;
        }
    }
}