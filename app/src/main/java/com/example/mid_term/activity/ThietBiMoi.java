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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mid_term.DAO.thietBiDAO;
import com.example.mid_term.R;
import com.example.mid_term.adapter.LoaiTbAdapter;
import com.example.mid_term.object.LoaiThietBi;
import com.example.mid_term.object.thietBi;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ThietBiMoi extends AppCompatActivity {

    EditText edtTenTB, edtIdLoai, edtXuatXu, edtSoLuong;
    ImageView imgbtCameraTb, imgbtFolderTb, imgHinhAnhTB;
    TextView btnThemTB, btnHuyTB;
    Spinner spLoaiTB;

    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;

    LoaiTbAdapter adapter;
    ArrayList<LoaiThietBi> loaiThietBiArrayList;

    String loaitb;

    com.example.mid_term.DAO.thietBiDAO thietBiDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_bi_moi);
        setTitle("THÊM THIẾT BỊ");
        thietBiDAO = new thietBiDAO(this);


        setControl();
        setData();
        setEvent();
    }

    private void setData() {
        loaiThietBiArrayList = new ArrayList<>();
        loaiThietBiArrayList.add(new LoaiThietBi(1, "New"));
        loaiThietBiArrayList.add(new LoaiThietBi(2, "Old"));

        adapter = new LoaiTbAdapter(ThietBiMoi.this, R.layout.dong_loai_thiet_bi, loaiThietBiArrayList);
        spLoaiTB.setAdapter(adapter);
    }

    private void setEvent() {
        imgbtCameraTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ThietBiMoi.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA
                );
            }
        });

        imgbtFolderTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ThietBiMoi.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });

        spLoaiTB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaitb = loaiThietBiArrayList.get(position).getLoaiTb();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnHuyTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnThemTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyen data image -> byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinhAnhTB.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArray);
                byte[] hinhanh = byteArray.toByteArray();

                if (edtTenTB.getText().toString().equals("")) {
                    Toast.makeText(ThietBiMoi.this, "Tên thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (loaitb.toString().trim().equals("")) {
                    Toast.makeText(ThietBiMoi.this, "Loai thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (edtXuatXu.getText().toString().trim().equals("")) {
                    Toast.makeText(ThietBiMoi.this, "Xuất xứ thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else if (hinhanh.length == 0) {
                    Toast.makeText(ThietBiMoi.this, "Hình Ảnh thiết bị không được rỗng", Toast.LENGTH_SHORT).show();
                } else {

                    int sl = Integer.valueOf(edtSoLuong.getText().toString().trim());

                    thietBi tb = new thietBi();
                    tb.setHinhAnh(hinhanh);
                    tb.setTenTB(edtTenTB.getText().toString().trim());
                    tb.setLoaitb(loaitb);
                    tb.setSoLuong(sl);
                    tb.setXuatXu(edtXuatXu.getText().toString().trim());

                    boolean result = thietBiDAO.insert(tb);
                    if (result) {

                        Toast.makeText(ThietBiMoi.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        while (ThietBi.arrayList.size() > 0) {
                            ThietBi.arrayList.remove(0);
                        }
                        for (thietBi t : thietBiDAO.selectAll()) {
                            ThietBi.arrayList.add(t);
                        }
                        ThietBi.adapter.notifyDataSetChanged();
                        onBackPressed();
                    } else {
                        Toast.makeText(ThietBiMoi.this, "SOMETHING WAS WRONG", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void setControl() {
        imgbtCameraTb = findViewById(R.id.imageButtonCameraHinhAnh);
        imgbtFolderTb = findViewById(R.id.imageButtonFolderHinhAnh);

        btnThemTB = findViewById(R.id.buttonThemThietbi);
        btnHuyTB = findViewById(R.id.buttonHuyThietBi);

        imgHinhAnhTB = (ImageView) findViewById(R.id.imageViewHinhAnh);

        edtTenTB = (EditText) findViewById(R.id.editTextTenThietBi);
//        edtIdLoai = (EditText) findViewById(R.id.editTextLoaiThietBi);
        edtXuatXu = (EditText) findViewById(R.id.editTextXuatXu);
        edtSoLuong = (EditText) findViewById(R.id.editTextSoLuongThietBi);

        spLoaiTB = (Spinner) findViewById(R.id.spinnerLoaiThietBi);
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
            imgHinhAnhTB.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhAnhTB.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}