package com.jyutwaa.zhaoziliang.glimpse.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.jyutwaa.zhaoziliang.glimpse.MyApplication;

/**
 * Created by zhaoziliang on 17/2/3.
 */

public class BaseActivity extends AppCompatActivity {

    public static BaseActivity baseActivity;

    private MyApplication myApplication;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        baseActivity = this;
        myApplication = (MyApplication) MyApplication.getContext();
        myApplication.addActivity(baseActivity);
    }


    @Override
    protected void onResume() {
        super.onResume();
        baseActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        baseActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        myApplication.removeActivity(this);
    }
}
