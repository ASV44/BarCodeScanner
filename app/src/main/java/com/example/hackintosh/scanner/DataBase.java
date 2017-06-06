package com.example.hackintosh.scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hackintosh on 6/6/17.
 */

public class DataBase {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    private Context context;

    public DataBase(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void addResult(String tableName, String result) {
        if(!checkIfExist(tableName)) {
            dbHelper.addNewReceiversTable(tableName,db);
        }
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.ResultModel.SCANS, result);
        db.insert(tableName, null, values);

        showTable(tableName, DataBaseHelper.ResultModel.SCANS);

    }

    public boolean checkIfExist(String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ tableName +"'", null);

        if(cursor.getCount() <= 0) { return false; }
        else { return true; }
    }

    public void showTable(String tableName, String tableColumn1) {
        if(checkIfExist(tableName)) {
            Cursor cursor = db.rawQuery("select * from " + tableName, null);
            Log.d("Show table","table exist");
            List<String[]> items = new ArrayList<String[]>();
            while (cursor.moveToNext()) {
                String[] item = new String[2];
                item[0] = cursor.getString(
                        cursor.getColumnIndexOrThrow(tableColumn1));
                items.add(0, item);
            }
            cursor.close();

            for(String[] item: items) {
                Log.d("DataBase item", "" + item[0]);
            }

        }
        else {
            Log.d("Show Table", "table doesn't exit");
        }
    }

    public List<String> getResults() {
        if(checkIfExist(DataBaseHelper.ResultModel.TABLE_NAME)) {
            showTable(DataBaseHelper.ResultModel.TABLE_NAME,DataBaseHelper.ResultModel.SCANS);
            Cursor cursor = db.rawQuery("select * from " + DataBaseHelper.ResultModel.TABLE_NAME, null);

            List<String> items = new ArrayList<String>();
            while (cursor.moveToNext()) {
                String item;
                item = cursor.getString(
                        cursor.getColumnIndexOrThrow(DataBaseHelper.ResultModel.SCANS));
                items.add(item);
            }
            cursor.close();

            Log.d("DataBase", "" + items);

            return items;
        }

        return null;
    }

    public void deleteTable(String tableName) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}

