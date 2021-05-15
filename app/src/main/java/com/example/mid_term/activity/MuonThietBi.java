package com.example.mid_term.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mid_term.DAO.phieuMuonDAO;
import com.example.mid_term.DAO.phongHocDAO;
import com.example.mid_term.DAO.thietBiDAO;
import com.example.mid_term.R;
import com.example.mid_term.adapter.ThietBiMuonTraAdapter;
import com.example.mid_term.object.ThietBiMuonTra;
import com.example.mid_term.object.muonTra;
import com.example.mid_term.object.phongHoc;
import com.example.mid_term.object.thietBi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MuonThietBi extends AppCompatActivity {

    Spinner spMaPhong, spTenThietBi;
    TextView btnMuon, btnHuyMuonTra;
    EditText edtNgayMuonTra, edtsoluong;

    ArrayList<String> arrayMaPhong;
    ArrayAdapter adapter;

    com.example.mid_term.DAO.phieuMuonDAO phieuMuonDAO;

    ThietBiMuonTraAdapter adapterTbMuonTra;
    ArrayList<thietBi> arrTbMuonTra;
    ArrayList<phongHoc> phongHocs;
    String tenTb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muon_thiet_bi);
        setTitle("MƯỢN THIẾT BỊ");

        phieuMuonDAO = new phieuMuonDAO(this);

        setControl();
        getData();
        setData();
        setEvent();
    }

    private void setEvent() {

        spTenThietBi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thietBi tb = arrTbMuonTra.get(position);
                tenTb = tb.getTenTB();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // button
        btnMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spMaPhong.getSelectedItem().toString().equals("")) {
                    Toast.makeText(MuonThietBi.this, "Mã phòng không được rỗng!", Toast.LENGTH_SHORT).show();
                } else if (tenTb.toString().trim().equals("")) {
                    Toast.makeText(MuonThietBi.this, "Tên thiết bị không được rỗng!", Toast.LENGTH_SHORT).show();
                } else if (edtsoluong.getText().toString().equals("")) {
                    Toast.makeText(MuonThietBi.this, "Số Lượng không được rỗng không được rỗng!", Toast.LENGTH_SHORT).show();
                } else {

                    int soluongmuon = Integer.valueOf(edtsoluong.getText().toString());
                    muonTra mt = new muonTra();
                    for (phongHoc p:phongHocs ) {
                        if (spMaPhong.getSelectedItem().toString().trim().equalsIgnoreCase(p.getTenPhong())){
                            mt.setIDPHONGHOC(p.getID()+"");
                        }
                    }
                    for (thietBi t:arrTbMuonTra ) {
                        if (tenTb.equalsIgnoreCase(t.getTenTB())){
                            mt.setIDTHIETBI(t.getID()+"");
                        }
                    }
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
                    mt.setNgaymuon(date);

                    mt.setSoLuong(soluongmuon);
                    boolean result = phieuMuonDAO.insert(mt);
                    if (result) {
                        Toast.makeText(MuonThietBi.this, "THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        while (PhieuMuon.arrayListMuonTra.size() > 0) {
                            PhieuMuon.arrayListMuonTra.remove(0);
                        }
                        for (muonTra m : phieuMuonDAO.selectAll()) {
                            PhieuMuon.arrayListMuonTra.add(m);
                        }
                        PhieuMuon.muontraAdapter.notifyDataSetChanged();
                        onBackPressed();
                    } else {
                        Toast.makeText(MuonThietBi.this, "KHÔNG ĐỦ VẬT TƯ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // button
        btnHuyMuonTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setData() {

        adapter = new ArrayAdapter(MuonThietBi.this, android.R.layout.simple_list_item_1, arrayMaPhong);
        spMaPhong.setAdapter(adapter);

        adapterTbMuonTra = new ThietBiMuonTraAdapter(MuonThietBi.this, R.layout.thiet_bi_muon_tra_item, arrTbMuonTra);
        spTenThietBi.setAdapter(adapterTbMuonTra);
    }

    private void getData() {
        arrayMaPhong = new ArrayList<>();
        phongHocs = new phongHocDAO(this).selectAll();
        for (phongHoc p: phongHocs) {
            arrayMaPhong.add(p.getTenPhong());
        }

        arrTbMuonTra = new thietBiDAO(this).selectAll();
    }

    private void setControl() {
        spMaPhong = findViewById(R.id.spinnerMaPhongMuonTra);
        spTenThietBi = findViewById(R.id.spinnerTenThietBiMuonTra);

        btnMuon = findViewById(R.id.buttonMuonTra);
        btnHuyMuonTra = findViewById(R.id.buttonHuyMuonTra);

        edtsoluong = findViewById(R.id.editTextSoLuongMuonTra);
    }
}