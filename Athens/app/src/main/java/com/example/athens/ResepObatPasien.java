package com.example.athens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResepObatPasien extends AppCompatActivity {
    String dokterID, getIdAppointment, getNamaPasien, getNoTelp, selectedAppointment, selected_obat, getObat, getIdObat;
    Spinner spinAppointment, spinObat;
    List<String> appointments;
    List<String> pilihanObat;
    Button add, confirm, back;
    TextView quantityObat;
    int indexObat, indexAppointment;
    private static final String TAG_DATA = "data";
    private static final String TAG_ID_APPOINTMENT = "idAppointment";
    private static final String TAG_NAMA = "namaPasien";
    private static final String TAG_TELPON = "notelp";
    private static final String TAG_OBAT ="namaObat";
    private static final String TAG_ID_OBAT ="idObat";
    String url_get_appointment_hari_ini = "http://10.0.2.2/athensmobileproject/getAppointmentHariIni.php";
    String url_get_obat = "http://10.0.2.2/athensmobileproject/getObat.php";
    String url_insert_resep_obat = "http://10.0.2.2/athensmobileproject/insertResepObat.php";
    String url_get_obat_id = "http://10.0.2.2/athensmobileproject/getObatId.php";
    String url_appointment_active = "http://10.0.2.2/athensmobileproject/updateStatusAppointmentActive.php";

//    String url_get_appointment_hari_ini = "https://athensclinic.000webhostapp.com/getAppointmentHariIni.php";
//    String url_get_obat = "https://athensclinic.000webhostapp.com/getObat.php";
//    String url_insert_resep_obat = "https://athensclinic.000webhostapp.com/insertResepObat.php";
//    String url_get_obat_id = "https://athensclinic.000webhostapp.com/getObatId.php";
//    String url_appointment_active = "https://athensclinic.000webhostapp.com/updateStatusAppointmentActive.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep_obat_pasien);
        getSupportActionBar().setTitle("Resep Obat Pasien");
        Intent intent = getIntent();
        dokterID = intent.getStringExtra("dokterID");
        spinAppointment = (Spinner)findViewById(R.id.spinAppointmentDokter);
        spinObat = (Spinner)findViewById(R.id.spinnerObat);
        appointments = new ArrayList<String>();
        pilihanObat = new ArrayList<String>();
        pilihanObat.add("- Pilih Obat -");
        appointments.add("- Pilih Appointment -");
        confirm = (Button)findViewById(R.id.confirmAppointment);
        add = (Button)findViewById(R.id.addObatBtn);
        back = (Button)findViewById(R.id.backResep);
        quantityObat = (TextView)findViewById(R.id.quantityObat);
        backClick();
        spinnerAppointment();
        spinnerObat();
        spinnerAppointmentSelected();
        spinnerObatSelected();
        addClicked();
        confirmClicked();

        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pilihanObat);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinObat.setAdapter(adapter2);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, appointments);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAppointment.setAdapter(adapter);
    }

    public void backClick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeDokter.class);
                intent.putExtra("dokterID", dokterID);
                startActivity(intent);
            }
        });


    }

    public void spinnerAppointmentSelected(){
        spinAppointment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexAppointment = spinAppointment.getSelectedItemPosition();
                if(indexAppointment == 0){

                } else {
                    selectedAppointment = spinAppointment.getSelectedItem().toString();
                    String[] seperate = selectedAppointment.split("-");
                    getIdAppointment = seperate[0];
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void spinnerObatSelected(){
        spinObat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexObat = spinObat.getSelectedItemPosition();
                if(indexObat == 0){

                } else {
                    selected_obat = spinObat.getSelectedItem().toString();
                    RequestQueue queue3 = Volley.newRequestQueue(ResepObatPasien.this);
                    StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url_get_obat_id, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                JSONArray member = jObj.getJSONArray(TAG_DATA);
                                JSONObject a = member.getJSONObject(0);
                                getIdObat = a.getString(TAG_ID_OBAT);

                            }
                            catch (Exception ex) {
                                Log.e("Error", ex.toString());

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error", error.getMessage());
                            Toast.makeText(ResepObatPasien.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {

                            Map<String, String> params = new HashMap<>();
                            params.put("namaObat", selected_obat);
                            return params;
                        }
                    };
                    queue3.add(stringRequest3);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void spinnerAppointment(){
        RequestQueue queue = Volley.newRequestQueue(ResepObatPasien.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_appointment_hari_ini, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    for (int i=0; i < member.length();i++) {
                        JSONObject a = member.getJSONObject(i);
                        getIdAppointment = a.getString(TAG_ID_APPOINTMENT);
                        getNamaPasien = a.getString(TAG_NAMA);
                        getNoTelp = a.getString(TAG_TELPON);
                        appointments.add(getIdAppointment + "-"+ getNamaPasien + " - " + getNoTelp);
                    }

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(ResepObatPasien.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idDokter", dokterID);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void spinnerObat(){
        RequestQueue queue2 = Volley.newRequestQueue(ResepObatPasien.this);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url_get_obat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    for (int i=0; i < member.length();i++) {
                        JSONObject a = member.getJSONObject(i);
                        getObat = a.getString(TAG_OBAT);
                        pilihanObat.add(getObat);
                    }

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(ResepObatPasien.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });
        queue2.add(stringRequest2);


    }

    public void addClicked(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexObat = spinObat.getSelectedItemPosition();
                indexAppointment = spinAppointment.getSelectedItemPosition();
                if(indexAppointment == 0){
                    Toast.makeText(ResepObatPasien.this, "silahkan pilih appointmentnya terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    if(indexObat == 0 | quantityObat.getText().toString().matches("")){
                        Toast.makeText(ResepObatPasien.this, "silahkan pilih obat dan isi jumlahnya terlebih dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        RequestQueue queue3 = Volley.newRequestQueue(ResepObatPasien.this);
                        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url_insert_resep_obat, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jObj = new JSONObject(response);
                                    int sukses = jObj.getInt("success");
                                    if (sukses == 1) {
                                        Toast.makeText(ResepObatPasien.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ResepObatPasien.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    Log.e("Error", ex.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error", error.getMessage());
                                Toast.makeText(ResepObatPasien.this, "4silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("idAppointment", getIdAppointment);
                                params.put("idObat", getIdObat);
                                params.put("qObat", quantityObat.getText().toString());
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
                        spinObat.setSelection(0);
                        quantityObat.setText("");
                    }
                }


            }
        });
    }

    public void confirmClicked(){
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexObat = spinObat.getSelectedItemPosition();
                indexAppointment = spinAppointment.getSelectedItemPosition();
                if(indexAppointment == 0){
                    Toast.makeText(ResepObatPasien.this, "silahkan pilih appointmentnya terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResepObatPasien.this);
                    builder.setTitle("Konfirmasi Appointment");
                    builder.setMessage("Apakah Anda yakin ingin mengkonfirmasi appointment ini? Dengan dikonfirmasinya appointment ini, Anda tidak dapat bisa menambahkan resep obat lagi!");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RequestQueue queue4 = Volley.newRequestQueue(ResepObatPasien.this);
                                    StringRequest stringRequest4 = new StringRequest(Request.Method.POST, url_appointment_active, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jObj = new JSONObject(response);
                                                int sukses = jObj.getInt("success");
                                                if (sukses == 1) {
                                                    Toast.makeText(ResepObatPasien.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), HomeDokter.class);
                                                    intent.putExtra("dokterID", dokterID);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(ResepObatPasien.this, "Data gagal diupdate", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception ex) {
                                                Log.e("Error", ex.toString());
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("Error", error.getMessage());
                                            Toast.makeText(ResepObatPasien.this, "4silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("idAppointment", getIdAppointment);
                                            return params;
                                        }

                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("Content-Type", "application/x-www-form-urlencoded");
                                            return params;
                                        }
                                    };
                                    queue4.getCache().clear();
                                    queue4.add(stringRequest4);


                                }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();


                }

            }
        });
    }
}
