package com.example.mid_term.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mid_term.DAO.thietBiDAO;
import com.example.mid_term.R;
import com.example.mid_term.adapter.ThietBiAdapter;
import com.example.mid_term.object.thietBi;

import java.util.ArrayList;

public class ThietBi extends AppCompatActivity {
    com.example.mid_term.DAO.thietBiDAO thietBiDAO;
    GridView listDsThietBi;
    public static ArrayList<thietBi> arrayList;
    public static ThietBiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi);
        setTitle("QUẢN LÝ THIẾT BỊ");
        thietBiDAO = new thietBiDAO(this);

        setControl();
        getData();
        setData();
    }

    private void getData() {
        arrayList = thietBiDAO.selectAll();
    }

    private void setData() {
        adapter = new ThietBiAdapter(ThietBi.this, R.layout.thietbi_item, arrayList);
        listDsThietBi.setAdapter(adapter);
    }

    private void setControl() {
        listDsThietBi = findViewById(R.id.listViewDsThietBi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dsthietbi, menu);

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
        if (item.getItemId() == R.id.menuThemThietBi) {
            Intent intent = new Intent(ThietBi.this, ThietBiMoi.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.menuThoatThietBi){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void update(int id, String tentb, String loaitb, byte[] hinhanh, String xuatxu, int sl) {
        ThietBiCapNhat.thietBi = new thietBi(id, tentb, loaitb, hinhanh, sl, xuatxu);
        Intent intent = new Intent(ThietBi.this, ThietBiCapNhat.class);
        startActivity(intent);
    }
}