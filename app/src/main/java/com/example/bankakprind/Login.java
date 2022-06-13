package com.example.bankakprind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankakprind.fragment.MainFragment;
import com.example.bankakprind.helper.DBRekening;
import com.example.bankakprind.model.RekeningModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    Button btnLogin, btnSignUp;
    EditText tfNoRekening, tfPin;

    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        tfNoRekening = findViewById(R.id.tfRekening);
        tfPin = findViewById(R.id.tfPin);

        database = DBRekening.getInstance();
        reference = database.getReference("users");

        btnLogin.setOnClickListener(view -> loginProcess());

        btnSignUp.setOnClickListener(view -> {
            // masuk activity pendaftaran
            Intent signUpIntent = new Intent(getApplicationContext(), Signup.class);
            startActivity(signUpIntent);
        });
    }


    int noRekening, pin;
    RekeningModel akun;
    boolean accountMatch = false;

    private void loginProcess() {
        // ambil data dari form login
        try {
            noRekening = Integer.parseInt(tfNoRekening.getText().toString());
            pin = Integer.parseInt(tfPin.getText().toString());
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Masukan data yang valid", Toast.LENGTH_SHORT).show();
            return;
        }

        // cocokan data form dengan database
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int rekeningDb;
                accountMatch = false;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    rekeningDb = data.child("noRekening").getValue(Integer.class);
                    int pinDb = data.child("pin").getValue(Integer.class);

                    // pengecekan akun user dengan database
                    if (rekeningDb == noRekening && pinDb == pin) {
                        accountMatch = true;
                        akun = new RekeningModel(
                                data.child("noKtp").getValue(Long.class),
                                data.child("nama").getValue(String.class),
                                data.child("alamat").getValue(String.class),
                                data.child("noRekening").getValue(Integer.class),
                                data.child("pin").getValue(Integer.class),
                                data.child("saldo").getValue(Double.class)
                        );
                    }
                }

                // berhasil login, masuk ke dashboard (MainFragment)
                if (accountMatch) {
                    Intent intent = new Intent(getApplicationContext(), MainFragment.class);
                    intent.putExtra("akun", akun);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Akun tidak tersedia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Gagal mendapatkan data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}