package com.example.mid_term.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "QLTB.sqlite";

    private final static int VERSION = 1;
    private Context context;

    protected final static String PHONGHOC_TABLE = "PHONGHOC";
    protected final static String PHONGHOC_ID = "ID";
    protected final static String PHONGHOC_LOAIPHONG = "LOAIPHONG";
    protected final static String PHONGHOC_TANG = "TANG";
    protected final static String PHONGHOC_TENPHONG = "TENPHONG";

    protected final static String THIETBI_TABLE = "THIETBI";
    protected final static String THIETBI_ID = "ID";
    protected final static String THIETBI_TENTHIETBI = "TENTHIETBI";
    protected final static String THIETBI_XUATXU = "XUATXU";
    protected final static String THIETBI_SOLUONG = "SOLUONG";
    protected final static String THIETBI_LOAI = "LOAI";
    protected final static String THIETBI_HINHANH = "HINHANH";

    protected final static String CHITIETSUDUNG_TABLE = "CHITIETSUDUNG";
    protected final static String CHITIETSUDUNG_ID = "ID";
    protected final static String CHITIETSUDUNG_IDTHIETBI = "IDTHIETBI";
    protected final static String CHITIETSUDUNG_IDPHONGHOC = "IDPHONGHOC";
    protected final static String CHITIETSUDUNG_SOLUONGMUON = "SOLUONGMUON";
    protected final static String CHITIETSUDUNG_NGAYSUDUNG = "NGAYSUDUNG";

    private final static String CREATE_PHONGHOC_TABLE_QUERY = "CREATE TABLE PHONGHOC ( " +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "LOAIPHONG TEXT NOT NULL, " +
            "TANG INTEGER NOT NULL, " +
            "TENPHONG TEXT NOT NULL )";

    private final static String CREATE_THIETBI_TABLE_QUERY = "CREATE TABLE THIETBI ( " +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TENTHIETBI TEXT NOT NULL, " +
            "XUATXU TEXT NOT NULL, " +
            "HINHANH BLOB NOT NULL, " +
            "LOAI TEXT NOT NULL, " +
            "SOLUONG INTEGER CHECK (SOLUONG >= 0) NOT NULL )";

    private final static String CREATE_QUANLYMUONTRA_TABLE_QUERY = "CREATE TABLE CHITIETSUDUNG ( " +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "IDTHIETBI INTEGER REFERENCES THIETBI (ID), " +
            "IDPHONGHOC INTEGER REFERENCES PHONGHOC (ID), " +
            "SOLUONGMUON INTEGER CHECK (SOLUONGMUON >= 0), " +
            "NGAYSUDUNG TEXT NOT NULL )";

    private final static String CREATE_TRIGGER_QUANLYMUONTRA_INSERT = "CREATE TRIGGER tg_INSERT_AFTER AFTER INSERT " +
            "ON CHITIETSUDUNG " +
            "BEGIN " +
            "UPDATE THIETBI SET SOLUONG = SOLUONG - NEW.SOLUONGMUON " +
            "WHERE THIETBI.ID = NEW.IDTHIETBI;" +
            "END;";

    private final static String CREATE_TRIGGER_QUANLYMUONTRA_UPDATE = "CREATE TRIGGER tg_UPDATE_AFTER AFTER UPDATE " +
            "ON CHITIETSUDUNG " +
            "BEGIN " +
            "UPDATE THIETBI SET SOLUONG = SOLUONG - NEW.SOLUONGMUON + OLD.SOLUONGMUON " +
            "WHERE THIETBI.ID = NEW.IDTHIETBI;" +
            "END;";

    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_THIETBI_TABLE_QUERY);
        db.execSQL(CREATE_PHONGHOC_TABLE_QUERY);
        db.execSQL(CREATE_QUANLYMUONTRA_TABLE_QUERY);
        db.execSQL(CREATE_TRIGGER_QUANLYMUONTRA_INSERT);
        db.execSQL(CREATE_TRIGGER_QUANLYMUONTRA_UPDATE);
        Log.e("OnCreate()","");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void execQuery(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getCursor(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
