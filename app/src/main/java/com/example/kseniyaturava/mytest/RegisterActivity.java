package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    Button btRegister;
    EditText etPassword, etEmail, etUser, etNombre, etEdad, etCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        // Customize action bar title to center and other styles
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_register);

        //  Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back36);


        btRegister = (Button) findViewById(R.id.btRegister);
        etPassword = (EditText) findViewById(R.id.password);
        etEmail = (EditText) findViewById(R.id.email);
        etUser = (EditText) findViewById(R.id.user);
        etNombre = (EditText) findViewById(R.id.nombre);
        etEdad = (EditText) findViewById(R.id.edad);
        etCiudad = (EditText) findViewById(R.id.ciudad);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (!(etEmail.getText().toString().equals("")) && !(etUser.getText().toString().equals("")) && !(etNombre.getText().toString().equals("")) && !(etEdad.getText().toString().equals("")) && !(etCiudad.getText().toString().equals("")) && !(etPassword.getText().toString().equals(""))){
                                insertarUser(etEmail.getText().toString(), etUser.getText().toString(), etNombre.getText().toString(), etEdad.getText().toString(), etCiudad.getText().toString(), etPassword.getText().toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "Bienvenido a OurMovie!!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.putExtra("User", etUser.getText().toString());
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Falta por rellenar algun campo", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                tr.start();
            }
        });
    }

    public void insertarUser (String email, String user, String nombre, String edad, String ciudad, String password) throws IOException {
        URL url=null;
        int respuesta;
        HttpURLConnection conection=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/RegisterUser.php?Email_User="+email+"&User="+user+"&Nombre_User="+nombre+"&Edad_User="+edad+"&Ciudad_User="+ciudad+"&Password="+password);
            conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            conection.disconnect();
        }
    }
    //Button back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
