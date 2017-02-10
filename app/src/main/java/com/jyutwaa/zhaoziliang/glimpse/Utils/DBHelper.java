package com.jyutwaa.zhaoziliang.glimpse.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jyutwaa.zhaoziliang.glimpse.Config.Config;

/**
 * Created by zhaoziliang on 17/2/10.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(DBUtils.CREATE_TABLE_IF_NOT_EXISTS, Config.ZHIHU));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
