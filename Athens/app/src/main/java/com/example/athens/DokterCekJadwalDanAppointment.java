package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class DokterCekJadwalDanAppointment extends AppCompatActivity {
    private Integer[] fotoJadwal = {R.drawable.jadwalakbar, R.drawable.jadwalbudi, R.drawable.jadwalsiti, R.drawable.jadwalnanda, R.drawable.jadwaltuti};
    String dokterID;
    int parameterIndex;
    ListView lv;
    ArrayList<HashMap<String, String>> list_appointment_dokter;
    String url_get_appointment = "http://10.0.2.2/athensmobileproject/getAppointmentByDokter.php";
//    String url_get_appointment = "https://athensclinic.000webhostapp.com/getAppointmentByDokter.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_ID_PASIEN = "namaPasien";
    private static final String TAG_TGL = "tglAppointment";
    private static final String TAG_JENIS = "jenisAppointment";
    private static final String TAG_WAKTU = "waktuAppointment";
    private static final String TAG_ID_APPOINTMENT = "idAppointment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_cek_jadwal_dan_appointment);
        getSupportActionBar().setTitle("Jadwal dan Appointment");
        Intent intent = getIntent();
        dokterID = intent.getStringExtra("dokterID");
        ImageView imgFilter = (ImageView)findViewById(R.id.jadwalDokterImg);
        try {
            parameterIndex = Integer.parseInt(dokterID);
        } catch (NumberFormatException nfe){

        }
        imgFilter.setImageResource(fotoJadwal[parameterIndex-1]);
        list_appointment_dokter = new ArrayList<>();
        lv = findViewById(R.id.listViewDokter);
        Button back = (Button)findViewById(R.id.backDokterCheck);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeDokter.class);
                intent.putExtra("dokterID", dokterID);
                startActivity(intent);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(DokterCekJadwalDanAppointment.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_appointment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String idAppointment = a.getString(TAG_ID_APPOINTMENT);
                        String tgl = a.getString(TAG_TGL);
                        String pasien = a.getString(TAG_ID_PASIEN);
                        String jenis = a.getString(TAG_JENIS);
                        String waktu = a.getString(TAG_WAKTU);
                        SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = stringToDate.parse(tgl);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        tgl = dateFormat.format(date);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", idAppointment);
                        map.put("tgl", tgl);
                        map.put("pasien", pasien);
                        map.put("jenis", jenis);
                        map.put("waktu", waktu);
                        list_appointment_dokter.add(map);
                    }

                    String[] from = {"id", "tgl", "pasien", "jenis", "waktu"};
                    int[] to = {R.id.appointmentIDListD, R.id.tglListD, R.id.namaListD, R.id.jenisListD, R.id.waktuListD};
                    ListAdapter adapter = new SimpleAdapter(DokterCekJadwalDanAppointment.this, list_appointment_dokter, R.layout.list_appointment_dokter, from, to);
                    lv.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(DokterCekJadwalDanAppointment.this, "silahkan cek koneksssi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idDokter", dokterID);
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
