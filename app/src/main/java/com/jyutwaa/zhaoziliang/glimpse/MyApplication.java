package com.jyutwaa.zhaoziliang.glimpse;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.jyutwaa.zhaoziliang.glimpse.Activity.BaseActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by zhaoziliang on 17/2/3.
 */

public class MyApplication extends Application {

    public static MyApplication mApplication;

    public static int mainId;

    public static List<BaseActivity> activityList;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static Application getContext(){
        return mApplication;
    }

    public static Context getApplication() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mainId = android.os.Process.myTid();
        activityList = new LinkedList<>();
    }

    public void addActivity(BaseActivity activity){
        activityList.add(activity);
    }

    public void removeActivity(BaseActivity activity){
        activityList.remove(activity);
    }

    public void clearActivityList(){
        ListIterator<BaseActivity> iterator = activityList.listIterator();
        BaseActivity baseActivity;
        while(iterator.hasNext()){
            baseActivity = iterator.next();
            if(baseActivity != null){
                activityList.remove(baseActivity);
                baseActivity.finish();
            }
        }
    }
}
