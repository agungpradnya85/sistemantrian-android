package com.sistemantrianonline.antrianonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ClinicActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    //Declaring an spinner
    private Spinner spinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> clinics;

    //JSON Array
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);

        //Initializing the ArrayList
        clinics = new ArrayList<String>();

        //Initializing Spinner
        spinner = (Spinner) findViewById(R.id.spinner);

        //Adding an Item Selected Listener to our Spinner
        spinner.setOnClickListener((View.OnClickListener) this);

        //This methdd will fetch the data from URL
        getData();

        final Button bClinic = (Button) findViewById(R.id.bClinic);
        final TextView resultTerakhir = (TextView) findViewById(R.id.tvAntrianTerakhir);
        final TextView resultTerdaftar = (TextView) findViewById(R.id.tvAntrianTerdaftar);


        //TO Saguna : Code yang sebelumnya
//        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.clinics_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2/advanced/backend/web/index.php/administration/antrian/rest-index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("lastQueue", jsonObject.getString("latest_queue"));
                            resultTerakhir.setText(jsonObject.getString("latest_queue"));
                            resultTerdaftar.setText(jsonObject.getString("current_queue"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.toString());
                    }
                }){


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


        bClinic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent clinicIntent = new Intent(ClinicActivity.this, MainActivity.class);
                clinicIntent.putExtra("SPINNER", 1);
                ClinicActivity.this.startActivity(clinicIntent);
            }
        });
    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("http://10.0.2.2/advanced/backend/web/index.php/administration/antrian/rest-index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;

                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the arrray of JSON String to our JSON Array
                            result = j.getJSONArray("result");

                            //Calling method getClinics to get the clinics from the JSON Array
                            getClinics(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getClinics(JSONArray result) {
        //Traversing through all the items in json array
        for(int i=0; i<result.length();i++){
            try{
                //Getting json object
                JSONObject json = result.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(ClinicActivity.this,android.R.layout.simple_spinner_dropdown_item,clinics));
    }

}
