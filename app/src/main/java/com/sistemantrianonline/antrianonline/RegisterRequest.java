package com.sistemantrianonline.antrianonline;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by ACER on 24/07/2017.
 */

public class RegisterRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://10.0.2.2/Register.php";
    private Map<String, String> params;

    //// TODO: 24/07/2017  
    public  RegisterRequest(String no_ktp, String name, String alamat, String no_hp, String username, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("no_ktp", no_ktp + "");
        params.put("nama", (name));
        params.put("alamat", (alamat));
        params.put("no_hp", no_hp + "");
        params.put("username", (username));
        params.put("password", (password));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
