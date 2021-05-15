package com.example.mid_term.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mid_term.DAO.thietBiDAO;
import com.example.mid_term.R;
import com.example.mid_term.adapter.LoaiTbAdapter;
import com.example.mid_term.object.LoaiThietBi;
import com.example.mid_term.object.thietBi;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ThietBiCapNhat extends AppCompatActivity {
    public static com.example.mid_term.object.thietBi thietBi;
    EditText edtTenTb, edtXuatXu, edtSoluong;
    Spinner spLoaiTb;
    ImageView imgHinhAnh, imgbtCamera, imgbtFolder;
    TextView btnUpdate, btnHuyEdit;

    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;

    LoaiTbAdapter adapter;
    ArrayList<LoaiThietBi> loaiThietBiArrayList;

    LoaiTbAdapter spAdapter;
    ArrayList<LoaiThietBi> arrayList;
    com.example.mid_term.DAO.thietBiDAO thietBiDAO;
    int id;
    String loaitb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi_cap_nhat);
        setTitle("CẬP NHẬT THIẾT BỊ");

        thietBiDAO = new thietBiDAO(this);

        setControl();
        setData();
        setEvent();
    }

    private void setEvent() {
        imgbtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ThietBiCapNhat.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA
                );
            }
        });

        // folder click
        imgbtFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ThietBiCapNhat.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });

        spLoaiTb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaitb = loaiThietBiArrayList.get(position).getLoaiTb();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // button update click
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyen hinh tu bitmap sang byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinhAnh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArray);
                byte[] hinhanhbyte = byteArray.toByteArray();

                byte[] hinh = hinhanhbyte.clone();
                if (edtTenTb.getText().toString().equals("")) {
                    Toast.makeText(ThietBiCapNhat.this, "Tên thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (loaitb.toString().trim().equals("")) {
                    Toast.makeText(ThietBiCapNhat.this, "Loai thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (edtXuatXu.getText().toString().trim().equals("")) {
                    Toast.makeText(ThietBiCapNhat.this, "Xuất xứ thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (hinhanhbyte.length == 0) {
                    Toast.makeText(ThietBiCapNhat.this, "Hình Ảnh thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else {
                    int soluongedit = Integer.valueOf(edtSoluong.getText().toString());

                    // update du lieu
                    thietBi tb = new thietBi();
                    tb.setID(thietBi.getID());
                    tb.setXuatXu(edtXuatXu.getText().toString().trim());
                    tb.setSoLuong(soluongedit);
                    tb.setTenTB(edtTenTb.getText().toString().trim());
                    tb.setLoaitb(loaitb);
                    tb.setHinhAnh(hinhanhbyte);
                    boolean result = thietBiDAO.edit(tb);
                    if (result) {
                        Toast.makeText(ThietBiCapNhat.this, "THÀNH CÔNG!", Toast.LENGTH_SHORT).show();
                        while (ThietBi.arrayList.size() > 0) {
                            ThietBi.arrayList.remove(0);
                        }
                        for (thietBi t : thietBiDAO.selectAll()) {
                            ThietBi.arrayList.add(t);
                        }
                        ThietBi.adapter.notifyDataSetChanged();
                        onBackPressed();
                    } else {
                        Toast.makeText(ThietBiCapNhat.this, "THẤT BẠI!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //button huy click
        btnHuyEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setData() {
        String tentb = thietBi.getTenTB();
        String loaitb = thietBi.getLoaitb();
        byte[] hinhanh = thietBi.getHinhAnh();
        String xuatxu = thietBi.getXuatXu();
        id = thietBi.getID();
        int soluong = thietBi.getSoLuong();

        // gán giá trị cho cá edittext
        edtTenTb.setText(tentb);
        edtXuatXu.setText(xuatxu);


        loaiThietBiArrayList = new ArrayList<>();
        loaiThietBiArrayList.add(new LoaiThietBi(1, "New"));
        loaiThietBiArrayList.add(new LoaiThietBi(2, "Old"));

        spAdapter = new LoaiTbAdapter(ThietBiCapNhat.this, R.layout.dong_loai_thiet_bi, loaiThietBiArrayList);
        spLoaiTb.setAdapter(spAdapter);
        if (loaitb.equalsIgnoreCase("New")){
            spLoaiTb.setSelection(0);
        }else {
            spLoaiTb.setSelection(1);
        }
//        spLoaiTb.setText(loaitb);

        String sol = String.valueOf(soluong);

        edtSoluong.setText(sol);

        //chuyen du lieu tu byte sang bitmap roi gan vao imgHinhAnh
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        imgHinhAnh.setImageBitmap(bitmap);
    }

    private void setControl() {

        edtTenTb = findViewById(R.id.editTextTextTenThietBiEdit);
        edtXuatXu = findViewById(R.id.editTextTextXuatXuEdit);
        spLoaiTb = findViewById(R.id.editTextTextLoaiThietBiEdit);
        edtSoluong = findViewById(R.id.editTextSoLuongEdit);

        imgHinhAnh = findViewById(R.id.imageViewHinhAnhEdit);

        imgbtCamera = findViewById(R.id.imageButtonCameraEdit);
        imgbtFolder = findViewById(R.id.imageButtonFolderEdit);

        btnUpdate = findViewById(R.id.buttonUpdateTB);
        btnHuyEdit = findViewById(R.id.buttonHuyEdit);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(this, "Bạn không cho phép mở camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDER);
                } else {
                    Toast.makeText(this, "Bạn không cho phép try cập thư viện ảnh", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // do hinh ra imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhAnh.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhAnh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}