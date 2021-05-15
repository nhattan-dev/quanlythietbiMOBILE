package com.example.mid_term.object;

public class ThietBiMuonTra {
    private byte[] hinhanh;
    private String tenTB;

    public ThietBiMuonTra(byte[] hinhanh, String tenTB) {
        this.hinhanh = hinhanh;
        this.tenTB = tenTB;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }
}
