package com.jyutwaa.zhaoziliang.glimpse.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoziliang on 17/2/11.
 */

public class ZhihuDetailPageActivity extends BaseDetailPageActivity implements IZhihuDetailPage{

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_detail_layout);
        getWindow().setStatusBarColor(getResources().getColor(R.color.immersive_bars));


    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showDetailPage(ZhihuDetailPage zhihuDetailPage) {

    }
}
