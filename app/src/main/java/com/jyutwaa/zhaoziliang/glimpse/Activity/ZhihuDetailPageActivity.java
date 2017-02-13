package com.jyutwaa.zhaoziliang.glimpse.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IZhihuDetailPagePresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.ParallaxScrimageView;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.TranslateYTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoziliang on 17/2/11.
 */

public class ZhihuDetailPageActivity extends BaseDetailPageActivity implements IZhihuDetailPage{

    private static final float SCRIM_ADJUSTMENT = 0.075f;

    private List<String> list = new ArrayList<>();
    private String id;
    private String title;
    private IZhihuDetailPagePresenterImpl mIZhihuDetailPagePresenterImpl;

    private ParallaxScrimageView mShot;
    private TranslateYTextView mTitle;
    private Toolbar mToolbar;
    private NestedScrollView mNestedView;
    private WebView mWebView;
    private NestedScrollView.OnScrollChangeListener mScrollChangeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_detail_layout);
        getWindow().setStatusBarColor(getResources().getColor(R.color.immersive_bars));
        initWidgets();
        initViews();
        initData();
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        mIZhihuDetailPagePresenterImpl = new IZhihuDetailPagePresenterImpl(this);
        mNestedView.setOnScrollChangeListener(mScrollChangeListener);
    }

    private void initViews() {

    }

    private void initWidgets() {
        mShot = (ParallaxScrimageView) findViewById(R.id.zhihu_detail_shot);
        mTitle = (TranslateYTextView) findViewById(R.id.zhihu_detail_title);
        mToolbar = (Toolbar) findViewById(R.id.zhihu_detail_toolbar);
        mNestedView = (NestedScrollView) findViewById(R.id.zhihu_detail_nest);
        mWebView = (WebView) findViewById(R.id.zhihu_detail_web);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showDetailPage(ZhihuDetailPage zhihuDetailPage) {

    }
}
