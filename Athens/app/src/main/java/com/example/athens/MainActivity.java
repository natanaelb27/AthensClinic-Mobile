package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPasien();
        buttonKaryawan();
    }

    public void buttonPasien(){
        Button pasien = (Button)findViewById(R.id.pasienbtn);
        pasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Pasien();
            }
        });
    }

    public void openActivity_Pasien(){
        Intent intent = new Intent(this, Pasien.class);
        startActivity(intent);
    }


    public void buttonKaryawan(){
        Button karyawan = (Button)findViewById(R.id.karyawanbtn);
        karyawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_Karyawan();
            }
        });
    }

    public void openActivity_Karyawan(){
        Intent intent = new Intent(this, Karyawan.class);
        startActivity(intent);
    }
}
