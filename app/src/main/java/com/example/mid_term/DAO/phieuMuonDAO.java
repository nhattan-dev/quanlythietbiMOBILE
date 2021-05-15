package com.example.mid_term.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mid_term.helper.MySQLiteOpenHelper;
import com.example.mid_term.m_interface.CRUD;
import com.example.mid_term.object.muonTra;

import java.util.ArrayList;

public class phieuMuonDAO extends MySQLiteOpenHelper implements CRUD<muonTra> {
    SQLiteDatabase db;

    public phieuMuonDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean insert(muonTra mt) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CHITIETSUDUNG_IDTHIETBI, mt.getIDTHIETBI());
            values.put(CHITIETSUDUNG_IDPHONGHOC, mt.getIDPHONGHOC());
            values.put(CHITIETSUDUNG_SOLUONGMUON, mt.getSoLuong());
            values.put(CHITIETSUDUNG_NGAYSUDUNG, mt.getNgaymuon());

            result = (int) db.insert(CHITIETSUDUNG_TABLE, null, values);
            if (result > -1)
                return true;
            else
                return false;
        } catch (Exception e) {
            if (result != -1) {
                delete(mt);
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
    public boolean delete(muonTra mt) {
        try {
            db = this.getWritableDatabase();
            int result = db.delete(CHITIETSUDUNG_TABLE, CHITIETSUDUNG_ID + " = " + mt.getID(), null);
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
    public boolean edit(muonTra mt) {
        int result = 0;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CHITIETSUDUNG_ID, mt.getID());
            values.put(CHITIETSUDUNG_IDPHONGHOC, mt.getIDPHONGHOC());
            values.put(CHITIETSUDUNG_IDTHIETBI, mt.getIDTHIETBI());
            values.put(CHITIETSUDUNG_SOLUONGMUON, mt.getSoLuong());
            values.put(CHITIETSUDUNG_NGAYSUDUNG, mt.getNgaymuon());

            result = (int) db.update(CHITIETSUDUNG_TABLE, values, CHITIETSUDUNG_ID + " = ?", new String[]{mt.getID() + ""});
            if (result > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            if (result != -1) {
                delete(mt);
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
    public ArrayList<muonTra> selectAll() {
        ArrayList<muonTra> muonTras = new ArrayList<>();
        try {
            String query = "SELECT ID, SOLUONGMUON, NGAYSUDUNG, " +
                    "(SELECT TENTHIETBI " +
                    "FROM THIETBI " +
                    "WHERE CHITIETSUDUNG.IDTHIETBI = THIETBI.ID) AS TENTHIETBI, " +
                    "(SELECT TENPHONG " +
                    "FROM PHONGHOC " +
                    "WHERE CHITIETSUDUNG.IDPHONGHOC = PHONGHOC.ID) AS TENPHONG," +
                    "IDPHONGHOC, IDTHIETBI " +
                    "FROM CHITIETSUDUNG " +
                    "WHERE SOLUONGMUON > 0";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    muonTra mt = new muonTra();

                    mt.setID(cursor.getInt(0));
                    mt.setSoLuong(cursor.getInt(1));
                    mt.setNgaymuon(cursor.getString(2));
                    mt.setTenThietBi(cursor.getString(3));
                    mt.setMaPhong(cursor.getString(4));
                    mt.setIDPHONGHOC(cursor.getString(5));
                    mt.setIDTHIETBI(cursor.getString(6));

                    muonTras.add(mt);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return muonTras;
    }

    public ArrayList<muonTra> selectAll2() {
        ArrayList<muonTra> muonTras = new ArrayList<>();
        try {
            String query = "SELECT ID, SOLUONGMUON, NGAYSUDUNG, " +
                    "(SELECT TENTHIETBI " +
                    "FROM THIETBI " +
                    "WHERE CHITIETSUDUNG.IDTHIETBI = THIETBI.ID) AS TENTHIETBI, " +
                    "(SELECT TENPHONG " +
                    "FROM PHONGHOC " +
                    "WHERE CHITIETSUDUNG.IDPHONGHOC = PHONGHOC.ID) AS TENPHONG," +
                    "IDPHONGHOC, IDTHIETBI " +
                    "FROM CHITIETSUDUNG ";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    muonTra mt = new muonTra();

                    mt.setID(cursor.getInt(0));
                    mt.setSoLuong(cursor.getInt(1));
                    mt.setNgaymuon(cursor.getString(2));
                    mt.setTenThietBi(cursor.getString(3));
                    mt.setMaPhong(cursor.getString(4));
                    mt.setIDPHONGHOC(cursor.getString(5));
                    mt.setIDTHIETBI(cursor.getString(6));

                    muonTras.add(mt);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return muonTras;
    }

    public ArrayList<com.example.mid_term.DTO.muonTra> selectbyMonth(String month, String year) {
        ArrayList<com.example.mid_term.DTO.muonTra> muonTras = new ArrayList<>();
        try {
            String query = "SELECT (SELECT TENTHIETBI " +
                    "               FROM THIETBI " +
                    "               WHERE THIETBI.ID = CHITIETSUDUNG.IDTHIETBI) AS TENTHIETBI, " +
                    "       COUNT(ID) AS SOLAN " +
                    "       FROM CHITIETSUDUNG " +
                    "       WHERE strftime('%m', NGAYSUDUNG) = '" + month + "' AND strftime('%Y', NGAYSUDUNG) = '" + year + "' " +
                    "       GROUP BY IDTHIETBI " +
                    "       ORDER BY COUNT(ID) DESC";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    com.example.mid_term.DTO.muonTra mt = new com.example.mid_term.DTO.muonTra(cursor.getString(0), cursor.getInt(1));
                    muonTras.add(mt);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return muonTras;
    }

    public ArrayList<com.example.mid_term.DTO.muonTra> selectbyChuaTra() {
        ArrayList<com.example.mid_term.DTO.muonTra> muonTras = new ArrayList<>();
        try {
            String query = "SELECT ( SELECT TENTHIETBI " +
                    "               FROM THIETBI " +
                    "               WHERE THIETBI.ID = CHITIETSUDUNG.IDTHIETBI) AS TENTHIETBI, " +
                    "               SUM(SOLUONGMUON) AS SOLUONGMUON " +
                    "       FROM CHITIETSUDUNG " +
                    "       WHERE SOLUONGMUON > 0 " +
                    "       GROUP BY IDTHIETBI";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    com.example.mid_term.DTO.muonTra mt = new com.example.mid_term.DTO.muonTra(cursor.getInt(1), cursor.getString(0));
                    muonTras.add(mt);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return muonTras;
    }

    public ArrayList<com.example.mid_term.DTO.muonTra> selectbyConLai() {
        ArrayList<com.example.mid_term.DTO.muonTra> muonTras = new ArrayList<>();
        try {
            String query = "SELECT SOLUONG, TENTHIETBI FROM THIETBI";
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    com.example.mid_term.DTO.muonTra mt = new com.example.mid_term.DTO.muonTra(cursor.getInt(0), cursor.getString(1));
                    muonTras.add(mt);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return muonTras;
    }

    public com.example.mid_term.DTO.muonTra selectbyThietBi(int ID) {
        com.example.mid_term.DTO.muonTra mt = new com.example.mid_term.DTO.muonTra();
        try {
            String query = "SELECT TENTHIETBI, SOLUONG, " +
                    "               (SELECT SUM(SOLUONGMUON) " +
                    "               FROM CHITIETSUDUNG " +
                    "               WHERE IDTHIETBI = THIETBI.ID) AS DAMUON " +
                    "       FROM THIETBI WHERE ID = " + ID;
            db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                mt.setTenThietBi(cursor.getString(0));
                mt.setSoluong(cursor.getInt(1));
                mt.setDamuon(cursor.getInt(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }
        return mt;
    }
}
