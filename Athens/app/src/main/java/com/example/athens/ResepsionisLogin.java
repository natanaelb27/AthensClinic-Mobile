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

public class ResepsionisLogin extends AppCompatActivity {
    Button login;
    TextInputLayout usernameR, passwordR;
    String url_login_resepsionis = "http://10.0.2.2/athensmobileproject/loginResepsionis.php";
//    String url_login_resepsionis = "https://athensclinic.000webhostapp.com/loginResepsionis.php";
    private static final String TAG_DATA = "data";
    private static final String TAG_ID = "id";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_VALIDASI = "validasi";
    String unameInput, passInput, getID, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resepsionis_login);
        getSupportActionBar().setTitle("Login Resepsionis");
        login = (Button)findViewById(R.id.pasien_loginbtn);
        usernameR = (TextInputLayout) findViewById(R.id.username_resepsionis);
        passwordR = (TextInputLayout)findViewById(R.id.password_resepsionis);
        Button login = (Button)findViewById(R.id.resepsionis_loginbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameR.setErrorEnabled(false);
                passwordR.setErrorEnabled(false);
                unameInput = usernameR.getEditText().getText().toString();
                passInput = passwordR.getEditText().getText().toString();
                if(unameInput.length() > 0 && passInput.length() > 0){
                    RequestQueue queue = Volley.newRequestQueue(ResepsionisLogin.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login_resepsionis, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONArray member = jObj.getJSONArray(TAG_DATA);
                                JSONObject a = member.getJSONObject(0);
                                getID = a.getString(TAG_ID);
                                getPassword = a.getString(TAG_PASSWORD);

                                if(passInput.equals(getPassword)){
                                    Intent intent = new Intent(getApplicationContext(), HomeResepsionis.class);
                                    intent.putExtra("resepsionisID", getID);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ResepsionisLogin.this, "Nama atau password salah!", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(ResepsionisLogin.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", unameInput);
                            return params;
                        }
                    };
                    queue.add(stringRequest);


                } else {
                    if(unameInput.isEmpty() & passInput.isEmpty()){
                        passwordR.setError("Silahkan isi password Anda");
                        usernameR.setError("Silahkan isi nama Anda");

                    } else if(unameInput.isEmpty()){
                        usernameR.setError("Silahkan isi nama Anda");
                    } else if(passInput.isEmpty()){
                        passwordR.setError("Silahkan isi password Anda");
                    }
                }
            }
        });
    }
}
