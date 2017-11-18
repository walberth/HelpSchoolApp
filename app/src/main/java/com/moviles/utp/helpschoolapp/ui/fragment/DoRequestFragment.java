package com.moviles.utp.helpschoolapp.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.moviles.utp.helpschoolapp.ContainerActivity;
import com.moviles.utp.helpschoolapp.R;
import com.moviles.utp.helpschoolapp.data.model.ListRequestType;
import com.moviles.utp.helpschoolapp.data.model.SendRequest;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
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
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoRequestFragment extends Fragment{
    UserResponse userResponse = ContainerActivity.userResponse;
    private static final String TAG = "DoRequestActivity";
    private static final String URL_WS = "http://wshelpdeskutp.azurewebsites.net/listRequestType/";
    private static final String URL_WS2 = "http://wshelpdeskutp.azurewebsites.net/createRequestByAppl/";
    private String username = userResponse.getUsername();
    private String type = "0";
    private Integer idRequestType;
    private EditText description;
    private String value;
    private int id;

    public DoRequestFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_do_request, container, false);
        new GetRequestType().execute(username, type, view);
        description = (EditText) view.findViewById(R.id.detail_request);
        //description.getText();
        Log.d(TAG, "description:" + description);
        Button sendR = (Button) view.findViewById(R.id.sendRequest);
        sendR.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "description:" + description.getText());
                new SendUserRequest().execute(username,idRequestType,description.getText().toString());
            }
        });
        return view;
    }

    private class GetRequestType extends AsyncTask<Object, Void, Void> {
          private ProgressDialog dialog = new ProgressDialog(getActivity());
        private String content;
        private View view;
        private ListRequestType listRequestType;

        private ArrayList<ListRequestType> listRequestTypeList = new ArrayList<>();

        protected void onPreExecute() {
            dialog.setMessage("Espere por favor...");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Object... strings) {

            postData((String) strings[0], (String) strings[1]);
            view = (View) strings[2];

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            JSONArray jsonResponse;

            try {

                jsonResponse = new JSONArray(content);

                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject jsonObject = jsonResponse.getJSONObject(i);
                    listRequestType = new ListRequestType(
                            Integer.parseInt(jsonObject.optString("idRequestType")),
                            jsonObject.getString("labelRequestType"));
                    //Integer.parseInt(jsonObject.optString("idStatus"))


                    listRequestTypeList.add(listRequestType);
                }

                if (!listRequestTypeList.isEmpty()) {
                    dialog.dismiss();
                    Spinner typeRequesSpinner = (Spinner) view.findViewById(R.id.spinner);
                    ArrayAdapter<ListRequestType> adapter = new ArrayAdapter<ListRequestType>(getContext(), android.R.layout.simple_spinner_item, listRequestTypeList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    typeRequesSpinner.setAdapter(adapter);
                    typeRequesSpinner.setSelection(adapter.getPosition(listRequestTypeList.get(0)));
                    typeRequesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ListRequestType obj = (ListRequestType) parent.getItemAtPosition(position);
                            value = (String) obj.getRequestName();
                            idRequestType = (Integer) obj.getId();
                            Log.d(TAG, "Spinner Value:" + value);
                            Log.d(TAG, "Spinner id:" + id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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
                dialog.dismiss();
            }
        }

        private void postData(String username, String type) {

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

            Log.d(TAG, "convertJSONObjectToStringParams: ends");
            return stringBuilder.toString();
        }
    }

    //ENVIO DE SOLICITUD

    private class  SendUserRequest extends AsyncTask<Object,Void,Void>{
        private ProgressDialog dialogo = new ProgressDialog(getActivity());
        private String content;
        private View view;
        private SendRequest confirmation;

        protected void onPreExecute(){
            dialogo.setMessage("Enviando solicitud...");
            dialogo.show();
            dialogo.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Object... strings) {
            String user = (String) strings[0];
            Integer idRequestType = (Integer) strings[1];
            String description = (String) strings[2];


            postData(user,  idRequestType, description);

            return null;
        }

        protected void onPostExecute(Void aVoid){
            JSONObject jsonResponse;

            try {
                jsonResponse = new JSONObject(content);
                if (jsonResponse != null) {
                    String respuesta = jsonResponse.getString("status");
                    if (respuesta.equals("Created"))
                       Toast.makeText(getActivity(),"Solicitud enviada exitosamente", Toast.LENGTH_LONG).show();
                    dialogo.hide();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                dialogo.hide();
            }
        }

        private void postData(String name, int idRequest, String description) {
            URL url = null;

            try {
                url = new URL(URL_WS2);
                JSONObject objParams = new JSONObject();
                objParams.put("nameAppl",name);
                objParams.put("idRequestType",idRequest);
                objParams.put("description",description);
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private String convertJSONObjectToStringParams(JSONObject params) throws Exception{
            StringBuilder stringBuilder = new StringBuilder();
            boolean first = true;
            Iterator<String> iterator = params.keys();

            while(iterator.hasNext()){
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


