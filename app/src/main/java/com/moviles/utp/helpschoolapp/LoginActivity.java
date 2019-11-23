package com.moviles.utp.helpschoolapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.moviles.utp.helpschoolapp.data.model.UserResponse;
import com.moviles.utp.helpschoolapp.data.storage.UserSessionManager;
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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL_WS = "http://wshelpdeskutp.azurewebsites.net/loginValidate/";
    private EditText username, password;
    private Button login;
    private TextInputLayout usernameWrap = null;
    private TextInputLayout passwordWrap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameWrap = (TextInputLayout) findViewById(R.id.usernameWrap);
        usernameWrap.setHint("Usuario");
        passwordWrap = (TextInputLayout) findViewById(R.id.passwordWrap);
        passwordWrap.setHint("Contraseña");
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        boolean isOk = true;
        TextInputLayout component = null;
        //Valida que campos usuario y clave tengan texto
        if (usernameWrap.getEditText().getText().length() == 0) {
            usernameWrap.setErrorEnabled(true);
            usernameWrap.setError("Ingrese usuario");
            isOk = false;
            if (component == null) {
                passwordWrap.setErrorEnabled(false);
                component = usernameWrap;
            }
        }
        if (passwordWrap.getEditText().getText().length() == 0) {
            passwordWrap.setErrorEnabled(true);
            passwordWrap.setError("Ingrese contraseña");
            isOk = false;
            if (component == null) {
                usernameWrap.setErrorEnabled(false);
                component = passwordWrap;
            }
        }
        if (isOk) {
            usernameWrap.setErrorEnabled(false);
            passwordWrap.setErrorEnabled(false);
            //new LoginAction().execute(usernameWrap.getEditText().getText().toString(), passwordWrap.getEditText().getText().toString());

            Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            if (component != null) {
                component.requestFocus();
            }
        }
    }

    private class LoginAction extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        private String content;
        private UserResponse user;

        @Override
        protected void onPreExecute() {
            //Inicia dialog con mensaje cargando
            dialog.setMessage("Espere por favor..");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            //Envia a metodo post parametros de login
            postData(params[0], params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONObject jsonResponse;
            try {
                jsonResponse = new JSONObject(content);
                user = new UserResponse(Integer.parseInt(jsonResponse.optString("id").toString()),
                        jsonResponse.optString("username"),
                        jsonResponse.optString("name"),
                        jsonResponse.optString("fatherLastname"),
                        jsonResponse.optString("motherLastname"),
                        jsonResponse.optString("email"),
                        jsonResponse.optString("perfil"));
                if (user.getId() != 0) {
                    dialog.dismiss();
                    new UserSessionManager(getApplicationContext(), user);
                    Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    dialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setMessage("Usuario y/o contraseña inválidos.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Intentar de nuevo",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();//
                                }
                            });
                    alertDialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void postData(String username, String password) {
            //Obtiene url
            URL url = null;
            try {
                //Asigna url
                url = new URL(URL_WS);
                //Crea objeto json para guardar parametros
                JSONObject objParams = new JSONObject();
                objParams.put("username", username);
                objParams.put("password", password);
                //Creacion de un nuevo HttpUrlConnection y header post
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);//milliseconds
                connection.setConnectTimeout(15000);//milliseconds
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(convertJSONObjectToStringParams(objParams));
                writer.flush();
                writer.close();
                outputStream.close();
                //Codigo de respuesta de servidor
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
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
