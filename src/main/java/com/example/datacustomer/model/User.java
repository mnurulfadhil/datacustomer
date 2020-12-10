package com.example.datacustomer.model;

public class User {
    int id;
    String nama;
    String alamat;
    int id_kota;
    double pendapatan;

    public User(int id,String nama,String alamat,int id_kota,double pendapatan){
        this.id=id;
        this.nama=nama;
        this.alamat=alamat;
        this.id_kota=id_kota;
        this.pendapatan=pendapatan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId_kota() {
        return id_kota;
    }

    public void setId_kota(int id_kota) {
        this.id_kota = id_kota;
    }

    public double getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(double pendapatan) {
        this.pendapatan = pendapatan;
    }
}
