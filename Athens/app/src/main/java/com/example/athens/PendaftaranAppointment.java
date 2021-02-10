package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendaftaranAppointment extends AppCompatActivity {
    int day, month, year;
    int indexDokter, indexJenis;
    ImageButton datebtn;
    Button showWaktu, submit;
    TextView tglAppointment, waktuAppointment;
    Spinner spinDokter, spinJenis;
    private static final String TAG_DATA = "data";
    private static final String TAG_ID_DOKTER = "iddokter";
    private static final String TAG_NAMA = "namadokter";
    private static final String TAG_ID_JENIS = "idjenis";
    private static final String TAG_NAMA_JENIS = "jenisappointment";
    private static final String TAG_HARGA = "harga";
    private static final String TAG_WAKTU = "waktuMulai";
    String getIdDokter, getNamaDokter, UserID, getIdJenis, getNamaJenis, getHarga, getWaktu;
    String selectedDate, selectedDay;
    List<String> namaDokter;
    List<String> jenisAppointment;
    String nama_selected, jenis_selected, selected_jenis;
    String url_get_dokter = "http://10.0.2.2/athensmobileproject/getDokterSesuaiJadwal.php";
    String url_get_jenis_appointment = "http://10.0.2.2/athensmobileproject/getJenisAppointment.php";
    String url_print_waktu_appointment = "http://10.0.2.2/athensmobileproject/printWaktuAppointment.php";
    String url_get_id_dokter = "http://10.0.2.2/athensmobileproject/getIdDokterSesuaiPilihan.php";
    String url_insert_appointment = "http://10.0.2.2/athensmobileproject/insertAppointment.php";
    String url_get_id_jenis = "http://10.0.2.2/athensmobileproject/getIdJenisSesuaiPilihan.php";

//    String url_get_dokter = "https://athensclinic.000webhostapp.com/getDokterSesuaiJadwal.php";
//    String url_get_jenis_appointment = "https://athensclinic.000webhostapp.com/getJenisAppointment.php";
//    String url_print_waktu_appointment = "https://athensclinic.000webhostapp.com/printWaktuAppointment.php";
//    String url_get_id_dokter = "https://athensclinic.000webhostapp.com/getIdDokterSesuaiPilihan.php";
//    String url_insert_appointment = "https://athensclinic.000webhostapp.com/insertAppointment.php";
//    String url_get_id_jenis = "https://athensclinic.000webhostapp.com/getIdJenisSesuaiPilihan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran_appointment);
        getSupportActionBar().setTitle("Pendaftaran Appointment");
        datebtn = (ImageButton) findViewById(R.id.dateBtn);
        waktuAppointment = (TextView)findViewById(R.id.waktuAppointment);
        spinDokter = (Spinner)findViewById(R.id.spinnerDokter);
        spinJenis = (Spinner)findViewById(R.id.spinnerJenis);
        showWaktu = (Button)findViewById(R.id.showWaktu);
        namaDokter =  new ArrayList<String>();
        jenisAppointment =  new ArrayList<String>();
        tglAppointment = (TextView) findViewById(R.id.tglAppointment);
        namaDokter.add("- Pilih Dokter -");
        jenisAppointment.add("- Pilih Jenis Appointment -");
        submit = (Button)findViewById(R.id.SubmitAppointmentBtn);
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");

        backBtn();
        datePicker();
        spinnerJenisAppointment();
        spinDokterSelected();
        spinJenisSelected();
        showWaktuClicked();
        submitAppointment();
        spinnerNamaDokter();



        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, namaDokter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDokter.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, jenisAppointment);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJenis.setAdapter(adapter2);
    }

    public void backBtn(){
        Button back = (Button)findViewById(R.id.daftarBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomePasien.class);
                i.putExtra("UserID", UserID);
                startActivity(i);
            }
        });
    }

    public void spinnerJenisAppointment(){
        RequestQueue queue2 = Volley.newRequestQueue(PendaftaranAppointment.this);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url_get_jenis_appointment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member= jObj.getJSONArray(TAG_DATA);
                    for (int i=0; i< member.length();i++) {
                        JSONObject a = member.getJSONObject(i);
                        getNamaJenis = a.getString(TAG_NAMA_JENIS);
                        getHarga = a.getString(TAG_HARGA);
                        jenisAppointment.add(getNamaJenis + "-Rp. " + getHarga);
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
                Toast.makeText(PendaftaranAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue2.add(stringRequest2);
    }

    public void spinJenisSelected(){
        spinJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexJenis = spinJenis.getSelectedItemPosition();
                if(indexJenis == 0){

                } else {
                    getIdJenis();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void spinDokterSelected(){
        spinDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexDokter = spinDokter.getSelectedItemPosition();
                if(indexDokter == 0){
                    waktuAppointment.setText("");
                } else {
                    waktuAppointment.setText("");
                    getIdDokter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getIdJenis(){
        jenis_selected = spinJenis.getSelectedItem().toString();
        String[] seperate = jenis_selected.split("-");
        selected_jenis = seperate[0];
        RequestQueue queue5 = Volley.newRequestQueue(PendaftaranAppointment.this);
        StringRequest stringRequest5 = new StringRequest(Request.Method.POST, url_get_id_jenis, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    JSONObject a = member.getJSONObject(0);
                    getIdJenis = a.getString(TAG_ID_JENIS);

                }
                catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(PendaftaranAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("jenis", selected_jenis);
                return params;
            }
        };
        queue5.add(stringRequest5);
    }

    public void showWaktuClicked(){
        showWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tglAppointment.getText().toString().matches("") | indexDokter == 0){
                    Toast.makeText(PendaftaranAppointment.this, "silahkan pilih tanggal appointment dan dokter terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    printWaktuAppointment();
                }


            }
        });
    }

    public void spinnerNamaDokter(){
        tglAppointment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                waktuAppointment.setText("");
                namaDokter.clear();
                namaDokter.add("- Pilih Dokter -");
                spinDokter.setSelection(0);
                populateDokterSpinner();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void submitAppointment(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexDokter = spinDokter.getSelectedItemPosition();
                indexJenis = spinJenis.getSelectedItemPosition();
                if(tglAppointment.getText().toString().matches("") | indexDokter == 0 | indexJenis == 0){
                    Toast.makeText(PendaftaranAppointment.this, "Silahkan isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    if(waktuAppointment.getText().toString().matches("")){
                        Toast.makeText(PendaftaranAppointment.this, "Silahkan menekan tombol show untuk melihat waktu appointment Anda terlebih dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        RequestQueue queue6 = Volley.newRequestQueue(PendaftaranAppointment.this);
                        StringRequest stringRequest6 = new StringRequest(Request.Method.POST, url_insert_appointment, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jObj = new JSONObject(response);
                                    int sukses = jObj.getInt("success");
                                    if (sukses == 1) {
                                        Toast.makeText(PendaftaranAppointment.this, "Data Appointment berhasil disimpan", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), HomePasien.class);
                                        intent.putExtra("UserID", UserID);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(PendaftaranAppointment.this, "Data Appointment gagal disimpan", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    Log.e("Error", ex.toString());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Error", error.getMessage());
                                Toast.makeText(PendaftaranAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("idPasien", UserID);
                                params.put("idDokter", getIdDokter);
                                params.put("idJenis", getIdJenis);
                                params.put("tglAppointment", tglAppointment.getText().toString());
                                params.put("waktuAppointment", waktuAppointment.getText().toString());
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Content-Type", "application/x-www-form-urlencoded");
                                return params;
                            }
                        };
                        queue6.getCache().clear();
                        queue6.add(stringRequest6);


                    }
                }


            }
        });
    }


    public void datePicker(){
        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(PendaftaranAppointment.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Year, int Month, int dayOfMonth) {
                        tglAppointment.setText(Year + "-" + (Month + 1) + "-" + dayOfMonth);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, Year);
                        c.set(Calendar.MONTH, Month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                        String[] seperate = selectedDate.split(",");
                        selectedDay = seperate[0];
                    }
                }, year, month, day);

                Calendar calMin = Calendar.getInstance();
                calMin.add(Calendar.DAY_OF_YEAR, 2);
                datePickerDialog.getDatePicker().setMinDate(calMin.getTimeInMillis());

                Calendar calMax = Calendar.getInstance();
                calMax.add(Calendar.MONTH, 2);
                datePickerDialog.getDatePicker().setMaxDate(calMax.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    public void populateDokterSpinner(){
        RequestQueue queue = Volley.newRequestQueue(PendaftaranAppointment.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_dokter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i=0; i < member.length();i++) {
                        JSONObject a = member.getJSONObject(i);
                        getNamaDokter = a.getString(TAG_NAMA);
                        namaDokter.add(getNamaDokter);
                    }
                }
                catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(PendaftaranAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("hari", selectedDay);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void getIdDokter(){
        nama_selected = spinDokter.getSelectedItem().toString();
        RequestQueue queue3 = Volley.newRequestQueue(PendaftaranAppointment.this);
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url_get_id_dokter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    JSONObject a = member.getJSONObject(0);
                    getIdDokter = a.getString(TAG_ID_DOKTER);

                }
                catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(PendaftaranAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("nama", nama_selected);
                return params;
            }
        };
        queue3.add(stringRequest3);
    }

    public void printWaktuAppointment(){
        RequestQueue queue4 = Volley.newRequestQueue(PendaftaranAppointment.this);
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, url_print_waktu_appointment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);
                    JSONObject a = member.getJSONObject(0);
                    getWaktu = a.getString(TAG_WAKTU);
                    waktuAppointment.setText(getWaktu);

                }
                catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(PendaftaranAppointment.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idDokter", getIdDokter);
                params.put("hari", selectedDay);
                return params;
            }
        };
        queue4.add(stringRequest4);

    }
}
