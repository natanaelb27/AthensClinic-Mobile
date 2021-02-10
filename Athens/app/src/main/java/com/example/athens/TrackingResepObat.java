package com.example.athens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TrackingResepObat extends AppCompatActivity {
    String UserID, get_id;
    Button back;
    ListView lv;
    ArrayList<HashMap<String, String>> list_obat;
    String url_get_resep_obat_pasien = "http://10.0.2.2/athensmobileproject/getResepObatPasien.php";
    String url_konfirmasi_obat = "http://10.0.2.2/athensmobileproject/updateStatusObatActive.php";
    String url_hapus_resep_obat_pasien = "http://10.0.2.2/athensmobileproject/hapusResepObatPasien.php";
//    String url_get_resep_obat_pasien = "https://athensclinic.000webhostapp.com/getResepObatPasien.php";
//    String url_konfirmasi_obat = "https://athensclinic.000webhostapp.com/updateStatusObatActive.php";
//    String url_hapus_resep_obat_pasien = "https://athensclinic.000webhostapp.com/hapusResepObatPasien.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_ID_RESEP = "idResep";
    private static final String TAG_NAMA_OBAT = "namaObat";
    private static final String TAG_JUMLAH_OBAT = "jumlahObat";
    private static final String TAG_HARGA_OBAT = "hargaObat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_resep_obat);
        getSupportActionBar().setTitle("Tracking Resep Obat");
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        list_obat = new ArrayList<>();
        back = (Button)findViewById(R.id.backTrackingResepObat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomePasien.class);
                i.putExtra("UserID", UserID);
                startActivity(i);
            }
        });
        lv = findViewById(R.id.listViewObat);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                get_id = ((TextView) view.findViewById(R.id.idObatL)).getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(TrackingResepObat.this);
                builder.setTitle("Konfirmasi Obat");
                builder.setMessage("Apakah Anda ingin membeli atau menghapus obat ini dari resep obat Anda?");
                builder.setPositiveButton("Beli", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue2 = Volley.newRequestQueue(TrackingResepObat.this);
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url_konfirmasi_obat, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jObj = new JSONObject(response);
                                } catch (Exception ex) {
                                    Log.e("Error", ex.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error", error.getMessage());
                                Toast.makeText(TrackingResepObat.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("id", get_id);
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                return params;
                            }
                        };
                        queue2.getCache().clear();
                        queue2.add(stringRequest2);
                        Intent i = new Intent(getApplicationContext(), TrackingResepObat.class);
                        i.putExtra("UserID", UserID);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue3 = Volley.newRequestQueue(TrackingResepObat.this);
                        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url_hapus_resep_obat_pasien, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jObj = new JSONObject(response);
                                } catch (Exception ex) {
                                    Log.e("Error", ex.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error", error.getMessage());
                                Toast.makeText(TrackingResepObat.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("id", get_id);
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                return params;
                            }
                        };
                        queue3.getCache().clear();
                        queue3.add(stringRequest3);
                        Intent i = new Intent(getApplicationContext(), TrackingResepObat.class);
                        i.putExtra("UserID", UserID);
                        startActivity(i);
                    }
                });
                builder.show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(TrackingResepObat.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_resep_obat_pasien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String idResep = a.getString(TAG_ID_RESEP);
                        String namaObat = a.getString(TAG_NAMA_OBAT);
                        String jumlahObat = a.getString(TAG_JUMLAH_OBAT);
                        String hargaObat = a.getString(TAG_HARGA_OBAT);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", idResep);
                        map.put("nama", namaObat);
                        map.put("jumlah", jumlahObat);
                        map.put("harga", hargaObat);
                        list_obat.add(map);
                    }

                    String[] from = {"id", "nama", "jumlah", "harga"};
                    int[] to = {R.id.idObatL, R.id.namaObatL, R.id.jumlahObatL, R.id.hargaObatL};
                    ListAdapter adapter = new SimpleAdapter(TrackingResepObat.this, list_obat, R.layout.list_obat, from, to);
                    lv.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(TrackingResepObat.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idPasien", UserID);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);

    }


}
