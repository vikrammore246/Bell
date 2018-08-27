package com.example.bell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vikram on 1/4/18.
 */

public class DatabaseHelper5 extends SQLiteOpenHelper {

    public static final String DATABASE_NAMEE = "temptimings5.db";
    public static final String TABLE_NAME5 = "password";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "IPPASSWORD";

    public DatabaseHelper5(Context context) {
        super(context, DATABASE_NAMEE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME5 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,IPPASSWORD TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+TABLE_NAME5);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData5(String ippassword){
        SQLiteDatabase db5 = this.getWritableDatabase();
        ContentValues contentValues5 = new ContentValues();
        contentValues5.put(COL_2,ippassword);
        long result5 = db5.insert(TABLE_NAME5, null, contentValues5);
        if (result5 == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData5(){
        SQLiteDatabase db5 = this.getWritableDatabase();
        Cursor res5 = db5.rawQuery("select * from "+TABLE_NAME5+" where ID=1",null);
        if (res5 != null){
            res5.moveToFirst();
        }
        return res5;
    }

    public boolean updateData5(String id,String ippassword){
        SQLiteDatabase db5 = this.getWritableDatabase();
        ContentValues contentValues5 = new ContentValues();
        contentValues5.put(COL_1,id);
        contentValues5.put(COL_2,ippassword);
        db5.update(TABLE_NAME5, contentValues5, "ID = ?", new String[]{id});
        return true;
    }
}
