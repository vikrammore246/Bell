package com.example.bell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vikram on 15/9/17.
 */

public class DatabaseHelper2 extends SQLiteOpenHelper {
    public static final String DATABASE_NAMEE = "temptimings2.db";
    public static final String TABLE_NAME2 = "timings2";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "LT1";
    public static final String COL_3 = "LT2";
    public static final String COL_4 = "LT3";
    public static final String COL_5 = "LT4";
    public static final String COL_6 = "LT5";
    public static final String COL_7 = "LT6";
    public static final String COL_8 = "LT7";
    public static final String COL_9 = "LT8";
    public static final String COL_10 = "LT9";
    public static final String COL_11 = "LT10";

    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAMEE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,LT1 TEXT,LT2 TEXT,LT3 TEXT,LT4 TEXT,LT5 TEXT,LT6 TEXT,LT7 TEXT,LT8 TEXT,LT9 TEXT,LT10 TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+TABLE_NAME2);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData2(String lt1,String lt2,String lt3,String lt4,String lt5,String lt6,String lt7,String lt8,String lt9,String lt10){
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL_2,lt1);
        contentValues2.put(COL_3,lt2);
        contentValues2.put(COL_4,lt3);
        contentValues2.put(COL_5,lt4);
        contentValues2.put(COL_6,lt5);
        contentValues2.put(COL_7,lt6);
        contentValues2.put(COL_8,lt7);
        contentValues2.put(COL_9,lt8);
        contentValues2.put(COL_10,lt9);
        contentValues2.put(COL_11,lt10);
        long result2 = db2.insert(TABLE_NAME2, null, contentValues2);
        if (result2 == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData2(){
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor res2 = db2.rawQuery("select * from "+TABLE_NAME2+" where ID=1",null);
        if (res2 != null){
            res2.moveToFirst();
        }
        return res2;
    }

    public boolean updateData2(String id,String lt1,String lt2,String lt3,String lt4,String lt5,String lt6,String lt7,String lt8,String lt9,String lt10){
        SQLiteDatabase db2 = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COL_1,id);
        contentValues2.put(COL_2,lt1);
        contentValues2.put(COL_3,lt2);
        contentValues2.put(COL_4,lt3);
        contentValues2.put(COL_5,lt4);
        contentValues2.put(COL_6,lt5);
        contentValues2.put(COL_7,lt6);
        contentValues2.put(COL_8,lt7);
        contentValues2.put(COL_9,lt8);
        contentValues2.put(COL_10,lt9);
        contentValues2.put(COL_11,lt10);
        db2.update(TABLE_NAME2, contentValues2, "ID = ?", new String[]{id});
        return true;
    }
}
