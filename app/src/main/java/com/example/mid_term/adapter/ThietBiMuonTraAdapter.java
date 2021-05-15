package com.example.mid_term.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mid_term.R;
import com.example.mid_term.object.ThietBiMuonTra;
import com.example.mid_term.object.thietBi;

import java.util.ArrayList;
import java.util.List;

public class ThietBiMuonTraAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<thietBi> listTbMuonTra;

    public ThietBiMuonTraAdapter(Context context, int layout, ArrayList<thietBi> listTbMuonTra) {
        this.context = context;
        this.layout = layout;
        this.listTbMuonTra = listTbMuonTra;
    }

    @Override
    public int getCount() {
        return listTbMuonTra.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHoder{
        TextView txtTenTb;
        ImageView imgHinhAnh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder holder;

        if(convertView == null){
            holder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.txtTenTb = convertView.findViewById(R.id.textViewTenThietBiSpinner);
            holder.imgHinhAnh = convertView.findViewById(R.id.imageViewHinhThietBiSpinner);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHoder) convertView.getTag();
        }

        ThietBiMuonTra tb = new ThietBiMuonTra(listTbMuonTra.get(position).getHinhAnh(), listTbMuonTra.get(position).getTenTB());

        holder.txtTenTb.setText(tb.getTenTB());

        byte[] hinhanh = tb.getHinhanh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0,hinhanh.length);
        holder.imgHinhAnh.setImageBitmap(bitmap);

        return convertView;
    }
}
