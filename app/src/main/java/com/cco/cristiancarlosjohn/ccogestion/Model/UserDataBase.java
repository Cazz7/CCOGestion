package com.cco.cristiancarlosjohn.ccogestion.Model;

import android.provider.BaseColumns;

/**
 * Created by cristian.zapata on 15/09/2017.
 */

public class UserDataBase {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserDataBase() {}

    // Table contents
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "DatosLogin";
        public static final String COLUMN_NAME_USER = "usuario";
        public static final String COLUMN_NAME_PASS = "password";
        public static final String COLUMN_NAME_PROFILE = "perfil";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_USER + " TEXT unique," +
                    //FeedEntry.COLUMN_NAME_PASS + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_PROFILE + TEXT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
