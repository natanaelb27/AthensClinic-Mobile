package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pasien extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);
        getSupportActionBar().setTitle("Pasien");
        buttonLogin();
        buttonRegister();
    }

    public void buttonLogin(){
        Button login = (Button)findViewById(R.id.login_pasienbtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_loginPasien();
            }
        });
    }

    public void openActivity_loginPasien(){
        Intent intent = new Intent(this, PasienLogin.class);
        startActivity(intent);
    }

    public void buttonRegister(){
        Button register = (Button)findViewById(R.id.register_pasienbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_registerPasien();
            }
        });
    }

    public void openActivity_registerPasien(){
        Intent intent = new Intent(this, PasienRegister.class);
        startActivity(intent);
    }
}
