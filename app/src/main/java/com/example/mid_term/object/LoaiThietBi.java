package com.example.mid_term.object;

public class LoaiThietBi {

    private int id;
    private String loaiTb;



    public LoaiThietBi(int id, String loaiTb) {
        this.id = id;
        this.loaiTb = loaiTb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiTb() {
        return loaiTb;
    }

    public void setLoaiTb(String loaiTb) {
        this.loaiTb = loaiTb;
    }
}
