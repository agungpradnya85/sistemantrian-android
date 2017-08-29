package com.sistemantrianonline.antrianonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LogoutActivity extends AppCompatActivity {

  //  @Override
  //  protected void onCreate(Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_logout);

       // final TextView bLogout =(TextView) findViewById(R.id.bLogout);

       // bLogout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
            }
      //  });
   // }
}
