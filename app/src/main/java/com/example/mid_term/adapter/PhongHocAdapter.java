package com.example.mid_term.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mid_term.DAO.phieuMuonDAO;
import com.example.mid_term.DAO.phongHocDAO;
import com.example.mid_term.DAO.thietBiDAO;
import com.example.mid_term.R;
import com.example.mid_term.activity.PhongHoc;
import com.example.mid_term.activity.ThietBi;
import com.example.mid_term.object.muonTra;
import com.example.mid_term.object.phongHoc;
import com.example.mid_term.object.thietBi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhongHocAdapter extends BaseAdapter {

    private PhongHoc context;
    private int layout;
    private List<phongHoc> listPhongHoc;
    //khai bao mang tam
    private ArrayList<phongHoc> array;

    public PhongHocAdapter(PhongHoc context, int layout, List<phongHoc> listPhongHoc) {
        this.context = context;
        this.layout = layout;
        this.listPhongHoc = listPhongHoc;

        this.array = new ArrayList<phongHoc>();
        array.addAll(listPhongHoc);
    }

    @Override
    public int getCount() {
        return listPhongHoc.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHoder {
        TextView txtMaPhong, txtLoaiPhong, txtTang;
        ImageView imgDeletePhongHoc, imgEditPhongHoc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder hoder;

        if (convertView == null) {
            hoder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            hoder.txtMaPhong = (TextView) convertView.findViewById(R.id.textViewMaPhongHoc);
            hoder.txtLoaiPhong = (TextView) convertView.findViewById(R.id.textViewLoaiPhong);
            hoder.txtTang = (TextView) convertView.findViewById(R.id.textViewTangPhongHoc);

            hoder.imgDeletePhongHoc = (ImageView) convertView.findViewById(R.id.imageButtonDeletePhongHoc);
            hoder.imgEditPhongHoc = (ImageView) convertView.findViewById(R.id.imageButtonEditPhongHoc);

            convertView.setTag(hoder);

        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);

        phongHoc ph = listPhongHoc.get(position);
        hoder.txtMaPhong.setText(ph.getTenPhong());
        hoder.txtLoaiPhong.setText(ph.getLoaiPhong());
        hoder.txtTang.setText(ph.getTang());

        hoder.imgDeletePhongHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (muonTra m : new phieuMuonDAO(context).selectAll2()) {
                    if (Integer.parseInt(m.getIDTHIETBI()) == ph.getID()) {
                        Toast.makeText(context, "THẤT BẠI(FK)!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //confirm
                new AlertDialog.Builder(context)
                        .setTitle("XÁC NHẬN !")
                        .setMessage("XÁC NHẬN XÓA ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                phongHoc p = new phongHoc();
                                p.setID(ph.getID());
                                boolean result = new phongHocDAO(context).delete(p);
                                if (result) {
                                    Toast.makeText(context, "THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < PhongHoc.arrayPhongHoc.size(); i++) {
                                        if (PhongHoc.arrayPhongHoc.get(i).getID() == ph.getID()) {
                                            PhongHoc.arrayPhongHoc.remove(i);
                                            break;
                                        }
                                    }
                                    PhongHoc.adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "THẤT BẠI!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("NO", null).show();
            }
        });

        hoder.imgEditPhongHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phongHoc p = new phongHoc(ph);
                context.dialog_SuaPhongHoc(p);
            }
        });


        return convertView;
    }

    public void filter(String text) {
        //chuyen ve chu thường
        text = text.toLowerCase(Locale.getDefault());
        listPhongHoc.clear();
        if (text.length() == 0) {
            listPhongHoc.addAll(array);
        } else {
            for (phongHoc lsphonghoc : array) {
                if (lsphonghoc.tenPhong.toLowerCase(Locale.getDefault()).contains(text)) {
                    listPhongHoc.add(lsphonghoc);
                }
            }
        }
        notifyDataSetChanged();
    }
}
