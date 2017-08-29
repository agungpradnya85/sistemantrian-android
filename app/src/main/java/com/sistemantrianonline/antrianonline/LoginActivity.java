package com.sistemantrianonline.antrianonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends BaseApplication {

    EditText etUsername, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
       // final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

//        registerLink.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
//                LoginActivity.this.startActivity(registerIntent);
//            }
//        });

        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                LoginActivity.this.startActivity(intent);*/

                login();
                Log.e("PP", "ooo");
            }
        });
    }


    private void login(){
        /* https://www.simplifiedcoding.net/android-login-example-using-php-mysql-and-volley/ */
        //Getting values from edit texts
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2/advanced/api/web/index.php/v1/authentications/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE LOGIN", response);
                        JSONObject jsonObject, credentialObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equalsIgnoreCase("success")) {
                                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                credentialObject = new JSONObject(jsonObject.getString("data"));
                                editor.putString(UserManager.KEY_TOKEN, credentialObject.getString("token"));
                                editor.putBoolean(UserManager.LOGGEDIN_SHARED_PREF, true);
                                //Saving values to editor
                                editor.commit();

                                //Starting profile activity
                                Intent intent = new Intent(LoginActivity.this, dashboard2.class);
                                startActivity(intent);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //If we are getting success from server
                        /* if(response.equalsIgnoreCase(UserManager.LOGIN_SUCCESS)){
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(UserManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(UserManager.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(UserManager.EMAIL_SHARED_PREF, email);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        } */
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Log.e("ERR", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(UserManager.KEY_USERNAME, username);
                params.put(UserManager.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
