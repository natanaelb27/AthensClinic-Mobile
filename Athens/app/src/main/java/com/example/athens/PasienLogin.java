package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasienLogin extends AppCompatActivity {
    Button login;
    TextInputLayout usernamePasien, passwordPasien;
    String url_login_pasien = "http://10.0.2.2/athensmobileproject/loginPasien.php";
//    String url_login_pasien = "https://athensclinic.000webhostapp.com/loginPasien.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_PASSWORD = "password";
    String unameInput, passInput, getID, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_login);
        getSupportActionBar().setTitle("Login Pasien");
        login = (Button)findViewById(R.id.pasien_loginbtn);
        usernamePasien = (TextInputLayout) findViewById(R.id.username_pasien);
        passwordPasien = (TextInputLayout)findViewById(R.id.password_pasien);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamePasien.setErrorEnabled(false);
                passwordPasien.setErrorEnabled(false);
                unameInput = usernamePasien.getEditText().getText().toString();
                passInput = passwordPasien.getEditText().getText().toString();
                if(unameInput.length() > 0 && passInput.length() > 0){
                    RequestQueue queue = Volley.newRequestQueue(PasienLogin.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login_pasien, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONArray member = jObj.getJSONArray(TAG_DATA);
                                JSONObject a = member.getJSONObject(0);
                                getID = a.getString(TAG_ID);
                                getPassword = a.getString(TAG_PASSWORD);
                                if(passInput.equals(getPassword)){
                                    Intent intent = new Intent(getApplication(), HomePasien.class);
                                    intent.putExtra("UserID", getID);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(PasienLogin.this, "Nomor telpon atau password salah!", Toast.LENGTH_SHORT).show();

                                }
                            }
                            catch (Exception ex) {
                                Log.e("Error", ex.toString());

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error", error.getMessage());
                            Toast.makeText(PasienLogin.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("usernamePhone", unameInput);
                            return params;
                        }
                    };
                    queue.add(stringRequest);


                } else {
                    if(unameInput.isEmpty() & passInput.isEmpty()){
                        passwordPasien.setError("Silahkan isi password Anda");
                        usernamePasien.setError("Silahkan isi nomor telpon Anda");

                    } else if(unameInput.isEmpty()){
                        usernamePasien.setError("Silahkan isi nomor telpon Anda");
                    } else if(passInput.isEmpty()){
                        passwordPasien.setError("Silahkan isi password Anda");


                    }

                }

            }
        });


    }
}
