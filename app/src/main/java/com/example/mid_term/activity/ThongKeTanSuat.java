package com.example.mid_term.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mid_term.DAO.phieuMuonDAO;
import com.example.mid_term.DTO.muonTra;
import com.example.mid_term.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThongKeTanSuat extends AppCompatActivity {
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();
    BarChart barchart;
    BarData data;
    BarDataSet dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_tan_suat);
        setTitle("THỐNG KÊ THIẾT BỊ CHƯA TRẢ");

        setControl();
        setData();
        setEvent();
    }

    private void setData() {
        ArrayList<muonTra> muonTras = new phieuMuonDAO(this).selectbyChuaTra();
        entries.removeAll(entries);
        labels.removeAll(labels);
        int i = 0;
        for (muonTra mt : muonTras) {
            entries.add(new BarEntry(mt.getSoluong(), i));
            labels.add(mt.getTenThietBi());
            i++;
        }
        dataSet = new BarDataSet(entries, "data");
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //create chart
        data = new BarData(labels, dataSet);
        barchart.setNoDataText("KHÔNG CÓ DỮ LIỆU");
        barchart.setData(data);
        barchart.invalidate();
    }

    private void setEvent() {
    }

    private void setControl() {
        barchart = findViewById(R.id.tk_muontra);
    }
}