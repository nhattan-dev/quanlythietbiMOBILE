package com.example.mid_term.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mid_term.helper.MySQLiteOpenHelper;
import com.example.mid_term.m_interface.CRUD;
import com.example.mid_term.object.thietBi;

import java.util.ArrayList;

public class thietBiDAO extends MySQLiteOpenHelper implements CRUD<thietBi> {
    SQLiteDatabase db;
    Context context;

    public thietBiDAO(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean insert(thietBi tb) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put(THIETBI_ID, tb.getID());
            values.put(THIETBI_HINHANH, tb.getHinhAnh());
            values.put(THIETBI_LOAI, tb.getLoaitb());
            values.put(THIETBI_SOLUONG, tb.getSoLuong());
            values.put(THIETBI_XUATXU, tb.getXuatXu());
            values.put(THIETBI_TENTHIETBI, tb.getTenTB());

            result = (int) db.insert(THIETBI_TABLE, null, values);
            if (result > -1)
                return true;
            else
                return false;
        } catch (Exception e) {
            if (result != -1) {
                delete(tb);
            }
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        Log.e("insert", "" + result);
        return false;
    }

    @Override
    public boolean delete(thietBi thietBi) {
        try {
            db = this.getWritableDatabase();
            int result = db.delete(THIETBI_TABLE, THIETBI_ID + " = " + thietBi.getID(), null);
            return result > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return false;
    }

    @Override
    public boolean edit(thietBi tb) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(THIETBI_ID, tb.getID());
            values.put(THIETBI_HINHANH, tb.getHinhAnh());
            values.put(THIETBI_LOAI, tb.getLoaitb());
            values.put(THIETBI_SOLUONG, tb.getSoLuong());
            values.put(THIETBI_XUATXU, tb.getXuatXu());
            values.put(THIETBI_TENTHIETBI, tb.getTenTB());

            result = (int) db.update(THIETBI_TABLE, values, THIETBI_ID + " = ?", new String[]{tb.getID() + ""});
            if (result > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            if (result != -1) {
                delete(tb);
            }
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        Log.e("update", "" + result);
        return false;
    }

    @Override
    public ArrayList<thietBi> selectAll() {
        ArrayList<thietBi> thietBis = new ArrayList<>();
        try {
            String query = "SELECT " + THIETBI_ID + ", " + THIETBI_TENTHIETBI + ", " + THIETBI_XUATXU + ", " + THIETBI_SOLUONG + ", " + THIETBI_HINHANH + ", " + THIETBI_LOAI + " FROM " + THIETBI_TABLE;
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    thietBi tb = new thietBi();
                    tb.setID(cursor.getInt(0));
                    tb.setTenTB(cursor.getString(1));
                    tb.setXuatXu(cursor.getString(2));
                    tb.setSoLuong(cursor.getInt(3));
                    tb.setHinhAnh(cursor.getBlob(4));
                    tb.setLoaitb(cursor.getString(5));
                    thietBis.add(tb);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
         return thietBis;
    }
}
