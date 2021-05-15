package com.example.mid_term.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mid_term.DAO.phieuMuonDAO;
import com.example.mid_term.DAO.thietBiDAO;
import com.example.mid_term.DTO.muonTra;
import com.example.mid_term.R;
import com.example.mid_term.object.thietBi;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ThongKePieChart extends AppCompatActivity {
    Spinner spinner;
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    PieChart pieChart;
    static String thang, nam;
    public static String date;
    Dialog dialog;
    PieData data;
    PieDataSet dataSet;

    int positionItem;

    ArrayList<thietBi> tbList = new ArrayList<>();

    ArrayList<String> arrayMaPhong = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_pie_chart);
        setTitle("THỐNG KÊ THIẾT BỊ");

        setControl();
        setAdapter();
        setEvent();
    }

    private void setAdapter() {
        arrayMaPhong.removeAll(arrayMaPhong);
        tbList = new thietBiDAO(this).selectAll();
        for (thietBi t : tbList) {
            arrayMaPhong.add(t.getTenTB());
        }
        adapter = new ArrayAdapter(ThongKePieChart.this, android.R.layout.simple_list_item_1, arrayMaPhong);
        spinner.setAdapter(adapter);
    }

    private void setData(muonTra mt) {
        entries.removeAll(entries);
        labels.removeAll(labels);

        if (mt.getSoluong() != 0)
            entries.add(new Entry(mt.getSoluong(), 0));
        if (mt.getDamuon() != 0)
            entries.add(new Entry(mt.getDamuon(), 1));

        int sum = mt.getDamuon() + mt.getSoluong();
        double conlai = (double) Math.round(mt.getSoluong() * 10000.0 / sum) / 100;
        double damuon = (double) Math.round(mt.getDamuon() * 10000.0 / sum) / 100;
        labels.add("CÒN LẠI (" + conlai + "%)");
        labels.add("ĐÃ MƯỢN (" + damuon + "%)");

        dataSet = new PieDataSet(entries, "data");

        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //create chart
        data = new PieData(labels, dataSet);
        pieChart.setNoDataText("KHÔNG CÓ DỮ LIỆU");
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void setEvent() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ThongKePieChart.this, position + "", Toast.LENGTH_SHORT).show();
                int ID = tbList.get(position).getID();
                muonTra mt = new phieuMuonDAO(ThongKePieChart.this).selectbyThietBi(ID);
                setData(mt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setControl() {
        pieChart = findViewById(R.id.tk_piechart);
        spinner = findViewById(R.id.tk_piechart_spiner);
    }
}