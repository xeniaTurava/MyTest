package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

                    //we are on the method when the menu's item is selected
                    //type inside the instructions TODO
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            //setTitle("Explore");//Set the title ActionBar
                            //instance Activity
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            // startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            return true;
                        case R.id.searchItem:
                            // setTitle("Search");
                            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                            //startActivity(new Intent(MainActivity.this, SearchActivity.class));
                            return true;
                        case R.id.formItem:
                            // setTitle("Form");
                            startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            // startActivity(new Intent(MainActivity.this, FormActivity.class));
                            return true;
                        case R.id.notificationItem:
                            // setTitle("Notifications");
                            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            return true;
                        case R.id.profileItem:
                            // setTitle("Profile");
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            return true;
                    }
                    // finish();
                    return false;
                }

            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Search");//Set the title ActionBar

        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
        // Menu menu = BottomNavigationView.getMenu();
        //MenuItem menuItem = menu.getItem(1);
        //menuItem.setChecked(true);

        //Eli writes from here
        //variable searchText statement and IME_Action_Search

        EditText searchText = (EditText) findViewById(R.id.search_text);
        final TextView movieImage = (TextView) findViewById(R.id.movieImage);
        final TextView movieDescription = (TextView) findViewById(R.id.movieDescription);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean found=false;
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    Toast.makeText(SearchActivity.this, "Encontrada pelicula: " + v.getText().toString(), Toast.LENGTH_SHORT).show();
                    InputMethodManager imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    found=true;
                    movieImage.setVisibility(View.VISIBLE);
                    movieDescription.setVisibility(View.VISIBLE);
                } else{
                    Toast.makeText(SearchActivity.this, "La pelicula no está en la base de datos", Toast.LENGTH_SHORT).show();
                }
                return found;
            }
        });

        //code if movie found in the database is pending write to next lesson


    }
}
