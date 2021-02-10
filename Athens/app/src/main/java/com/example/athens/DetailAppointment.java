package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailAppointment extends AppCompatActivity {
    ListView lv;
    int itungHargaObat = 0, hargaObat = 0, getHargaJenistv, totalharga = 0, getHargaObattv;
    String totalHargaSemuaObat, appID, petunjuk, get_namaD, get_tgl, get_waktu, get_jenis, get_hargaJenis, totalhargaText, status, get_total_pembayaran, UserID;
    ArrayList<HashMap<String, String>> list_obat;
    Button back;
    TextView totalHargaObattv, petunjuktv, tgltv, waktutv, namaDtv, jenistv, hargaJtv, totalPembayaran;
    String url_get_obat_yang_dibeli = "http://10.0.2.2/athensmobileproject/getObatYangDibeli.php";
    String url_get_obat_yang_dibeli_done = "http://10.0.2.2/athensmobileproject/getObatYangDibeliDone.php";
    String url_get_appointment_data = "http://10.0.2.2/athensmobileproject/getAppointmentData.php";
    String url_get_appointment_data_complete = "http://10.0.2.2/athensmobileproject/getAppointmentDataComplete.php";
//    String url_get_obat_yang_dibeli = " https://athensclinic.000webhostapp.com/getObatYangDibeli.php";
//    String url_get_obat_yang_dibeli_done = " https://athensclinic.000webhostapp.com/getObatYangDibeliDone.php";
//    String url_get_appointment_data = " https://athensclinic.000webhostapp.com/getAppointmentData.php";
//    String url_get_appointment_data_complete = " https://athensclinic.000webhostapp.com/getAppointmentDataComplete.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_NAMA_OBAT = "namaObat";
    private static final String TAG_JUMLAH_OBAT = "jumlah";
    private static final String TAG_HARGA_OBAT_SATUAN = "hargaobatsatuan";
    private static final String TAG_TOTAL_HARGA_OBAT = "totalhargaobat";
    private static final String TAG_NAMA_DOKTER = "namaDokter";
    private static final String TAG_TGL = "tglAppointment";
    private static final String TAG_JENIS = "jenisAppointment";
    private static final String TAG_WAKTU = "waktuAppointment";
    private static final String TAG_HARGA_APPOINTMENT = "hargaAppointment";
    private static final String TAG_TOTAL_PEMBAYARAN = "totalPembayaran";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_appointment);
        getSupportActionBar().setTitle("Detail Appointment");
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        appID = intent.getStringExtra("appointmentID");
        petunjuk = intent.getStringExtra("petunjuk");
        status = intent.getStringExtra("status");
        lv = findViewById(R.id.listObatYangDibeliPasien);
        tgltv = (TextView)findViewById(R.id.tglDetail);
        waktutv = (TextView)findViewById(R.id.waktuDetail);
        namaDtv = (TextView)findViewById(R.id.namaDDetail);
        jenistv = (TextView)findViewById(R.id.jenisDetail);
        hargaJtv = (TextView)findViewById(R.id.hargaJenisDetail);
        list_obat = new ArrayList<>();
        totalHargaObattv = (TextView)findViewById(R.id.totalHargaObatDetailAppointment);
        totalPembayaran = (TextView)findViewById(R.id.totalHargaDetailAppointment);
        petunjuktv = (TextView)findViewById(R.id.petunjukUntukActive);
        petunjuktv.setText(petunjuk);
        back = (Button)findViewById(R.id.backDetailAppointment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TotalPembayaran.class);
                i.putExtra("UserID", UserID);
                startActivity(i);
            }
        });

        if(status.matches("active")){
            getAppointmentData();
            getObatListActive();
            calculateHargaTotal();
        } else if(status.matches("done")){
            getAppointmentDataAll();
            getObatListDone();
        } else {

        }
    }

    public void getAppointmentDataAll(){
        RequestQueue queue = Volley.newRequestQueue(DetailAppointment.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_appointment_data_complete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    JSONObject a = member.getJSONObject(0);
                    get_namaD = a.getString(TAG_NAMA_DOKTER);
                    get_tgl = a.getString(TAG_TGL);
                    get_waktu = a.getString(TAG_WAKTU);
                    get_jenis = a.getString(TAG_JENIS);
                    get_hargaJenis = a.getString(TAG_HARGA_APPOINTMENT);
                    get_total_pembayaran = a.getString(TAG_TOTAL_PEMBAYARAN);
                    tgltv.setText(get_tgl);
                    waktutv.setText(get_waktu);
                    namaDtv.setText(get_namaD);
                    jenistv.setText(get_jenis);
                    hargaJtv.setText(get_hargaJenis);
                    totalPembayaran.setText(get_total_pembayaran);
                }
                catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(DetailAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("appID", appID);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    public void getObatListDone(){
        RequestQueue queue = Volley.newRequestQueue(DetailAppointment.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_obat_yang_dibeli_done, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String namaObat = a.getString(TAG_NAMA_OBAT);
                        String jumlah = a.getString(TAG_JUMLAH_OBAT);
                        String hargaObatSatuan = a.getString(TAG_HARGA_OBAT_SATUAN);
                        String totalHargaObat = a.getString(TAG_TOTAL_HARGA_OBAT);
                        try {
                            hargaObat = Integer.parseInt(totalHargaObat);
                        } catch(NumberFormatException nfe) {

                        }
                        itungHargaObat = itungHargaObat + hargaObat;

                        HashMap<String, String> map = new HashMap<>();
                        map.put("namaObat", namaObat);
                        map.put("jumlah", jumlah);
                        map.put("hargaObatSatuan", hargaObatSatuan);
                        map.put("totalHargaObat", totalHargaObat);
                        list_obat.add(map);
                    }
                    totalHargaSemuaObat = String.valueOf(itungHargaObat);
                    totalHargaObattv.setText(totalHargaSemuaObat);

                    String[] from = {"namaObat", "jumlah", "hargaObatSatuan", "totalHargaObat"};
                    int[] to = {R.id.namaObatDibeli, R.id.jumlahDibeli, R.id.hargaDibeli, R.id.totalDibeli};
                    ListAdapter adapter = new SimpleAdapter(DetailAppointment.this, list_obat, R.layout.list_obat_yang_dibeli, from, to);
                    lv.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(DetailAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAppointment", appID);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void getObatListActive(){
        RequestQueue queue = Volley.newRequestQueue(DetailAppointment.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_obat_yang_dibeli, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String namaObat = a.getString(TAG_NAMA_OBAT);
                        String jumlah = a.getString(TAG_JUMLAH_OBAT);
                        String hargaObatSatuan = a.getString(TAG_HARGA_OBAT_SATUAN);
                        String totalHargaObat = a.getString(TAG_TOTAL_HARGA_OBAT);
                        try {
                            hargaObat = Integer.parseInt(totalHargaObat);
                        } catch(NumberFormatException nfe) {

                        }
                        itungHargaObat = itungHargaObat + hargaObat;

                        HashMap<String, String> map = new HashMap<>();
                        map.put("namaObat", namaObat);
                        map.put("jumlah", jumlah);
                        map.put("hargaObatSatuan", hargaObatSatuan);
                        map.put("totalHargaObat", totalHargaObat);
                        list_obat.add(map);
                    }
                    totalHargaSemuaObat = String.valueOf(itungHargaObat);
                    totalHargaObattv.setText(totalHargaSemuaObat);

                    String[] from = {"namaObat", "jumlah", "hargaObatSatuan", "totalHargaObat"};
                    int[] to = {R.id.namaObatDibeli, R.id.jumlahDibeli, R.id.hargaDibeli, R.id.totalDibeli};
                    ListAdapter adapter = new SimpleAdapter(DetailAppointment.this, list_obat, R.layout.list_obat_yang_dibeli, from, to);
                    lv.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(DetailAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAppointment", appID);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void getAppointmentData(){
        RequestQueue queue = Volley.newRequestQueue(DetailAppointment.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_appointment_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    JSONObject a = member.getJSONObject(0);
                    get_namaD = a.getString(TAG_NAMA_DOKTER);
                    get_tgl = a.getString(TAG_TGL);
                    get_waktu = a.getString(TAG_WAKTU);
                    get_jenis = a.getString(TAG_JENIS);
                    get_hargaJenis = a.getString(TAG_HARGA_APPOINTMENT);
                    tgltv.setText(get_tgl);
                    waktutv.setText(get_waktu);
                    namaDtv.setText(get_namaD);
                    jenistv.setText(get_jenis);
                    hargaJtv.setText(get_hargaJenis);
                }
                catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(DetailAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("appID", appID);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void calculateHargaTotal(){
        hargaJtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    getHargaJenistv = Integer.parseInt(hargaJtv.getText().toString());

                } catch(NumberFormatException nfe) {

                }
                totalharga = getHargaJenistv + totalharga;
                totalhargaText = String.valueOf(totalharga);
                totalPembayaran.setText(totalhargaText);
            }
        });
        totalHargaObattv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    getHargaObattv = Integer.parseInt(totalHargaObattv.getText().toString());

                } catch(NumberFormatException nfe) {

                }
                totalharga = getHargaObattv + totalharga;
                totalhargaText = String.valueOf(totalharga);
                totalPembayaran.setText(totalhargaText);
            }
        });
    }
}
