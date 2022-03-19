package com.example.bankakprind.model;

import java.io.Serializable;

public class RekeningModel implements Serializable {
    private Long noKtp;
    private String nama;
    private String alamat;
    private int noRekening;
    private int pin;
    private double saldo;

    public RekeningModel(Long noKtp, String nama, String alamat, int noRekening, int pin, Double saldo) {
        this.noKtp = noKtp;
        this.nama = nama;
        this.alamat = alamat;
        this.noRekening = noRekening;
        this.pin = pin;
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Long getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(Long noKtp) {
        this.noKtp = noKtp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(int noRekening) {
        this.noRekening = noRekening;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
