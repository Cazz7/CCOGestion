package com.cco.cristiancarlosjohn.ccogestion.Tools.DataBaseHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.UnicodeSetSpanner;
import android.widget.Toast;

import com.cco.cristiancarlosjohn.ccogestion.Model.UserDataBase;

import java.util.ArrayList;

/**
 * Created by cristian.zapata on 15/09/2017.
 */

public class UserDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UsersData.db";
    private static String user;
    private static String profile;

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDataBase.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserDataBase.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertUser(String user, String profile){
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + UserDataBase.FeedEntry.TABLE_NAME); //Borrar usuario en cada login nuevo
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserDataBase.FeedEntry.COLUMN_NAME_USER, user);
            contentValues.put(UserDataBase.FeedEntry.COLUMN_NAME_PROFILE, profile);
            long newRowId = db.insert(UserDataBase.FeedEntry.TABLE_NAME, null, contentValues);
            db.close();
            if (newRowId != -1) return true;
            return false;
        }
        catch (Exception ex)
        {
            String hola = ex.getMessage().toString();
            return false;
        }
    }

    private void selectData()
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM " + UserDataBase.FeedEntry.TABLE_NAME, null);
            //res.moveToNext(); //Sólo habrá un registro
            if (res.getCount() > 0){
                while (res.moveToNext()) {
                    user = res.getString(1);
                    profile = res.getString(2);
                }
            }

            db.close();
        }
        catch (Exception ex){

        }

    }

    public String getUser(){
        selectData();
        return user;
    }

    public String getProfile(){
        selectData();
        return profile;
    }

}
