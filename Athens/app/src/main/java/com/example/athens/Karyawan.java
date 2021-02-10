package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Karyawan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan);
        buttonDokter();
        buttonResepsionis();
        getSupportActionBar().setTitle("Karyawan");
    }

    public void buttonDokter(){
        Button dokter = (Button)findViewById(R.id.dokterbtn);
        dokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Dokter();
            }
        });
    }

    public void openActivity_Dokter(){
        Intent intent = new Intent(this, DokterLogin.class);
        startActivity(intent);
    }

    public void buttonResepsionis(){
        Button resepsionis = (Button)findViewById(R.id.resepsionisbtn);
        resepsionis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Resepsionis();
            }
        });
    }

    public void openActivity_Resepsionis(){
        Intent intent = new Intent(this, ResepsionisLogin.class);
        startActivity(intent);
    }
}
