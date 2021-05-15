package com.example.mid_term.object;

public class muonTra {
    private int ID;
    private String tenThietBi;
    private String maPhong;
    private int soLuong;
    private String ngaymuon;
    private String IDTHIETBI;
    private String IDPHONGHOC;

    public muonTra() {
    }

    public muonTra(muonTra mt) {
        this.tenThietBi = mt.tenThietBi;
        this.ID = mt.ID;
        this.maPhong = mt.maPhong;
        this.soLuong = mt.soLuong;
        this.ngaymuon = mt.ngaymuon;
        this.IDPHONGHOC = mt.getIDPHONGHOC();
        this.IDTHIETBI = mt.getIDTHIETBI();
    }

    public muonTra(int ID, String tenThietBi, String maPhong, int soLuong, String ngaymuon, String IDTHIETBI, String IDPHONGHOC) {
        this.ID = ID;
        this.tenThietBi = tenThietBi;
        this.maPhong = maPhong;
        this.soLuong = soLuong;
        this.ngaymuon = ngaymuon;
        this.IDTHIETBI = IDTHIETBI;
        this.IDPHONGHOC = IDPHONGHOC;
    }

    public String getIDTHIETBI() {
        return IDTHIETBI;
    }

    public void setIDTHIETBI(String IDTHIETBI) {
        this.IDTHIETBI = IDTHIETBI;
    }

    public String getIDPHONGHOC() {
        return IDPHONGHOC;
    }

    public void setIDPHONGHOC(String IDPHONGHOC) {
        this.IDPHONGHOC = IDPHONGHOC;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }
}
