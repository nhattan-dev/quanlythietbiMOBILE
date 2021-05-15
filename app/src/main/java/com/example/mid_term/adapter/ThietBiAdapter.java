package com.example.mid_term.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.mid_term.DAO.thietBiDAO;
import com.example.mid_term.R;
import com.example.mid_term.activity.ThietBi;
import com.example.mid_term.object.muonTra;
import com.example.mid_term.object.thietBi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThietBiAdapter extends BaseAdapter {
    private ThietBi context;
    private int layout;
    private List<thietBi> thietBiList;
    private ArrayList<thietBi> arrayThietBi;

    public ThietBiAdapter(ThietBi context, int layout, List<thietBi> thietBiList) {
        this.context = context;
        this.layout = layout;
        this.thietBiList = thietBiList;

        this.arrayThietBi = new ArrayList<thietBi>();
        arrayThietBi.addAll(thietBiList);
    }

    @Override
    public int getCount() {
        return thietBiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {

        ImageView imgAnhTB, imgBTEdit, imgBTDelete;

        TextView txtTenTB, txtLoaiTB, txtXuatXu, txtSoLuong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.imgAnhTB = (ImageView) convertView.findViewById(R.id.imageThietBi);

            holder.txtTenTB = convertView.findViewById(R.id.textViewTenThieBi);
            holder.txtLoaiTB = convertView.findViewById(R.id.textViewLoaiTB);
            holder.txtXuatXu = convertView.findViewById(R.id.textViewXuatXu);
            holder.txtSoLuong = convertView.findViewById(R.id.textViewSoLuong);

            holder.imgBTDelete = convertView.findViewById(R.id.imageButtonDeleteThietBi);
            holder.imgBTEdit = convertView.findViewById(R.id.imageButtonEditThietBi);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.appear);
        convertView.startAnimation(animation);

        thietBi tb = thietBiList.get(position);
        holder.txtTenTB.setText(tb.getTenTB());
        holder.txtLoaiTB.setText(tb.getLoaitb());
        holder.txtXuatXu.setText(tb.getXuatXu());

        String sl = String.valueOf(tb.getSoLuong());
        holder.txtSoLuong.setText(sl);
        // chuyen du lieu tu byte ve bitmap

        byte[] hinhanh = tb.getHinhAnh();

        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        holder.imgAnhTB.setImageBitmap(bitmap);

        int id = tb.getID();
        String tentb = tb.getTenTB();
        String loaitb = tb.getLoaitb();
        String xuatxu = tb.getXuatXu();
        int soluong = tb.getSoLuong();
        byte[] hinhanhedit = tb.getHinhAnh();

        holder.imgBTEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.update(id, tentb, loaitb, hinhanhedit, xuatxu, soluong);
            }
        });

        holder.imgBTDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (muonTra m : new phieuMuonDAO(context).selectAll2()) {
                    if (Integer.parseInt(m.getIDTHIETBI()) == id) {
                        Toast.makeText(context, "THẤT BẠI(FK)!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                new AlertDialog.Builder(context)
                        .setTitle("XÁC NHẬN !")
                        .setMessage("XÁC NHẬN XÓA ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                thietBi t = new thietBi();
                                t.setID(id);
                                boolean result = new thietBiDAO(context).delete(t);
                                if (result) {
                                    Toast.makeText(context, "THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < ThietBi.arrayList.size(); i++) {
                                        if (ThietBi.arrayList.get(i).getID() == id) {
                                            ThietBi.arrayList.remove(i);
                                            break;
                                        }
                                    }
                                    ThietBi.adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "THẤT BẠI!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("NO", null).show();
//                context.dialog_Delete(id);
            }
        });


        return convertView;
    }

    public void filter(String text) {
        //chuyen ve chu thường
        text = text.toLowerCase(Locale.getDefault());
        thietBiList.clear();
        if (text.length() == 0) {
            thietBiList.addAll(arrayThietBi);
        } else {
            for (thietBi tb : arrayThietBi) {
                if (tb.getTenTB().toLowerCase(Locale.getDefault()).contains(text)) {
                    thietBiList.add(tb);
                }
            }
        }
        notifyDataSetChanged();
    }
}
