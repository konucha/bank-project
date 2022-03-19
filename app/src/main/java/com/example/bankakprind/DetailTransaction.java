package com.example.bankakprind;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bankakprind.helper.CurrencyRupiah;
import com.example.bankakprind.helper.DBRekening;
import com.example.bankakprind.model.RekeningModel;
import com.example.bankakprind.model.TransactionModel;
import com.google.firebase.database.DatabaseReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class DetailTransaction extends AppCompatActivity {


    TextView tvNama, tvNoRekening, tvAlamat, tvJam, tvTanggal, tvTipeTrasaksi, tvNominal;
    Button btnDelete;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        Intent it = getIntent();
        RekeningModel akun = (RekeningModel) it.getSerializableExtra("akun");
        TransactionModel transaksi = (TransactionModel) it.getSerializableExtra("transaksi");

        tvNama = findViewById(R.id.tv_namadelete);
        tvNoRekening = findViewById(R.id.tv_rekeningdelete);
        tvAlamat = findViewById(R.id.tv_alamatdelete);
        tvJam = findViewById(R.id.tv_jamdelete);
        tvTanggal = findViewById(R.id.tv_tanggaldelete);
        tvTipeTrasaksi = findViewById(R.id.tv_typedelete);
        tvNominal = findViewById(R.id.tv_nominaldelete);

        tvNama.setText(akun.getNama());
        tvNoRekening.setText(String.valueOf(akun.getNoRekening()));
        tvAlamat.setText(akun.getAlamat());
        tvJam.setText(transaksi.getJam());


        tvTipeTrasaksi.setText(transaksi.getJenisTransaksi());
        tvNominal.setText(CurrencyRupiah.format(transaksi.getUang()));

        try {
            tvTanggal.setText(
                    // Ubah tanggal dengan format E.g 02 Januari 2021
                    new SimpleDateFormat("dd MMM yyyy")
                            // data dari firebase diconvert ke object Date
                            .format(Objects.requireNonNull(new SimpleDateFormat("dd-MM-yyyy").parse(transaksi.getTanggal())))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnDelete = findViewById(R.id.btn_Hapus);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteData(akun, transaksi);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Yakin akan menghapus data?").setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener).show();
            }
        });
    }

    private void deleteData(RekeningModel akun, TransactionModel transaksi) {
        DatabaseReference reference = DBRekening.getInstance().getReference("transactions");
        reference.child(String.valueOf(akun.getNoRekening()))
                .child(String.valueOf(transaksi.getIdTransaksi()))
                .removeValue();
        Toast.makeText(getApplicationContext(), "Transaksi '" + transaksi.getIdTransaksi() + "' berhasil dihapus", Toast.LENGTH_SHORT).show();
    }
}