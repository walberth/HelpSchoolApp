package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.moviles.utp.helpschoolapp.data.model.PendingRequestResponse;

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

public class PendingResponseActivity extends Activity {
    private static final String TAG = "PendingResponseActivity";
    private static final String URL_WS = "http://wshelpdeskutp.azurewebsites.net/listRequest/";
    //TODO: PENDIENTE DE CREAR ACCIONES ACÁ
    private String username = "GUSTAVO.RAMOS";
    private String type = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_response);

        new GetPendingResponse().execute(username, type);
    }

    private class GetPendingResponse extends AsyncTask<String, Void, Void> {
        private ProgressDialog dialog = new ProgressDialog(PendingResponseActivity.this);
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
            JSONObject jsonResponse;

            try {
                jsonResponse = new JSONObject(content);
                pendingRequestResponse = new PendingRequestResponse(
                        Integer.parseInt(jsonResponse.optString("idRequest")),
                        Integer.parseInt(jsonResponse.optString("idRequester")),
                        jsonResponse.optString("usernameRequester"),
                        Integer.parseInt(jsonResponse.optString("idRequestType")),
                        jsonResponse.optString("request"),
                        jsonResponse.optString("statusRequest"),
                        jsonResponse.optString("timeStampCReq")
                );

                Log.d(TAG, jsonResponse.toString());


                if(pendingRequestResponse.getIdRequest() != 0){
                    dialog.dismiss();
                    //TODO: DEFINIR UN INTENT A UN ACTIVITY AQUI
                } else {
                    dialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(PendingResponseActivity.this).create();
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
