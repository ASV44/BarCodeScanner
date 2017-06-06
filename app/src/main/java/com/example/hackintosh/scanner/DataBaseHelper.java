package com.example.hackintosh.scanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by hackintosh on 6/6/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Scan.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static class ResultModel implements BaseColumns {
        public static final String TABLE_NAME = "result";
        public static final String SCANS = "scans";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNewReceiversTable(String tableName, SQLiteDatabase db) {
        String table = "CREATE TABLE " + tableName + " (" +
                ResultModel._ID + " INTEGER PRIMARY KEY," +
                ResultModel.SCANS + " TEXT)";

        db.execSQL(table);
    }
}
