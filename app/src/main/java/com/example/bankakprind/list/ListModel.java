package com.example.bankakprind.list;

public class ListModel {
    int timestamp;
    String tipe;
    String jumlah;

    public ListModel(int timestamp, String tipe, String jumlah) {
        this.timestamp = timestamp;
        this.tipe = tipe;
        this.jumlah = jumlah;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
