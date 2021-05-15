package com.example.mid_term.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mid_term.DAO.phieuMuonDAO;
import com.example.mid_term.R;

public class ThongKe extends AppCompatActivity {
    TextView thongkemuon, thongketansuat, thongkeconlai, piechart;
    phieuMuonDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setTitle("THỐNG KÊ");

        dao = new phieuMuonDAO(this);

        setControl();
        setEvent();
    }

    private void setEvent() {
        thongkemuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKe.this, ThongKeMuon.class);
                startActivity(intent);
            }
        });
        thongketansuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKe.this, ThongKeTanSuat.class);
                startActivity(intent);
            }
        });
        thongkeconlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKe.this, ThongKeConLai.class);
                startActivity(intent);
            }
        });
        piechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongKe.this, ThongKePieChart.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        thongkemuon = findViewById(R.id.tk_thongkemuon);
        thongketansuat = findViewById(R.id.tk_thongketansuat);
        thongkeconlai = findViewById(R.id.tk_thongkeconlai);
        piechart = findViewById(R.id.tk_thongkepiechart);
    }
}