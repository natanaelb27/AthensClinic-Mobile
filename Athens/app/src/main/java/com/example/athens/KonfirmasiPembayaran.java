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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KonfirmasiPembayaran extends AppCompatActivity {
    ListView lv;
    int itungHargaObat = 0, hargaObat = 0, getHargaJenistv, selectedCaraPembayaran, totalharga = 0, getHargaObattv;
    String appID, resepsionisID, totalHargaSemuaObat, get_namaP, get_namaD, get_tgl, get_waktu, get_jenis, get_hargaJenis, totalhargaText;
    TextView totalHargaObattv, idAtv, tgltv, waktutv, namaPtv, namaDtv, jenistv, hargaJtv, totalPembayaran;
    RadioGroup rg;
    RadioButton rb;
    Button back, confirm;
    ArrayList<HashMap<String, String>> list_obat;
    String url_get_appointment_data = "http://10.0.2.2/athensmobileproject/getAppointmentData.php";
    String url_get_obat_yang_dibeli = "http://10.0.2.2/athensmobileproject/getObatYangDibeli.php";
    String url_insert_pembayaran = "http://10.0.2.2/athensmobileproject/insertPembayaran.php";
    String url_update_appointment = "http://10.0.2.2/athensmobileproject/updateStatusAppointmentDone.php";
    String url_update_obat = "http://10.0.2.2/athensmobileproject/updateStatusObatDone.php";
//    String url_get_appointment_data = "https://athensclinic.000webhostapp.com/getAppointmentData.php";
//    String url_get_obat_yang_dibeli = "https://athensclinic.000webhostapp.com/getObatYangDibeli.php";
//    String url_insert_pembayaran = "https://athensclinic.000webhostapp.com/insertPembayaran.php";
//    String url_update_appointment = "https://athensclinic.000webhostapp.com/updateStatusAppointmentDone.php";
//    String url_update_obat = "https://athensclinic.000webhostapp.com/updateStatusObatDone.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_NAMA_PASIEN = "namaPasien";
    private static final String TAG_NAMA_DOKTER = "namaDokter";
    private static final String TAG_TGL = "tglAppointment";
    private static final String TAG_JENIS = "jenisAppointment";
    private static final String TAG_WAKTU = "waktuAppointment";
    private static final String TAG_HARGA_APPOINTMENT = "hargaAppointment";
    private static final String TAG_NAMA_OBAT = "namaObat";
    private static final String TAG_JUMLAH_OBAT = "jumlah";
    private static final String TAG_HARGA_OBAT_SATUAN = "hargaobatsatuan";
    private static final String TAG_TOTAL_HARGA_OBAT = "totalhargaobat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembayaran);
        getSupportActionBar().setTitle("Konfirmasi Pembayaran");
        Intent intent = getIntent();
        appID = intent.getStringExtra("appointmentID");
        resepsionisID = intent.getStringExtra("resepsionisID");
        lv = findViewById(R.id.listObatYangDibeli);
        rg = (RadioGroup)findViewById(R.id.rgCaraPembayaran);
        list_obat = new ArrayList<>();
        back = (Button)findViewById(R.id.backPembayaranConfirm);
        confirm = (Button)findViewById(R.id.confirmPembayaran);
        totalHargaObattv = (TextView)findViewById(R.id.totalHargaObatConfirm);
        idAtv = (TextView)findViewById(R.id.idAppointmentConfirm);
        idAtv.setText(appID);
        tgltv = (TextView)findViewById(R.id.tglConfirm);
        waktutv = (TextView)findViewById(R.id.waktuConfirm);
        namaPtv = (TextView)findViewById(R.id.namaPConfirm);
        namaDtv = (TextView)findViewById(R.id.namaDConfirm);
        jenistv = (TextView)findViewById(R.id.jenisConfirm);
        hargaJtv = (TextView)findViewById(R.id.hargaJenisAppointmentConfirm);
        totalPembayaran = (TextView)findViewById(R.id.totalPembayaran);

        getAppointmentData();
        getObatList();
        calculateHargaTotal();
        confirmClick();
        backClick();

    }

    public void confirmClick(){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId() == -1){
                    Toast.makeText(KonfirmasiPembayaran.this, "Silahkan pilih cara pembayarannya terlebih dahulu", Toast.LENGTH_SHORT).show();

                } else {
                    selectedCaraPembayaran = rg.getCheckedRadioButtonId();
                    rb = (RadioButton)findViewById(selectedCaraPembayaran);
                    insertPembayaran();
                }
            }
        });

    }

    public void insertPembayaran(){
        if(totalPembayaran.getText().toString().matches("")){
            Toast.makeText(KonfirmasiPembayaran.this, "Silahkan Restart", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue queue = Volley.newRequestQueue(KonfirmasiPembayaran.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_pembayaran, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        int sukses = jObj.getInt("success");
                        if (sukses == 1) {
                            Toast.makeText(KonfirmasiPembayaran.this, "Data Pembayaran berhasil disimpan", Toast.LENGTH_SHORT).show();
                            updateAppointment();
                            updateResepObat();

                            Intent intent = new Intent(getApplicationContext(), ProsesPembayaran.class);
                            intent.putExtra("resepsionisID", resepsionisID);
                            startActivity(intent);
                        } else {
                            Toast.makeText(KonfirmasiPembayaran.this, "Data Pembayaran gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.getMessage());
                    Toast.makeText(KonfirmasiPembayaran.this, "4silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idApp", appID);
                    params.put("totalPembayaran", totalPembayaran.getText().toString());
                    params.put("idResepsionis", resepsionisID);
                    params.put("caraPembayaran", rb.getText().toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.getCache().clear();
            queue.add(stringRequest);


        }


    }

    public void updateAppointment(){
        RequestQueue queue = Volley.newRequestQueue(KonfirmasiPembayaran.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update_appointment, new Response.Listener<String>() {
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
                Toast.makeText(KonfirmasiPembayaran.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAppointment", appID);
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

    public void updateResepObat(){
        RequestQueue queue = Volley.newRequestQueue(KonfirmasiPembayaran.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update_obat, new Response.Listener<String>() {
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
                Toast.makeText(KonfirmasiPembayaran.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAppointment", appID);
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


    public void backClick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProsesPembayaran.class);
                i.putExtra("resepsionisID", resepsionisID);
                startActivity(i);
            }
        });

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

    public void getAppointmentData(){
        RequestQueue queue = Volley.newRequestQueue(KonfirmasiPembayaran.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_appointment_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    JSONObject a = member.getJSONObject(0);
                    get_namaP = a.getString(TAG_NAMA_PASIEN);
                    get_namaD = a.getString(TAG_NAMA_DOKTER);
                    get_tgl = a.getString(TAG_TGL);
                    get_waktu = a.getString(TAG_WAKTU);
                    get_jenis = a.getString(TAG_JENIS);
                    get_hargaJenis = a.getString(TAG_HARGA_APPOINTMENT);
                    tgltv.setText(get_tgl);
                    waktutv.setText(get_waktu);
                    namaPtv.setText(get_namaP);
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
                Toast.makeText(KonfirmasiPembayaran.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
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
    public void getObatList(){
        RequestQueue queue = Volley.newRequestQueue(KonfirmasiPembayaran.this);
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
                    ListAdapter adapter = new SimpleAdapter(KonfirmasiPembayaran.this, list_obat, R.layout.list_obat_yang_dibeli, from, to);
                    lv.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(KonfirmasiPembayaran.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
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
}
