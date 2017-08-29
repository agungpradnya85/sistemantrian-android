package com.sistemantrianonline.antrianonline;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.SimpleCursorAdapter;


public class DashboardActivity extends AppCompatActivity  {

    Button btnReservasi;

    static final String[] MENU = new String[] { "RESERVASI", "PROFILE", "PASSWORD"
             };



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar); // get the reference of Toolbar
      //  setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar
//        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_menu,MENU));
//
//        ListView listView = getListView();
//        listView.setTextFilterEnabled(true);
//
//        listView.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // When clicked, show a toast with the TextView text
//                Toast.makeText(getApplicationContext(),
//                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });

       setContentView(R.layout.activity_dashboard);
//        btnReservasi = (Button) findViewById(R.id.btnReservasi);
//
//        btnReservasi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(DashboardActivity.this, ClinicActivity.class);
//                DashboardActivity.this.startActivity(intent);
//            }
//        });
    }

    /*protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        boolean loggedIn = sharedPreferences.getBoolean(UserManager.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
        }
    }*/
}
