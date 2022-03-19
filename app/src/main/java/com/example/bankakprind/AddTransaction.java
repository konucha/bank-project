package com.example.bankakprind;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankakprind.helper.CurrencyRupiah;
import com.example.bankakprind.helper.DBRekening;
import com.example.bankakprind.helper.MyNotification;
import com.example.bankakprind.model.RekeningModel;
import com.example.bankakprind.model.TransactionModel;
import com.google.firebase.database.DatabaseReference;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddTransaction extends AppCompatActivity {

    private final String[] listStatus = {"Tabung", "Tarik"};
    String typeTransaksi;
    Button btnCancel, btnAddTransaction;
    private RekeningModel akun;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        final Spinner spinner = findViewById(R.id.status_spinner);

        //Inisialiasi Array Adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, listStatus);
        spinner.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        String type = bundle.getString("type");
        akun = (RekeningModel) bundle.getSerializable("akunRekening");

        //set default option, spinner
        if (type.equals("nabung")) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                typeTransaksi = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnAddTransaction = findViewById(R.id.btnProcess);
        btnAddTransaction.setOnClickListener(view -> transactionProcess());

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> finish());
    }

    EditText tfUang;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongViewCast")
    private void transactionProcess() {

        double uang = 0;
        try {
            tfUang = findViewById(R.id.tfNominal);
            String textNominal = tfUang.getText().toString();

            if (textNominal.matches("")) {
                Toast.makeText(getApplicationContext(), "Data tidak boleh kosong ", Toast.LENGTH_SHORT).show();
                return;
            }

            uang = Double.parseDouble(textNominal);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Masukan data yang valid", Toast.LENGTH_SHORT).show();
        }

        // transaksi tabung atau ambil
        DatabaseReference ref = DBRekening.getInstance().getReference("users");
        if (typeTransaksi.equals("Tabung")) {
            akun.setSaldo(akun.getSaldo() + uang);

            // update saldo kedatabase
            ref.child(String.valueOf(akun.getNoRekening())).child("saldo").setValue(akun.getSaldo());

            // buat log transaksi
            insertToDB(uang);

            Toast.makeText(getApplicationContext(), "Transaksi tambah saldo berhasil", Toast.LENGTH_SHORT).show();
            MyNotification.createNotification(
                    "Transaksi Berhasil",
                    "Anda berhasil menabung " + CurrencyRupiah.format(uang),
                    getApplicationContext()
            );
        }

        // Tarik tabungan //
        else {
            // cek saldo tidak boleh kurang dari yg akan diambil
            if (akun.getSaldo() < uang) {
                Toast.makeText(getApplicationContext(), "Saldo tidak mencukupi", Toast.LENGTH_SHORT).show();
                return;
            }

            akun.setSaldo(akun.getSaldo() - uang);
            ref.child(String.valueOf(akun.getNoRekening())).child("saldo").setValue(akun.getSaldo());

            insertToDB(uang);
            Toast.makeText(getApplicationContext(), "Transaksi tarik saldo berhasil", Toast.LENGTH_LONG).show();
            MyNotification.createNotification(
                    "Transaksi Berhasil",
                    "Anda berhasil menarik uang " + CurrencyRupiah.format(uang),
                    getApplicationContext()
            );
        }
    }

    TransactionModel transaction;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertToDB(Double uang) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatJam = DateTimeFormatter.ofPattern("HH:mm:ss");

        transaction = new TransactionModel(
                timestamp.getTime(),
                formatTanggal.format(now),
                formatJam.format(now),
                uang,
                typeTransaksi
        );

        // set id for key data firebase
        String idUser = String.valueOf(akun.getPin());
        String idTrans = String.valueOf(transaction.getIdTransaksi());

        // insert to firebase
        DatabaseReference reference = DBRekening.getInstance().getReference("transactions");
        reference.child(idUser).child(idTrans).setValue(transaction);
    }
}

