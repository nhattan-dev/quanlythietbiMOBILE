package com.example.mid_term.object;

public class thietBi {
    private int ID;
    private String tenTB;
    private String loaitb;
    private byte[] HinhAnh;
    private int soLuong;
    private String XuatXu;

    public thietBi() {
    }

    public thietBi(int ID, String tenTB, String loaitb, byte[] hinhAnh, int soLuong, String xuatXu) {
        this.ID = ID;
        this.tenTB = tenTB;
        this.loaitb = loaitb;
        HinhAnh = hinhAnh;
        this.soLuong = soLuong;
        XuatXu = xuatXu;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getLoaitb() {
        return loaitb;
    }

    public void setLoaitb(String loaitb) {
        this.loaitb = loaitb;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String xuatXu) {
        XuatXu = xuatXu;
    }
}
