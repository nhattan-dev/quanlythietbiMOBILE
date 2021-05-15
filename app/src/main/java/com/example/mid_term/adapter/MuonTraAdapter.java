package com.example.mid_term.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mid_term.DAO.phieuMuonDAO;
import com.example.mid_term.R;
import com.example.mid_term.activity.PhieuMuon;
import com.example.mid_term.object.muonTra;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MuonTraAdapter extends BaseAdapter {

    private PhieuMuon context;
    private int layout;
    private List<muonTra> listMuonTra;
    private ArrayList<muonTra> arrayMuonTra;

    public MuonTraAdapter(PhieuMuon context, int layout, List<muonTra> listMuonTra) {
        this.context = context;
        this.layout = layout;
        this.listMuonTra = listMuonTra;

        this.arrayMuonTra = new ArrayList<muonTra>();
        arrayMuonTra.addAll(listMuonTra);
    }

    @Override
    public int getCount() {
        return listMuonTra.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtmaPhongmt, txtTenTbMt, txtNgayMuon, txtsoLuongMuonTra;
        ImageView imgbtTra;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder hoder;

        if(convertView == null){
            hoder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            hoder.txtmaPhongmt = (TextView) convertView.findViewById(R.id.textViewMaPhongMuonTra);
            hoder.txtTenTbMt = (TextView) convertView.findViewById(R.id.textViewTenThietBiMuonTra);
            hoder.txtNgayMuon = (TextView) convertView.findViewById(R.id.textViewNgayMuon);
            hoder.txtsoLuongMuonTra = (TextView) convertView.findViewById(R.id.textViewSoLuongMuonTra);

            hoder.imgbtTra = convertView.findViewById(R.id.imageViewTra);


            convertView.setTag(hoder);

        }else {
            hoder = (ViewHolder) convertView.getTag();
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);

        muonTra mt = listMuonTra.get(position);

        hoder.txtmaPhongmt.setText(mt.getMaPhong());
        hoder.txtTenTbMt.setText(mt.getTenThietBi());
        hoder.txtNgayMuon.setText(mt.getNgaymuon());

        String soluong = String.valueOf(mt.getSoLuong());
        hoder.txtsoLuongMuonTra.setText(soluong);
        int id = mt.getID();

        hoder.imgbtTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muonTra m = new muonTra(mt);
                m.setSoLuong(0);
                boolean result = new phieuMuonDAO(context).edit(m);
                if (result){
                    Toast.makeText(context, "THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                    while (context.arrayListMuonTra.size() > 0){
                        context.arrayListMuonTra.remove(0);
                    }
                    for (muonTra mT:new phieuMuonDAO(context).selectAll() ) {
                        context.arrayListMuonTra.add(mT);
                    }
                    context.muontraAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "THẤT BẠI!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public void filter(String text){
        //chuyen ve chu thường
        text = text.toLowerCase(Locale.getDefault());
        listMuonTra.clear();
        if(text.length() == 0){
            listMuonTra.addAll(arrayMuonTra);
        }else {
            for (muonTra muonTra : arrayMuonTra ){
                if (muonTra.getMaPhong().toLowerCase(Locale.getDefault()).contains(text)){
                    listMuonTra.add(muonTra);
                }
            }
        }
        notifyDataSetChanged();
    }
}
