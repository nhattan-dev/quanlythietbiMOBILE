package com.example.mid_term.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mid_term.DAO.phongHocDAO;
import com.example.mid_term.R;
import com.example.mid_term.adapter.PhongHocAdapter;
import com.example.mid_term.object.phongHoc;

import java.util.ArrayList;

public class PhongHoc extends AppCompatActivity {

    ListView listDsPhongHoc;
    public static PhongHocAdapter adapter;
    public static ArrayList<phongHoc> arrayPhongHoc;
    com.example.mid_term.DAO.phongHocDAO phongHocDAO;
    Dialog dialog;

    String maPhong, loaiPhong, tang;
    Button btnHuy;
    Button btnXacNhan;

    EditText edtMaPhong;
    EditText edtLoaiPhong;
    EditText edtTang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_hoc);
        setTitle("QUẢN LÝ PHÒNG HỌC");

        phongHocDAO = new phongHocDAO(this);
        dialog  = new Dialog(PhongHoc.this);
        dialog.setContentView(R.layout.phonghoc_dialog);
        setControl();
        getData();
        setData();
        setEvent();
    }

    private void getData() {
        arrayPhongHoc = phongHocDAO.selectAll();
    }

    private void setEvent() {

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setData() {
        adapter = new PhongHocAdapter(PhongHoc.this, R.layout.phonghoc_item, arrayPhongHoc);
        listDsPhongHoc.setAdapter(adapter);
    }

    private void setControl() {
        listDsPhongHoc = (ListView) findViewById(R.id.listviewDsPhongHoc);
        btnHuy = dialog.findViewById(R.id.buttonHUYPhongHocADD);
        btnXacNhan = dialog.findViewById(R.id.buttonThemPhongHocADD);

        edtMaPhong = dialog.findViewById(R.id.editTextMaPhongADD);
        edtLoaiPhong = dialog.findViewById(R.id.editTextLoaiPhongADD);
        edtTang = dialog.findViewById(R.id.editTextTangPhongADD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.custom_dsphong, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText.trim());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.themDsPhongHoc) {

            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edtMaPhong.getText().toString().trim().equals("")) {
                        Toast.makeText(PhongHoc.this, "Mã phòng không được rỗng!", Toast.LENGTH_SHORT).show();
                    } else if (edtLoaiPhong.getText().toString().trim().equals("")) {
                        Toast.makeText(PhongHoc.this, "Loại phòng không được rỗng!", Toast.LENGTH_SHORT).show();
                    } else if (edtTang.getText().toString().trim().equals("")) {
                        Toast.makeText(PhongHoc.this, "Tầng không được rỗng!", Toast.LENGTH_SHORT).show();
                    } else {
                        String maPhongadd = edtMaPhong.getText().toString().trim();
                        String loaiPhongadd = edtLoaiPhong.getText().toString().trim();
                        String tangadd = edtTang.getText().toString().trim();
                        phongHoc ph = new phongHoc(0, maPhongadd, loaiPhongadd, tangadd);
                        boolean result = phongHocDAO.insert(ph);
                        if (result) {
                            Toast.makeText(PhongHoc.this, "THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                            while (arrayPhongHoc.size() > 0) {
                                arrayPhongHoc.remove(0);
                            }
                            for (phongHoc p : phongHocDAO.selectAll()) {
                                arrayPhongHoc.add(p);
                            }
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(PhongHoc.this, "THẤT BẠI!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            dialog.show();
        }
        if (item.getItemId() == R.id.thoatDsPhongHoc) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialog_SuaPhongHoc(phongHoc p) {
        edtMaPhong.setText(p.getTenPhong());
        edtLoaiPhong.setText(p.getLoaiPhong());
        edtTang.setText(p.getTang());
        dialog.show();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mp  = edtMaPhong.getText().toString().trim();
                String lp = edtLoaiPhong.getText().toString().trim();
                String t = edtTang.getText().toString().trim();
                phongHoc ph = new phongHoc(p.getID(), mp, lp, t);
                boolean result = phongHocDAO.edit(ph);
                if(result){
                    Toast.makeText(PhongHoc.this, "THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                    while(arrayPhongHoc.size() > 0){
                        arrayPhongHoc.remove(0);
                    }
                    for (phongHoc pH: phongHocDAO.selectAll()) {
                        arrayPhongHoc.add(pH);
                    }
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(PhongHoc.this, "THẤT BẠI!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}