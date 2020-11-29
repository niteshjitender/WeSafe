package com.example.wesafe.UtilService;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Contact.db";
    public static final String TABLE_NAME="EmergencyContact_table";
    public static final String COL_1="ID";
    public static final String COL_2="MOBILENUMBER1";
    public static final String COL_3="MOBILENUMBER2";
    public static final String COL_4="MOBILENUMBER3";
    public static final String COL_5="USERNAME";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,MOBILENUMBER1 TEXT,MOBILENUMBER2 TEXT,MOBILENUMBER3 TEXT,USERNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String mobilenumber1,String mobilenumber2,String mobilenumber3,String username)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,mobilenumber1);
        contentValues.put(COL_3,mobilenumber2);
        contentValues.put(COL_4,mobilenumber3);
        contentValues.put(COL_5,username);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result ==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean updateData(String username,String mobilenumber1,String mobilenumber2,String mobilenumber3)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,mobilenumber1);
        contentValues.put(COL_3,mobilenumber2);
        contentValues.put(COL_4,mobilenumber3);
        contentValues.put(COL_5,username);
        db.update(TABLE_NAME,contentValues,"USERNAME = ?",new String[] {username});
        return true;
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;
    }
}
