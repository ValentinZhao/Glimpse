package com.jyutwaa.zhaoziliang.glimpse.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jyutwaa.zhaoziliang.glimpse.Config.Config;

/**
 * Created by zhaoziliang on 17/2/10.
 */

public class DBUtils {
    public static final String CREATE_TABLE_IF_NOT_EXISTS = "create table if not exists %s " +
    "(id integer  primary key autoincrement,key text unique,is_read integer)";

    private static DBUtils mDBUtils;

    private SQLiteDatabase mSQLiteDataBase;

    public DBUtils(Context context){
        mSQLiteDataBase = new DBHelper(context, Config.IS_READ_NAME + ".db").getWritableDatabase();
    }

    public static synchronized DBUtils getDB(Context context){
        if(mDBUtils == null){
            mDBUtils = new DBUtils(context);
        }
        return mDBUtils;
    }

    public void addHasReadInfo(String table, String key, int value){
        Cursor cursor = mSQLiteDataBase.query(table, null, null, null, null, null, "id asc");
        if(cursor.getCount() > 200 && cursor.moveToNext()){
            mSQLiteDataBase.delete(table, "id=?", new String[]{
                    String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))});
        }
        cursor.close();
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", key);
        contentValues.put("is_read", value);
        mSQLiteDataBase.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public boolean isRead(String table, String key, int value){
        Cursor cursor = mSQLiteDataBase.query(table, null, "key=?", new String[]{key}, null, null, null);
        boolean isRead;
        if(cursor.moveToNext() && cursor.getInt(cursor.getColumnIndex("is_read")) == value){
            isRead = true;
        } else {
            isRead = false;
        }
        cursor.close();
        return isRead;
    }
}
