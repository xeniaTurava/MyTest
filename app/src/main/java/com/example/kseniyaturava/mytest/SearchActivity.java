package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private String user;
    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                           // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
                            intent0.putExtra("User", user);
                            startActivity(intent0);
                            return true;
                        case R.id.searchItem:
                            //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                            intent1.putExtra("User", user);
                            startActivity(intent1);
                            return true;
                        case R.id.formItem:
                            //startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            Intent intent2 = new Intent(getApplicationContext(), FormActivity.class);
                            intent2.putExtra("User", user);
                            startActivity(intent2);
                            return true;
                        case R.id.notificationItem:
                            //startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            Intent intent3 = new Intent(getApplicationContext(), AlertsActivity.class);
                            intent3.putExtra("User", user);
                            startActivity(intent3);
                            return true;
                        case R.id.profileItem:
                            // startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent4.putExtra("User", user);
                            startActivity(intent4);
                            return true;
                    }
                    // finish();
                    return false;
                }
            };


    ArrayAdapter<String> adapter;
    ListView lv;
    ArrayList<String> arrayMovies;
    protected String titulo="";
    Button btInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lv=(ListView)findViewById(R.id.listViewMovies);
        arrayMovies=new ArrayList<>();
        lv.setBackgroundColor(Color.WHITE);
        btInsertar=(Button) findViewById(R.id.btInsertar);


        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Search");//Set the title ActionBar
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView );

        // Ensure correct menu item is selected
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }

        Thread tr=new Thread(){
            @Override
            public void run() {
                try {
                    final String res=recogerTitulos();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r=objJSON(res);
                            if(r>0){
                                int inicio=0;
                                int longitud;
                                String palabra;
                                for (int i=0;i<res.length();i++) {
                                    if (res.charAt(i) == ('"') && res.charAt(i + 1) == ('}')) {
                                        longitud = i;
                                        palabra = res.substring(inicio, longitud);
                                        inicio = longitud + 3;
                                        if (palabra.startsWith("[")) {
                                            titulo = palabra.substring(17, palabra.length());
                                            arrayMovies.add(titulo);
                                            adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, arrayMovies);
                                            lv.setAdapter(adapter);
                                        }else if (!palabra.startsWith("[")) {
                                            titulo = palabra.substring(16, palabra.length());
                                            arrayMovies.add(titulo);
                                            adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, arrayMovies);
                                            lv.setAdapter(adapter);
                                        }
                                    }
                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        tr.start();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String titulo = (String)(lv.getItemAtPosition(position));
                Intent intent = new Intent(SearchActivity.this, MovieActivity.class);
                intent.putExtra("Titulo", titulo);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

        btInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });
    }

    public int objJSON(String respuesta) {
        int res=0;
        try{
            JSONArray json=new JSONArray(respuesta);
            if(json.length()>0){
                res=1;
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String recogerTitulos () throws IOException {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/ListaTitulos.php");
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            resul=new StringBuilder();
            if (respuesta==HttpURLConnection.HTTP_OK){
                InputStream in=new BufferedInputStream(conection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                while((linea=reader.readLine())!=null){
                    resul.append(linea);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resul.toString();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem item=menu.findItem(R.id.menuSearch);
        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                if(lv.getChildCount()<1){
                    Toast.makeText(SearchActivity.this, "La pelicula que buscas no está en la base, pero puedes insertarla", Toast.LENGTH_LONG).show();
                    btInsertar.setVisibility(View.VISIBLE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                adapter.getFilter().filter(newText);
                if(lv.getChildCount()>0){
                    btInsertar.setVisibility(View.INVISIBLE);
                }
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

}
