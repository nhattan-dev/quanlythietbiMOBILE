package com.example.mid_term.object;

public class phongHoc {

    private int ID;
    public String tenPhong;
    public String loaiPhong;
    public String tang;

    public phongHoc() {
    }

    public phongHoc(phongHoc p) {
        this.ID = p.getID();
        this.loaiPhong = p.getLoaiPhong();
        this.tenPhong = p.getTenPhong();
        this.tang = p.getTang();
    }

    public phongHoc(int ID, String maPhong, String loaiPhong, String tang) {
        this.ID = ID;
        this.tenPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.tang = tang;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public String getTang() {
        return tang;
    }

    public void setTang(String tang) {
        this.tang = tang;
    }
}
