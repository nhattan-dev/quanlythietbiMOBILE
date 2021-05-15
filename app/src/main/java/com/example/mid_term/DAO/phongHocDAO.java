package com.example.mid_term.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mid_term.helper.MySQLiteOpenHelper;
import com.example.mid_term.m_interface.CRUD;
import com.example.mid_term.object.phongHoc;

import java.util.ArrayList;

public class phongHocDAO extends MySQLiteOpenHelper implements CRUD<phongHoc> {
    SQLiteDatabase db;

    public phongHocDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean insert(phongHoc ph) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PHONGHOC_LOAIPHONG, ph.getLoaiPhong());
            values.put(PHONGHOC_TANG, Integer.parseInt(ph.getTang()));
            values.put(PHONGHOC_TENPHONG, ph.getTenPhong());

            result = (int) db.insert(PHONGHOC_TABLE, null, values);
            if (result > -1)
                return true;
            else
                return false;
        } catch (Exception e) {
            if (result != -1) {
                delete(ph);
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
    public boolean delete(phongHoc phongHoc) {
        try {
            db = this.getWritableDatabase();
            int result = db.delete(PHONGHOC_TABLE, PHONGHOC_ID + " = " + phongHoc.getID(), null);
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
    public boolean edit(phongHoc ph) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PHONGHOC_ID, ph.getID());
            values.put(PHONGHOC_TENPHONG, ph.getTenPhong());
            values.put(PHONGHOC_TANG, ph.getTang());
            values.put(PHONGHOC_LOAIPHONG, ph.getLoaiPhong());

            result = (int) db.update(PHONGHOC_TABLE, values, PHONGHOC_ID + " = ?", new String[]{ph.getID() + ""});
            if (result > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            if (result != -1) {
                delete(ph);
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
    public ArrayList<phongHoc> selectAll() {
        ArrayList<phongHoc> phongHocs = new ArrayList<>();
        try {
            String query = "SELECT " + PHONGHOC_ID + ", " + PHONGHOC_LOAIPHONG + ", " + PHONGHOC_TANG + ", " + PHONGHOC_TENPHONG + " FROM " + PHONGHOC_TABLE;
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    phongHoc ph = new phongHoc();
                    ph.setID(cursor.getInt(0));
                    ph.setLoaiPhong(cursor.getString(1));
                    ph.setTang(cursor.getInt(2)+"");
                    ph.setTenPhong(cursor.getString(3));
                    phongHocs.add(ph);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return phongHocs;
    }
}
