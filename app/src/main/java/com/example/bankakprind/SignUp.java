package com.example.bankakprind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bankakprind.model.RekeningModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    Button btnSignUp;

    EditText tfNoKtp, tfNama, tfAlamat, tfNoRekening, tfPin;

    //database
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://bank-akprind-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //from form activity
        tfNoKtp = findViewById(R.id.tfKtp);
        tfNama = findViewById(R.id.tfNama);
        tfAlamat = findViewById(R.id.tfAlamat);
        tfNoRekening = findViewById(R.id.tfRekening);
        tfPin = findViewById(R.id.tfPin);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(view -> signUp());
    }

    private void signUp() {
        // validasi inputan

        // exception
        int noRekening, noKtp, pin;
        String nama, alamat;

        try {
            noKtp =  Integer.parseInt(tfNoKtp.getText().toString()) ;
            noRekening = Integer.parseInt(tfNoRekening.getText().toString()) ;
            pin = Integer.parseInt(tfPin.getText().toString()) ;
            nama = tfNama.getText().toString();
            alamat = tfAlamat.getText().toString();

            // TODO
            // LONG INTEGER UNTUK NO KTP DAN NO KTP
            // PANJANG PIN WAJIB 6 DIGIT
            // VALIDASI JIKA SUDAH ADA AKUN TERDAFTAR DENGAN VALIDASI REKENING

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error guys", Toast.LENGTH_SHORT).show();
            Log.d("RIZAL", "Error: " + ex);
            return;
        }

        // masukan data ke firebase
        RekeningModel akun = new RekeningModel(
                noKtp, nama, alamat, noRekening, pin
        );
        String id = String.valueOf(noRekening);
        reference = database.getReference("users");
        reference.child(id).setValue(akun);

        Toast.makeText(getApplicationContext(), "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
        try {
            TimeUnit.SECONDS.sleep(1);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}