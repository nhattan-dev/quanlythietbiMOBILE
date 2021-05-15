package com.example.mid_term.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mid_term.R;
import com.example.mid_term.object.LoaiThietBi;

import java.util.List;

public class LoaiTbAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<LoaiThietBi> loaiThietBiList;

    public LoaiTbAdapter(Context context, int layout, List<LoaiThietBi> loaiThietBiList) {
        this.context = context;
        this.layout = layout;
        this.loaiThietBiList = loaiThietBiList;
    }

    @Override
    public int getCount() {
        return loaiThietBiList.size();
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
        TextView txtLoaiTB;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder holder;

        if(convertView == null){
            holder = new ViewHoder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.txtLoaiTB = (TextView) convertView.findViewById(R.id.textViewLoaiTB);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHoder) convertView.getTag();
        }

        LoaiThietBi tb = loaiThietBiList.get(position);

        holder.txtLoaiTB.setText(tb.getLoaiTb());

        return convertView;
    }
}
