package com.example.athens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class PasienRegister extends AppCompatActivity {
    int day, month, year;
    ImageButton datebtn;
    TextView tglLahir;
    TextInputLayout namaPasien, alamatPasien, emailPasien, phonePasien, passwordPasien;
    Button submitbtn;
    RadioGroup rg;
    RadioButton selectedRadioButton;
    String jkP = "", namaP = "", alamatP = "", emailP = "", phoneP = "", passwordP = "", tglLahirP = "";
    private static final String TAG_DATA = "data";
    private static final String TAG_PHONE = "Phone";
    String url_register_pasien = "http://10.0.2.2/athensmobileproject/registerPasien.php";
    String url_get_phone_number = "https://10.0.2.2/athensmobileproject/getPhoneNumber.php";

//    String url_get_phone_number = "https://athensclinic.000webhostapp.com/getPhoneNumber.php";
//    String url_register_pasien = "https://athensclinic.000webhostapp.com/registerPasien.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_register);
        getSupportActionBar().setTitle("Daftar Pasien Baru");
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        namaPasien = (TextInputLayout)findViewById(R.id.namaPasien);
        alamatPasien = (TextInputLayout)findViewById(R.id.alamatPasien);
        emailPasien = (TextInputLayout)findViewById(R.id.emailPasien);
        phonePasien = (TextInputLayout)findViewById(R.id.phonePasien);
        passwordPasien = (TextInputLayout)findViewById(R.id.passwordPasien);
        datebtn = (ImageButton) findViewById(R.id.datePicker);
        tglLahir = (TextView) findViewById(R.id.tglLahir);

        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerShow();
            }
        });

        submitbtn = (Button)findViewById(R.id.submitbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonID = rg.getCheckedRadioButtonId();
                if(selectedRadioButtonID != -1){
                    selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    jkP = selectedRadioButton.getText().toString();
                }

                namaP = namaPasien.getEditText().getText().toString();
                alamatP = alamatPasien.getEditText().getText().toString();;
                emailP = emailPasien.getEditText().getText().toString();;
                phoneP = phonePasien.getEditText().getText().toString();;
                passwordP = passwordPasien.getEditText().getText().toString();;
                tglLahirP = tglLahir.getText().toString();
                if(jkP.length() > 0 & tglLahirP.length() > 0 & validateNama() & validateAlamat() & validateEmail() & validatePassword() & validatePhone()){
                    if(namaLength() & alamatLength() & emailLength() & passwordLength() & phoneLength()){
                        if(passwordP.length() >= 8){
                            RequestQueue queue = Volley.newRequestQueue(PasienRegister.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_register_pasien, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jObj = new JSONObject(response);
                                        int sukses = jObj.getInt("success");
                                        if (sukses == 1) {
                                            Toast.makeText(PasienRegister.this, "Data Pasien berhasil disimpan", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(PasienRegister.this, "Nomor telpon tersebut sudah terdaftar", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception ex) {
                                        Log.e("Error", ex.toString());
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Error", error.getMessage());
                                    Toast.makeText(PasienRegister.this, "silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("namaPasien", namaP);
                                    params.put("jkPasien", jkP);
                                    params.put("tglLahirPasien", tglLahirP);
                                    params.put("alamatPasien", alamatP);
                                    params.put("emailPasien", emailP);
                                    params.put("phonePasien", phoneP);
                                    params.put("passwordPasien", passwordP);
                                    return params;
                                }

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("Content-Type", "application/x-www-form-urlencoded");
                                    return params;
                                }
                            };
                            queue.getCache().clear();
                            queue.add(stringRequest);

                            Intent intent = new Intent(getApplication(), Pasien.class);
                            startActivity(intent);
                        } else {
                            passwordPasien.setError("Password terlalu pendek (minimum 8 character)");
                        }
                    }
                } else {
                    Toast.makeText(PasienRegister.this, "Silahkan isi semua field terlebih dahulu", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void datepickerShow(){
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PasienRegister.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Year, int Month, int dayOfMonth) {
                tglLahir.setText(Year + "-" + (Month+1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private boolean namaLength(){
        if(namaP.length() > 50) {
            namaPasien.setError("Nama terlalu panjang");
            return false;
        } else {
            return true;
        }

    }

    private boolean validateNama(){
        if(namaP.isEmpty()){
            namaPasien.setError("Field tidak boleh kosong");
            return false;
        } else {
            namaPasien.setError(null);
            return true;
        }
    }

    private boolean alamatLength(){
        if(alamatP.length() > 100){
            alamatPasien.setError("Alamat terlalu panjang");
            return false;
        } else {
            return true;
        }

    }

    private boolean validateAlamat(){
        if(alamatP.isEmpty()){
            alamatPasien.setError("Field tidak boleh kosong");
            return false;
        } else {
            alamatPasien.setError(null);
            return true;
        }
    }

    private boolean emailLength(){
        if(emailP.length() > 50){
            emailPasien.setError("Email terlalu panjang");
            return false;
        } else {
            return true;
        }

    }

    private boolean validateEmail(){
        if(emailP.isEmpty()){
            emailPasien.setError("Field tidak boleh kosong");
            return false;
        } else {
            emailPasien.setError(null);
            return true;
        }
    }

    private boolean phoneLength(){
        if(phoneP.length() > 20){
            emailPasien.setError("Nomor telpon terlalu panjang");
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhone(){
        if(phoneP.isEmpty()){
            phonePasien.setError("Field tidak boleh kosong");
            return false;
        } else {
            phonePasien.setError(null);
            return true;
        }
    }

    private boolean passwordLength(){
        if(passwordP.length() > 30){
            passwordPasien.setError("Password terlalu panjang");
            return false;
        } else {
            return true;
        }

    }
    private boolean validatePassword(){
        if(passwordP.isEmpty()){
            passwordPasien.setError("Field tidak boleh kosong");
            return false;
        } else {
            passwordPasien.setError(null);
            return true;
        }
    }


}
