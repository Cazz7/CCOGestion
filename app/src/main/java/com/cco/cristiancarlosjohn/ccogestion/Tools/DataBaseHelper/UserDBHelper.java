package com.cco.cristiancarlosjohn.ccogestion.Tools.DataBaseHelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cco.cristiancarlosjohn.ccogestion.Model.UserDataBase;

/**
 * Created by cristian.zapata on 15/09/2017.
 */

public class UserDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UsersData.db";


    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDataBase.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(UserDataBase.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
