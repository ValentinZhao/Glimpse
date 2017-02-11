package com.jyutwaa.zhaoziliang.glimpse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.jyutwaa.zhaoziliang.glimpse.MyApplication;
import com.jyutwaa.zhaoziliang.glimpse.R;

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
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.animator.slide_right_in, R.animator.slide_remain);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.animator.slide_right_out);
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
