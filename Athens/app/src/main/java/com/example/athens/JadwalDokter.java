package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import java.util.List;

public class JadwalDokter extends AppCompatActivity {
    Spinner spinNamaDokter;
    ImageView imgJadwal;
    int index;
    String url_get_dokter = "http://10.0.2.2/athensmobileproject/getDokter.php";
//    String url_get_dokter = "https://athensclinic.000webhostapp.com/getDokter.php";

    private static final String TAG_DATA = "data";
    private static final String TAG_NAMA = "namadokter";
    List<String> namaDokter;
    String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_dokter);
        getSupportActionBar().setTitle("Jadwal Dokter");
        namaDokter =  new ArrayList<String>();
        namaDokter.add("- Pilih Dokter -");
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        spinNamaDokter = (Spinner)findViewById(R.id.spinnerJadwalNamaDokter);
        RequestQueue queue = Volley.newRequestQueue(JadwalDokter.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_dokter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray member= jObj.getJSONArray(TAG_DATA);


                    for (int i=0; i< member.length();i++) {
                        JSONObject a = member.getJSONObject(i);
                        String nama = a.getString(TAG_NAMA);
                        namaDokter.add(nama);
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
                Toast.makeText(JadwalDokter.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, namaDokter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNamaDokter.setAdapter(adapter);

        spinNamaDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonNoFilter();
        buttonFilter();
        buttonBack();
    }

    public void buttonBack(){
        Button back = (Button)findViewById(R.id.jadwalBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomePasien.class);
                i.putExtra("UserID", UserID);
                startActivity(i);
            }
        });

    }

    public void buttonNoFilter(){
        Button noFilter = (Button)findViewById(R.id.noFilterBtn);
        noFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JadwalFilter fragFilter = new JadwalFilter();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameJadwal, fragFilter);
                Bundle args = new Bundle();
                args.putInt("index", 0);
                fragFilter.setArguments(args);
                fragmentTransaction.commit();
            }
        });
    }

    public void buttonFilter(){
        Button Filter = (Button)findViewById(R.id.filterBtn);
        Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = spinNamaDokter.getSelectedItemPosition();
                if(index == 0){
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih Dokter Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    JadwalFilter fragFilter = new JadwalFilter();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameJadwal, fragFilter);
                    Bundle args = new Bundle();
                    args.putInt("index", index);
                    fragFilter.setArguments(args);
                    fragmentTransaction.commit();

                }

            }
        });


    }
}
