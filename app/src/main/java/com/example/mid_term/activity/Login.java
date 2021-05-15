package com.example.mid_term.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mid_term.R;

public class Login extends AppCompatActivity {
    TextView lg_username, lg_password, lg_danhnhap;
    private final static String USERNAME = "admin";
    private final static String PASSWORD = "123";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        this.context = this;
        setControl();
        setEvent();
    }

    private void setEvent() {
        lg_danhnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = lg_username.getText().toString();
                String password = lg_password.getText().toString();
                if(username.equalsIgnoreCase("")){
                    Toast.makeText(context, "Tên đăng nhập không được để trống!", Toast.LENGTH_SHORT).show();
                }
                if(password.equalsIgnoreCase("")){
                    Toast.makeText(context, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                }
                if(username.equals(USERNAME) && password.equals(PASSWORD)){
                    Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl() {
        lg_danhnhap = findViewById(R.id.lg_dangnhap);
        lg_username = findViewById(R.id.lg_username);
        lg_password = findViewById(R.id.lg_password);
    }
}