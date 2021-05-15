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

public class ThongKeMuon extends AppCompatActivity {
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    BarChart barchart;
    TextView tk_thangnam, tk_nam, tk_thongke;
    static String thang, nam;
    public static String date;
    Dialog dialog;
    BarData data;
    BarDataSet dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_muon);
        setTitle("THỐNG KÊ THIẾT BỊ ĐÃ MƯỢN");

        setControl();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        date = simpleDateFormat.format(new Date());
        dialog = new Dialog(this);
        nam = date.split(" ")[0].split("-")[0];
        thang = date.split(" ")[0].split("-")[2];
        date = nam + "-10" + "-" + thang + " 10:10:10.111";
        tk_thangnam.setText("Tháng " + thang + " năm " + nam);
        setData();
        setEvent();
    }

    private void setData() {
        ArrayList<muonTra> muonTras = new phieuMuonDAO(this).selectbyMonth(thang, nam);
        entries.removeAll(entries);
        labels.removeAll(labels);
        int i = 0;
        for (muonTra mt : muonTras) {
            entries.add(new BarEntry(mt.getSolan(), i));
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
        tk_thangnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.number_picker_dialog);
                final DatePicker datePicker = dialog.findViewById(R.id.npd_datepicker);
                final TextView npd_ok = dialog.findViewById(R.id.npd_ok);
                final TextView npd_huy = dialog.findViewById(R.id.npd_huy);
                dialog.show();
                npd_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        thang = datePicker.getMonth() + 1 < 10 ? "0" + (datePicker.getMonth() + 1) : (datePicker.getMonth() + 1) + "";
                        nam = datePicker.getYear() + "";
                        date = nam + "-10" + "-" + thang + " 10:10:10.111";
                        tk_thangnam.setText("Tháng " + thang + " năm " + nam);
                        dialog.dismiss();
                    }
                });
                npd_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                tk_thongke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setData();
                    }
                });
            }
        });
    }

    private void setControl() {
        barchart = findViewById(R.id.barchart);
        tk_thangnam = findViewById(R.id.tk_thangnam);
        tk_thongke = findViewById(R.id.tk_thongke);
    }
}