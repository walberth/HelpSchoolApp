package com.moviles.utp.helpschoolapp.ui.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.PendingRequestResponse;

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
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRequestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;

    private static final String TAG = "PendingResponseActivity";
    private static final String URL_WS = "http://wshelpdeskutp.azurewebsites.net/listRequest/";
    //TODO: PENDIENTE DE CREAR ACCIONES ACÁ
    private String username = "GUSTAVO.RAMOS";
    private String type = "2";


    public ListRequestFragment() {
        // Required empty public constructor
    }

    public static ListRequestFragment newInstance(String param1, String param2, String param3){
        ListRequestFragment fragment = new ListRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_request, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new GetPendingResponse().execute(username, type);
    }

    private class GetPendingResponse extends AsyncTask<String, Void, Void> {
        private ProgressDialog dialog = new ProgressDialog(getActivity());
        private String content;
        private PendingRequestResponse pendingRequestResponse;

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Espere por favor...");
            dialog.show();
            dialog.setCancelable(false);
        }


        @Override
        protected Void doInBackground(String... strings) {
            postData(strings[0], strings[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONArray jsonResponse;

            try {
                //jsonResponse = new JSONObject(content);
                jsonResponse = new JSONArray(content);

                for(int i  = 0; i < jsonResponse.length(); i++) {
                    JSONObject jsonObject = jsonResponse.getJSONObject(i);
                    pendingRequestResponse = new PendingRequestResponse(
                            Integer.parseInt(jsonObject.getString("idRequest")),
                            Integer.parseInt(jsonObject.getString("idRequester")),
                            jsonObject.getString("usernameRequester"),
                            Integer.parseInt(jsonObject.getString("idRequestType")),
                            jsonObject.getString("request"),
                            jsonObject.getString("statusRequest"),
                            jsonObject.getString("timeStampCReq")
                    );
                }

                Log.d(TAG, jsonResponse.toString());


                if(pendingRequestResponse.getIdRequest() != 0){
                    dialog.dismiss();
                    //TODO: DEFINIR UN INTENT A UN ACTIVITY AQUI
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void postData(String username, String type){
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

                if(responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer("");
                    String line;

                    while((line = bufferedReader.readLine()) != null){
                        stringBuffer.append(line);
                        break;
                    }

                    bufferedReader.close();
                    content = stringBuffer.toString();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private String convertJSONObjectToStringParams(JSONObject params) throws Exception {
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

            return stringBuilder.toString();
        }
    }
}
