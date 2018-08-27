package com.example.bell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vikram on 23/3/18.
 */

public class Databasehelper4 extends SQLiteOpenHelper {

    public static final String DATABASE_NAMEE = "temptimings4.db";
    public static final String TABLE_NAME4 = "timings4";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "IPADDRESS";

    public Databasehelper4(Context context) {
        super(context, DATABASE_NAMEE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME4 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,IPADDRESS TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+TABLE_NAME4);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData4(String ipaddress){
        SQLiteDatabase db4 = this.getWritableDatabase();
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(COL_2,ipaddress);
        long result4 = db4.insert(TABLE_NAME4, null, contentValues4);
        if (result4 == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData4(){
        SQLiteDatabase db4 = this.getWritableDatabase();
        Cursor res4 = db4.rawQuery("select * from "+TABLE_NAME4+" where ID=1",null);
        if (res4 != null){
            res4.moveToFirst();
        }
        return res4;
    }

    public boolean updateData4(String id,String ipaddress){
        SQLiteDatabase db4 = this.getWritableDatabase();
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(COL_1,id);
        contentValues4.put(COL_2,ipaddress);
        db4.update(TABLE_NAME4, contentValues4, "ID = ?", new String[]{id});
        return true;
    }
}
