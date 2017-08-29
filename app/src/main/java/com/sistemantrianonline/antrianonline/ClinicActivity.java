package com.sistemantrianonline.antrianonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sistemantrianonline.antrianonline.models.KeyVal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ClinicActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    //Declaring an spinner
    private Spinner spinner;

    //An ArrayList for Spinner Items
    private List<KeyVal> clinics = new ArrayList<>();

    //JSON Array
    private JSONArray result;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        sharedPreferences = getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getData();
        final Button bClinic = (Button) findViewById(R.id.bClinic);
        final TextView resultTerakhir = (TextView) findViewById(R.id.tvAntrianTerakhir);
        final TextView resultTerdaftar = (TextView) findViewById(R.id.tvAntrianTerdaftar);

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
                }) {


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


        bClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinnerID = ((KeyVal) spinner.getSelectedItem()).getId();
                //Log.e("SPIN", spinnerID);
                Intent clinicIntent = new Intent(ClinicActivity.this, MainActivity.class);
                clinicIntent.putExtra("CLINIC_ID", spinnerID);
                ClinicActivity.this.startActivity(clinicIntent);
            }
        });
    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.0.2.2/advanced/api/web/index.php/v1/clinics?"
                + UserManager.KEY_TOKEN + "=" + sharedPreferences.getString(UserManager.KEY_TOKEN, ""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        Log.e("JSON", response);

                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the arrray of JSON String to our JSON Array
                            result = j.getJSONArray("items");

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

        for (int i = 0; i < result.length(); i++) {
            try {
                //Getting json object
                JSONObject json = result.getJSONObject(i);
                clinics.add(new KeyVal(json.getString("id"), json.getString("nama_klinik")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<KeyVal> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, clinics);
        spinner.setAdapter(adapter);

        //Setting adapter to show the items in the spinner
        //spinner.setAdapter(new ArrayAdapter<String>(ClinicActivity.this,android.R.layout.simple_spinner_dropdown_item,clinics));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //String spinnerID = ((KeyVal) spinner.getSelectedItem()).getId();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
