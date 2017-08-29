package com.sistemantrianonline.antrianonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int id_klinik = 1;
        final int id_user = 1;
        result  = (TextView) findViewById(R.id.tvNoAntrian);
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        final Button bLogout = (Button) findViewById(R.id.bLogout);

       sharedPreferences = getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        bLogout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                SharedPreferences preferences = getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(logoutIntent);

                finish();
            }

        });

        //Toast.makeText(this, "String "+s, Toast.LENGTH_LONG).show();

        // on click button cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!id_antrian.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/advanced/api/web/index.php/v1/reservations/cancel-reservation?"+UserManager.KEY_TOKEN+"="+sharedPreferences.getString(UserManager.KEY_TOKEN, ""),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//Log.e("error",response);
                                    try {
                                        String message;
                                        JSONObject jsonObject = new JSONObject(response);

                                        if(jsonObject.getString("message").equals("success")) {
                                            message ="Reservasi Telah dibatalkan";
                                        } else {
                                            message =jsonObject.getString("message");
                                        }

                                        Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                        MainActivity.this.startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();

                           // params.put("id_user", String.valueOf(id_user));
                            params.put("id_klinik", String.valueOf(id_klinik));
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/advanced/api/web/index.php/v1/reservations/create?"+UserManager.KEY_TOKEN+"="+sharedPreferences.getString(UserManager.KEY_TOKEN, ""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(MainActivity.this,response.getJSONArray("no_antrian"),Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String antrianText = jsonObject.getString("no_antrian");
                            Log.e("clinick", antrianText);
                            result.setText(antrianText.toString());
                            Toast.makeText(MainActivity.this,jsonObject.getString("no_antrian"),Toast.LENGTH_LONG).show();
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
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("id_klinik", String.valueOf(id_klinik));

                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    /*protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        sharedPreferences = getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

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
