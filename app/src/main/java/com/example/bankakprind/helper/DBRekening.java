package com.example.bankakprind.helper;

import com.google.firebase.database.FirebaseDatabase;

public class DBRekening {

    public static FirebaseDatabase getInstance() {
        return FirebaseDatabase.getInstance("https://bank-akprind-default-rtdb.asia-southeast1.firebasedatabase.app/");
    }
}
