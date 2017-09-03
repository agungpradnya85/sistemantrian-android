package com.sistemantrianonline.antrianonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // id antrian di database;
    String id_antrian;
    SharedPreferences sharedPreferences;
    String id_klinik;
    private TextView result, tvNamaKlinik, tvTimeExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_klinik = getIntent().getStringExtra("CLINIC_ID");
        result = (TextView) findViewById(R.id.tvNoAntrian);
        tvNamaKlinik = (TextView) findViewById(R.id.tvNamaKlinik);
        tvTimeExam = (TextView) findViewById(R.id.tvTimeExam);
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);

        sharedPreferences = getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // on click button cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id_antrian.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/advanced/api/web/index.php/v1/reservations/cancel-reservation?"
                            + UserManager.KEY_TOKEN + "=" + sharedPreferences.getString(UserManager.KEY_TOKEN, ""),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        String message;
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("message").equals("success")) {
                                            message = "Reservasi Telah dibatalkan";
                                        } else {
                                            message = jsonObject.getString("message");
                                        }

                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, dashboard2.class);
                                        MainActivity.this.startActivity(intent);
                                        finish();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id_klinik", id_klinik);
                            params.put("id", id_antrian);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);
                }
            }
        });

        /*
         * fetch queue from remote database (json file)
         */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/advanced/api/web/index.php/v1/reservations/create?"
                + UserManager.KEY_TOKEN + "=" + sharedPreferences.getString(UserManager.KEY_TOKEN, ""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(MainActivity.this,response.getJSONArray("no_antrian"),Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String antrianText = jsonObject.getString("no_antrian");
                            result.setText(antrianText.toString());
                            tvNamaKlinik.setText(jsonObject.getString("nama_klinik").toString());
                            tvTimeExam.setText(jsonObject.getString("time_exam").toString());
                            id_antrian = jsonObject.getString("id_antrian");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_klinik", id_klinik);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}
