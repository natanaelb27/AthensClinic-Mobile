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

public class DokterLogin extends AppCompatActivity {
    Button login;
    TextInputLayout usernameDokter, passwordDokter;
    String url_login_dokter = "http://10.0.2.2/athensmobileproject/loginDokter.php";
//    String url_login_dokter = "https://athensclinic.000webhostapp.com/loginDokter.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_UNAME = "username";
    private static final String TAG_PASSWORD = "password";
    String unameInput, passInput, getID, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_login);
        getSupportActionBar().setTitle("Login Dokter");
        login = (Button)findViewById(R.id.dokter_loginbtn);
        usernameDokter = (TextInputLayout) findViewById(R.id.username_dokter);
        passwordDokter = (TextInputLayout)findViewById(R.id.password_dokter);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameDokter.setErrorEnabled(false);
                passwordDokter.setErrorEnabled(false);
                unameInput = usernameDokter.getEditText().getText().toString();
                passInput = passwordDokter.getEditText().getText().toString();
                if(unameInput.length() > 0 && passInput.length() > 0){
                    RequestQueue queue = Volley.newRequestQueue(DokterLogin.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login_dokter, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONArray member = jObj.getJSONArray(TAG_DATA);
                                JSONObject a = member.getJSONObject(0);

                                getID = a.getString(TAG_ID);
                                getPassword = a.getString(TAG_PASSWORD);

                                if(passInput.equals(getPassword)){
                                    Intent intent = new Intent(getApplication(), HomeDokter.class);
                                    intent.putExtra("dokterID", getID);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(DokterLogin.this, "Nomor telpon atau password salah!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DokterLogin.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
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
                        passwordDokter.setError("Silahkan isi password Anda");
                        usernameDokter.setError("Silahkan isi nomor telpon Anda");

                    } else if(unameInput.isEmpty()){
                        usernameDokter.setError("Silahkan isi nomor telpon Anda");
                    } else if(passInput.isEmpty()){
                        passwordDokter.setError("Silahkan isi password Anda");


                    }

                }
            }
        });
    }

}
