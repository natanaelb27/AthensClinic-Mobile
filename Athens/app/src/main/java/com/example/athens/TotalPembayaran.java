package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

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

public class TotalPembayaran extends AppCompatActivity {
    ListView lvActive, lvDone;
    Button back;
    String UserID, get_idActive, get_idDone;
    ArrayList<HashMap<String, String>> list_appointment_active;
    ArrayList<HashMap<String, String>> list_appointment_done;
    String url_get_appointment_active = "http://10.0.2.2/athensmobileproject/getAppointmentActiveByPasien.php";
    String url_get_appointment_done= "http://10.0.2.2/athensmobileproject/getAppointmentDoneByPasien.php";
//    String url_get_appointment_active = "https://athensclinic.000webhostapp.com/getAppointmentActiveByPasien.php";
//    String url_get_appointment_done= "https://athensclinic.000webhostapp.com/getAppointmentDoneByPasien.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_DOKTER = "Dokter";
    private static final String TAG_TGL = "tglAppointment";
    private static final String TAG_JENIS = "jenisAppointment";
    private static final String TAG_WAKTU = "waktuAppointment";
    private static final String TAG_ID_APPOINTMENT = "idAppointment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_pembayaran);
        getSupportActionBar().setTitle("Total Pembayaran");

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        list_appointment_active = new ArrayList<>();
        list_appointment_done = new ArrayList<>();
        lvActive = findViewById(R.id.listViewAppointmentActive);
        lvDone = findViewById(R.id.listViewAppointmentDone);
        back = (Button)findViewById(R.id.backTotalPembayaran);

        getActive();
        getDone();
        activeClicked();
        doneClicked();
        backClicked();
    }

    public void backClicked(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomePasien.class);
                i.putExtra("UserID", UserID);
                startActivity(i);
            }
        });
    }

    public void activeClicked(){
        lvActive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                get_idActive = ((TextView)view.findViewById(R.id.idAppointmentActive)).getText().toString();
                Intent i = new Intent(getApplicationContext(), DetailAppointment.class);
                i.putExtra("UserID", UserID);
                i.putExtra("appointmentID", get_idActive);
                i.putExtra("petunjuk", "Silahkan ke resepsionis untuk melakukan pembayaran!");
                i.putExtra("status", "active");
                startActivity(i);
            }
        });
    }

    public void doneClicked(){
        lvDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                get_idDone = ((TextView)view.findViewById(R.id.idAppointmentDone)).getText().toString();
                Intent i = new Intent(getApplicationContext(), DetailAppointment.class);
                i.putExtra("UserID", UserID);
                i.putExtra("appointmentID", get_idDone);
                i.putExtra("petunjuk", "");
                i.putExtra("status", "done");
                startActivity(i);
            }
        });

    }

    public void getActive(){
        RequestQueue queue = Volley.newRequestQueue(TotalPembayaran.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_appointment_active, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String idAppointment = a.getString(TAG_ID_APPOINTMENT);
                        String tgl = a.getString(TAG_TGL);
                        String dokter = a.getString(TAG_DOKTER);
                        String jenis = a.getString(TAG_JENIS);
                        String waktu = a.getString(TAG_WAKTU);
                        SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = stringToDate.parse(tgl);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        tgl = dateFormat.format(date);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("idAppointment", idAppointment);
                        map.put("tgl", tgl);
                        map.put("dokter", dokter);
                        map.put("jenis", jenis);
                        map.put("waktu", waktu);
                        list_appointment_active.add(map);
                    }

                    String[] from = {"idAppointment", "tgl", "dokter", "jenis", "waktu"};
                    int[] to = {R.id.idAppointmentActive, R.id.tglActive, R.id.namaDActive, R.id.jenisActive, R.id.waktuActive};
                    ListAdapter adapter = new SimpleAdapter(TotalPembayaran.this, list_appointment_active, R.layout.list_appointment_active, from, to);
                    lvActive.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(TotalPembayaran.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", UserID);
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

    public void getDone(){
        RequestQueue queue = Volley.newRequestQueue(TotalPembayaran.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_appointment_done, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member = jObj.getJSONArray(TAG_DATA);

                    for (int i = 0; i < member.length(); i++) {
                        JSONObject a = member.getJSONObject(i);
                        String idAppointment = a.getString(TAG_ID_APPOINTMENT);
                        String tgl = a.getString(TAG_TGL);
                        String dokter = a.getString(TAG_DOKTER);
                        String jenis = a.getString(TAG_JENIS);
                        String waktu = a.getString(TAG_WAKTU);
                        SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = stringToDate.parse(tgl);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        tgl = dateFormat.format(date);

                        HashMap<String, String> map = new HashMap<>();
                        map.put("idAppointment", idAppointment);
                        map.put("tgl", tgl);
                        map.put("dokter", dokter);
                        map.put("jenis", jenis);
                        map.put("waktu", waktu);
                        list_appointment_done.add(map);
                    }

                    String[] from = {"idAppointment", "tgl", "dokter", "jenis", "waktu"};
                    int[] to = {R.id.idAppointmentDone, R.id.tglDone, R.id.namaDDone, R.id.jenisDone, R.id.waktuDone};
                    ListAdapter adapter = new SimpleAdapter(TotalPembayaran.this, list_appointment_done, R.layout.list_appointment_done, from, to);
                    lvDone.setAdapter(adapter);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                Toast.makeText(TotalPembayaran.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", UserID);
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
