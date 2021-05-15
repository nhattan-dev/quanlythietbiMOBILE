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

import com.example.mid_term.DAO.phieuMuonDAO;
import com.example.mid_term.R;
import com.example.mid_term.adapter.MuonTraAdapter;
import com.example.mid_term.object.muonTra;

import java.util.ArrayList;

public class PhieuMuon extends AppCompatActivity {

    com.example.mid_term.DAO.phieuMuonDAO phieuMuonDAO;
    public static MuonTraAdapter muontraAdapter;
    public static ArrayList<muonTra> arrayListMuonTra;
    GridView lsDsMuonTra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_muon);
        setTitle("QUẢN LÝ PHIẾU MƯỢN");
        
        phieuMuonDAO = new phieuMuonDAO(this);
        
        setControl();
        getData();
        setData();
        setEvent();
    }

    private void setEvent() {
    }

    private void setData() {
        muontraAdapter = new MuonTraAdapter(PhieuMuon.this, R.layout.phieumuon_item,arrayListMuonTra);
        lsDsMuonTra.setAdapter(muontraAdapter);
    }

    private void getData() {
        arrayListMuonTra = phieuMuonDAO.selectAll();
    }

    private void setControl() {
        lsDsMuonTra = findViewById(R.id.listviewDsMuonTra);
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_muon_tra, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                muontraAdapter.filter(newText.trim());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //click menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuMuonTra){
            startActivity(new Intent(PhieuMuon.this, MuonThietBi.class));
        }
        if(item.getItemId() == R.id.menuThoatMuonTra){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}