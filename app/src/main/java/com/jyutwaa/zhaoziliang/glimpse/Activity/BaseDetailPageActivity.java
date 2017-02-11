package com.jyutwaa.zhaoziliang.glimpse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.SwipeBackLayout;

/**
 * Created by zhaoziliang on 17/2/11.
 */

public class BaseDetailPageActivity extends AppCompatActivity {

    SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = (SwipeBackLayout) View.inflate(this, R.layout.swipe_back_layout, null);
        mSwipeBackLayout.attachToActivity(this);
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
}