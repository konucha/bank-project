package com.example.bankakprind.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransaksiModel {
    private String idTransaksi;
    private LocalDate tanggal;
    private LocalTime jam;
    private double uang;
    private boolean status;

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public TransaksiModel(String idTransaksi, LocalDate tanggal, LocalTime jam, double uang, boolean status) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.jam = jam;
        this.uang = uang;
        this.status = status;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public LocalTime getJam() {
        return jam;
    }

    public void setJam(LocalTime jam) {
        this.jam = jam;
    }

    public double getUang() {
        return uang;
    }

    public void setUang(double uang) {
        this.uang = uang;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
