package com.example.bankakprind.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bankakprind.R;
import com.example.bankakprind.model.RekeningModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends AppCompatActivity {

    BottomNavigationView navigationView;

    RekeningModel akun;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        Intent it = getIntent();
        akun = (RekeningModel) it.getSerializableExtra("akun");

        navigationView = findViewById(R.id.button_navigation);

        // fragement active pertamakali buka activity
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, HomeFragment.newInstance(akun)).commit();

        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = HomeFragment.newInstance(akun);
                    break;
                case R.id.nav_transact:
                    fragment = TransactionFragment.newInstance(akun);
                    break;
                case R.id.nav_history:
                    fragment = TrasactionHistoryFragment.newInstance(akun);
                    break;
                case R.id.nav_account:
                    fragment = ProfileFragment.newInstance(akun);
                    break;
                case R.id.nav_dev:
                    fragment = new DevFragment();
                    break;
            }

            if (fragment != null)
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

            return true;
        });
    }
}