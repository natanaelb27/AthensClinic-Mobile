package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeDokter extends AppCompatActivity {
    String dokterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dokter);
        getSupportActionBar().setTitle("Home Dokter");
        Intent intent = getIntent();
        dokterID = intent.getStringExtra("dokterID");
        cekJadwalDanAppointment();
        resepObatPasien();
    }
    public void cekJadwalDanAppointment(){
        ImageButton cek = (ImageButton)findViewById(R.id.dokterJadwalDanAppointment);
        cek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DokterCekJadwalDanAppointment.class);
                intent.putExtra("dokterID", dokterID);
                startActivity(intent);
            }
        });
    }

    public void resepObatPasien(){
        ImageButton resep = (ImageButton)findViewById(R.id.resepObatPasien);
        resep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResepObatPasien.class);
                intent.putExtra("dokterID", dokterID);
                startActivity(intent);
            }
        });
    }
}
