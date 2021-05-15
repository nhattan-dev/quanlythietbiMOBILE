package com.example.mid_term.DTO;

public class muonTra {
    private String tenThietBi;
    private int solan;
    private int soluong;
    private int damuon;

    public muonTra(String tenThietBi, int soluong, int damuon) {
        this.tenThietBi = tenThietBi;
        this.soluong = soluong;
        this.damuon = damuon;
    }

    public muonTra() {
        this.soluong = 0;
        this.solan = 0;
        this.damuon = 0;
        this.tenThietBi = "";
    }

    public muonTra(String tenThietBi, int solan) {
        this.tenThietBi = tenThietBi;
        this.solan = solan;
    }

    public muonTra(int soluong, String tenThietBi) {
        this.tenThietBi = tenThietBi;
        this.soluong = soluong;
    }

    public int getDamuon() {
        return damuon;
    }

    public void setDamuon(int damuon) {
        this.damuon = damuon;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public int getSolan() {
        return solan;
    }

    public void setSolan(int solan) {
        this.solan = solan;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
