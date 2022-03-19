package com.example.bankakprind;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankakprind.helper.DBRekening;
import com.example.bankakprind.model.RekeningModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {
    Button btnSignUp;

    EditText tfNoKtp, tfNama, tfAlamat, tfNoRekening, tfPin;


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

    DatabaseReference reference = DBRekening.getInstance().getReference("users");

    private void signUp() {
        long noKtp;
        int noRekening, pin, repin;
        String nama, alamat;


        try {
            noKtp = Long.parseLong(tfNoKtp.getText().toString());
            noRekening = Integer.parseInt(tfNoRekening.getText().toString());
            nama = tfNama.getText().toString();
            alamat = tfAlamat.getText().toString();
            pin = Integer.parseInt(tfPin.getText().toString());

            EditText tfRepin = findViewById(R.id.tfRepin);
            repin = Integer.parseInt(tfRepin.getText().toString());

            // validasi inputan
            if (nama.matches("") || alamat.matches("")) {
                Toast.makeText(getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pin != repin) {
                Toast.makeText(getApplicationContext(), "Pin tidak cocok", Toast.LENGTH_SHORT).show();
                return;
            }

            //TODO  VALIDASI JIKA SUDAH ADA AKUN TERDAFTAR DENGAN VALIDASI REKENING
//            if (!norekeningValidation(noRekening)) {
//                Toast.makeText(getApplicationContext(), "No rekening tidak terseddia", Toast.LENGTH_SHORT).show();
//                return;
//            }


        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Masukan data yang sesuai", Toast.LENGTH_SHORT).show();
            return;
        }

        // masukan data ke firebase
        RekeningModel akun = new RekeningModel(
                noKtp, nama, alamat, noRekening, pin, (double) 0
        );
        String id = String.valueOf(noRekening);
        reference.child(id).setValue(akun);

        Toast.makeText(getApplicationContext(), "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean status;
    private boolean norekeningValidation(int norekUser) {
        status = true;
        DatabaseReference reference = DBRekening.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Integer norekeningDB = postSnapshot.child("noRekening").getValue(Integer.class);
                    if (norekeningDB != null) {
                        Log.d("ASUS", "NOREK DB: " + norekeningDB);
                        Log.d("ASUS", "NO REK USER: " + norekUser);

                        if (norekeningDB == norekUser) {
                            status = false;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
        return status;
    }
}