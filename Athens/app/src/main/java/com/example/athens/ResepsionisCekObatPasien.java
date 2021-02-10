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

public class ResepsionisCekObatPasien extends AppCompatActivity {
    ListView lv;
    String get_id, resepsionisID;
    ArrayList<HashMap<String, String>> list_obat;
    String url_hapus_resep_obat = "http://10.0.2.2/athensmobileproject/hapusResepObatPasien.php";
    String url_get_resep_obat = "http://10.0.2.2/athensmobileproject/getResepObatByResepsionis.php";
//    String url_hapus_resep_obat = "https://athensclinic.000webhostapp.com/hapusResepObatPasien.php";
//    String url_get_resep_obat = "https://athensclinic.000webhostapp.com/getResepObatByResepsionis.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_PASIEN = "Pasien";
    private static final String TAG_ID_RESEP = "idResep";
    private static final String TAG_TGL = "tglAppointment";
    private static final String TAG_NAMA_OBAT = "namaObat";
    private static final String TAG_JUMLAH = "jumlah";
    private static final String TAG_ID_APPOINTMENT = "idAppointment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resepsionis_cek_obat_pasien);
        getSupportActionBar().setTitle("Cek Obat Pasien");
        Intent intent = getIntent();
        resepsionisID = intent.getStringExtra("resepsionisID");
        list_obat = new ArrayList<>();
        Button back = (Button)findViewById(R.id.backResepsionisObat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeResepsionis.class);
                i.putExtra("resepsionisID", resepsionisID);
                startActivity(i);
            }
        });
        lv = findViewById(R.id.listViewObatR);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                get_id = ((TextView) view.findViewById(R.id.idResepObatROL)).getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(ResepsionisCekObatPasien.this);
                builder.setTitle("Konfirmasi Hapus");
                builder.setMessage("Apakah Anda yakin ingin menghapus appointment ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(ResepsionisCekObatPasien.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_hapus_resep_obat, new Response.Listener<String>() {
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
                                Toast.makeText(ResepsionisCekObatPasien.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
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
                                params.put("Content-Type", "application/x-www-form-urlencoded" );
                                return params;
                            }
                        };
                        queue.getCache().clear();
                        queue.add(stringRequest);
                        Intent i = new Intent(getApplicationContext(), ResepsionisCekObatPasien.class);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(ResepsionisCekObatPasien.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_resep_obat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String idResepObat = a.getString(TAG_ID_RESEP);
                        String idAppointment = a.getString(TAG_ID_APPOINTMENT);
                        String tgl = a.getString(TAG_TGL);
                        String pasien = a.getString(TAG_PASIEN);
                        String namaObat = a.getString(TAG_NAMA_OBAT);
                        String jumlah = a.getString(TAG_JUMLAH);
                        SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = stringToDate.parse(tgl);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        tgl = dateFormat.format(date);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("idResepObat", idResepObat);
                        map.put("idAppointment", idAppointment);
                        map.put("tgl", tgl);
                        map.put("pasien", pasien);
                        map.put("namaObat", namaObat);
                        map.put("jumlah", jumlah);
                        list_obat.add(map);
                    }

                    String[] from = {"idResepObat", "idAppointment", "tgl", "pasien", "namaObat", "jumlah"};
                    int[] to = {R.id.idResepObatROL, R.id.idAppointmentROL, R.id.tanggalAppointmentROL, R.id.namaPasienROL, R.id.namaObatROL, R.id.jumlahROL};
                    ListAdapter adapter = new SimpleAdapter(ResepsionisCekObatPasien.this, list_obat, R.layout.list_resepsionis_cek_obat_pasien, from, to);
                    lv.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(ResepsionisCekObatPasien.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);

    }
}
