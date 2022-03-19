package com.example.bankakprind.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TransactionModel implements Serializable {
    private Long idTransaksi;
    private String tanggal;
    private String jam;
    private double uang;
    private String jenisTransaksi;

    public Long getIdTransaksi() {
        return idTransaksi;
    }

    public TransactionModel() {

    }

    public TransactionModel(Long idTransaksi, String tanggal, String jam, double uang, String jenisTransaksi) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.jam = jam;
        this.uang = uang;
        this.jenisTransaksi = jenisTransaksi;
    }

    public void setIdTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public double getUang() {
        return uang;
    }

    public void setUang(double uang) {
        this.uang = uang;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }
}
