package com.example.bankakprind;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankakprind.helper.DBRekening;
import com.example.bankakprind.model.RekeningModel;
import com.google.firebase.database.DatabaseReference;

public class EditAccount extends AppCompatActivity {


    EditText tfNama, tfAlamat, tfNoktp;
    Button btnEdit, btnCancel;
    RekeningModel akun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Intent it = getIntent();
        akun = (RekeningModel) it.getSerializableExtra("akun");

        tfNama = findViewById(R.id.tv_namaedit);
        tfNoktp = findViewById(R.id.tv_noktp);
        tfAlamat = findViewById(R.id.tv_alamatedit);

        tfNama.setText(akun.getNama());
        tfAlamat.setText(akun.getAlamat());
        tfNoktp.setText(String.valueOf(akun.getNoKtp()));

        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);

        btnEdit.setOnClickListener(view -> editData());
        btnCancel.setOnClickListener(view -> finish());
    }

    private void editData() {
        // validasi form
        long noKtp;
        String nama, alamat;
        try {
            noKtp = Long.parseLong(tfNoktp.getText().toString());
            nama = tfNama.getText().toString();
            alamat = tfAlamat.getText().toString();

            // TODO
            // LONG INTEGER UNTUK NO KTP DAN NO KTP
            // PANJANG PIN WAJIB 6 DIGIT
            // VALIDASI JIKA SUDAH ADA AKUN TERDAFTAR DENGAN VALIDASI REKENING

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Masukan data yang sesuai", Toast.LENGTH_SHORT).show();
            return;
        }

        // insert to DB
        DatabaseReference reference = DBRekening.getInstance().getReference("users");

        String id = String.valueOf(akun.getNoRekening());

//        akun.setNama(nama);
//        akun.setAlamat(alamat);
//        akun.setNoKtp(noKtp);

        reference.child(id).child("noKtp").setValue(noKtp);
        reference.child(id).child("nama").setValue(nama);
        reference.child(id).child("alamat").setValue(alamat);


        // create new object for update account
//        RekeningModel newAccount = new RekeningModel(
//                noKtp,
//                nama,
//                alamat,
//                akun.getNoRekening(),
//                akun.getPin(),
//                akun.getSaldo()
//        );

        // add new account to DB
//        String newId = String.valueOf(noRekening);
//        reference.child(newId).setValue(newAccount);

        // remove old account


    }


}
