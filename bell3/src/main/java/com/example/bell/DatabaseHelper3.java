package com.example.bell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vikram on 20/3/18.
 */

public class DatabaseHelper3 extends SQLiteOpenHelper {
    public static final String DATABASE_NAMEE = "temptimings3.db";
    public static final String TABLE_NAME3 = "timings3";
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

    public DatabaseHelper3(Context context) {
        super(context, DATABASE_NAMEE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,LT1 TEXT,LT2 TEXT,LT3 TEXT,LT4 TEXT,LT5 TEXT,LT6 TEXT,LT7 TEXT,LT8 TEXT,LT9 TEXT,LT10 TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+TABLE_NAME3);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData3(String lt1,String lt2,String lt3,String lt4,String lt5,String lt6,String lt7,String lt8,String lt9,String lt10){
        SQLiteDatabase db3 = this.getWritableDatabase();
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(COL_2,lt1);
        contentValues3.put(COL_3,lt2);
        contentValues3.put(COL_4,lt3);
        contentValues3.put(COL_5,lt4);
        contentValues3.put(COL_6,lt5);
        contentValues3.put(COL_7,lt6);
        contentValues3.put(COL_8,lt7);
        contentValues3.put(COL_9,lt8);
        contentValues3.put(COL_10,lt9);
        contentValues3.put(COL_11,lt10);
        long result3 = db3.insert(TABLE_NAME3, null, contentValues3);
        if (result3 == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData3(){
        SQLiteDatabase db3 = this.getWritableDatabase();
        Cursor res3 = db3.rawQuery("select * from "+TABLE_NAME3+" where ID=1",null);
        if (res3 != null){
            res3.moveToFirst();
        }
        return res3;
    }

    public boolean updateData3(String id,String lt1,String lt2,String lt3,String lt4,String lt5,String lt6,String lt7,String lt8,String lt9,String lt10){
        SQLiteDatabase db3 = this.getWritableDatabase();
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(COL_1,id);
        contentValues3.put(COL_2,lt1);
        contentValues3.put(COL_3,lt2);
        contentValues3.put(COL_4,lt3);
        contentValues3.put(COL_5,lt4);
        contentValues3.put(COL_6,lt5);
        contentValues3.put(COL_7,lt6);
        contentValues3.put(COL_8,lt7);
        contentValues3.put(COL_9,lt8);
        contentValues3.put(COL_10,lt9);
        contentValues3.put(COL_11,lt10);
        db3.update(TABLE_NAME3, contentValues3, "ID = ?", new String[]{id});
        return true;
    }
}

