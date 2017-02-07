package com.jyutwaa.zhaoziliang.glimpse.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by zhaoziliang on 17/2/6.
 */

public class SharedPreferenceUtils {

    public static int getNavigationMenuItemId(Context context){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        return mPref.getInt("navi_item_id", -1);
    }

    public static void setNaivigationMenuItemId(Context context, int id){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(context);
        mPref.edit().putInt("navi_item_id", id).commit();
    }
}
