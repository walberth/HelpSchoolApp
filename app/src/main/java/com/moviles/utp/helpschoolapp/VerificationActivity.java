package com.moviles.utp.helpschoolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by juanc on 21/10/2017.
 */

public class VerificationActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_request);

    }
    public void Cargar(){
        Bundle extras = getIntent().getExtras();
        String s = extras.getString("usuario").toString();
        TextView txtResultado;
        txtResultado = (TextView)findViewById(R.id.txtUser);
        txtResultado.setText("Hola " + s + " ,¿Aceptas las condiciones?");
    }

    @Override
    public void onClick(View view) {
        // EditText txtName = (EditText) findViewById(R.id.txtUser);
        Intent intent = new Intent(this, ListRequestActivity.class);
        //intent.putExtra("name", txtName.getText().toString());
        startActivity(intent);
    }
}
