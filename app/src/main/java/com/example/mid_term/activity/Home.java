package com.example.mid_term.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mid_term.R;

public class Home extends AppCompatActivity {
    Context context;
    ImageView hm_qlph1, hm_qltb1, hm_qlmt1, hm_tk1;
    TextView hm_qlph2, hm_qltb2, hm_qlmt2, hm_tk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("TRANG CHỦ");
        this.context = this;

        setControl();
        setEvent();
    }

    private void setEvent() {
        hm_qltb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQLTB();
            }
        });
        hm_qltb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQLTB();
            }
        });
        hm_qlph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQLPH();
            }
        });
        hm_qlph2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQLPH();
            }
        });
        hm_qlmt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQLMT();
            }
        });
        hm_qlmt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickQLMT();
            }
        });
        hm_tk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTK();
            }
        });
        hm_tk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickTK();
            }
        });
    }

    private void clickQLTB() {
        Intent intent = new Intent(Home.this, ThietBi.class);
        startActivity(intent);
    }

    private void clickQLPH() {
        Intent intent = new Intent(Home.this, PhongHoc.class);
        startActivity(intent);
    }

    private void clickQLMT() {
        Intent intent = new Intent(Home.this, PhieuMuon.class);
        startActivity(intent);
    }

    private void clickTK() {
        Intent intent = new Intent(Home.this, ThongKe.class);
        startActivity(intent);
    }

    private void setControl() {
        hm_qltb1 = findViewById(R.id.hm_qltb1);
        hm_qlph1 = findViewById(R.id.hm_qlph1);
        hm_qlmt1 = findViewById(R.id.hm_qlmt1);
        hm_tk1 = findViewById(R.id.hm_tk1);
        hm_qltb2 = findViewById(R.id.hm_qltb2);
        hm_qlph2 = findViewById(R.id.hm_qlph2);
        hm_qlmt2 = findViewById(R.id.hm_qlmt2);
        hm_tk2 = findViewById(R.id.hm_tk2);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);

        dialog.setTitle("XÁC NHẬN");
        dialog.setIcon(R.mipmap.ic_launcher_round);
        dialog.setMessage("Bạn có muốn thoát không!");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("NO", null);
        dialog.show();
    }
}