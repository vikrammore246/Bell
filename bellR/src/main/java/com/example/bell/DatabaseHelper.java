package com.example.bell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 22/2/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "stringlist.db";
    public static final String TABLE_NAME = "stringtable2";
    public static final String TABLE_NAME2 = "timings2";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "STRINGS";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "TIME";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,STRINGS TEXT,DATE TEXT,TIME TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String strings, String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,strings);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,time);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }



    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        if (res != null){
            res.moveToFirst();
        }
        return res;
    }

    public List<ListItem>getDataInList(){
        List<ListItem> modelList = new ArrayList<ListItem>();
        String query = "select * from "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                ListItem model = new ListItem();
                model.setHead(cursor.getString(1));
                model.setDate(cursor.getString(2));
                model.setTime(cursor.getString(3));

                modelList.add(model);
            }while (cursor.moveToNext());
        }

        return modelList;

    }



    public boolean updateData(String id,String strings,String date,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,strings);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,time);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_NAME);
        //db.execSQL("TRUNCATE table " + TABLE_NAME);
        db.close();
    }


}
