package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeResepsionis extends AppCompatActivity {
    String resepsionisID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_resepsionis);
        getSupportActionBar().setTitle("Home Resepsionis");
        Intent intent = getIntent();
        resepsionisID = intent.getStringExtra("resepsionisID");
        prosesPembayaran();
        appointmentPasien();
        obatPasien();
    }
    public void prosesPembayaran(){
        ImageButton prosesPembayaran = (ImageButton)findViewById(R.id.prosesPembayaran);
        prosesPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProsesPembayaran.class);
                intent.putExtra("resepsionisID", resepsionisID);
                startActivity(intent);
            }
        });
    }

    public void appointmentPasien(){
        ImageButton appointmentPasien = (ImageButton)findViewById(R.id.appointmentResepsionis);
        appointmentPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResepsionisCekAppointment.class);
                intent.putExtra("resepsionisID", resepsionisID);
                startActivity(intent);
            }
        });
    }

    public void obatPasien(){
        ImageButton obatPasien = (ImageButton)findViewById(R.id.obatResepsionis);
        obatPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResepsionisCekObatPasien.class);
                intent.putExtra("resepsionisID", resepsionisID);
                startActivity(intent);
            }
        });
    }
}
