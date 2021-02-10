package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class HomePasien extends AppCompatActivity {
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pasien);
        getSupportActionBar().setTitle("Home Pasien");
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        daftarAppointmentBtn();
        jadwalDokterBtn();
        pembayaranBtn();
        obatBtn();
        appointmentAndaBtn();
    }

    public void daftarAppointmentBtn(){
        ImageButton daftarAppointment = (ImageButton)findViewById(R.id.daftarAppointment);
        daftarAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PendaftaranAppointment.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });
    }

    public void jadwalDokterBtn(){
        ImageButton jadwalDokter = (ImageButton)findViewById(R.id.JadwalDokter);
        jadwalDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JadwalDokter.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });

    }
    public void pembayaranBtn(){
        ImageButton pembayaran = (ImageButton)findViewById(R.id.TotalPembayaran);
        pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TotalPembayaran.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });

    }

    public void obatBtn(){
        ImageButton obat = (ImageButton)findViewById(R.id.TrackingObat);
        obat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackingResepObat.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });
    }

    public void appointmentAndaBtn(){
        ImageButton appointmentAnda = (ImageButton)findViewById(R.id.AppointmentAnda);
        appointmentAnda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppointmentAnda.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });

    }
}
