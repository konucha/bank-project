package com.example.bankakprind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTransaction extends AppCompatActivity {

    private String[] listStatus = {"Tabung", "Ambil"};
    boolean status;
    Button btnCancel, btnAddTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        final Spinner spinner = findViewById(R.id.status_spinner);

        //Inisialiasi Array Adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, listStatus);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                status = (adapter.getItem(i).equals("Tabung") ? true : false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnAddTransaction = findViewById(R.id.btnProcess);
        btnAddTransaction.setOnClickListener(view -> {

        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> {
            finish();
        });
    }
}