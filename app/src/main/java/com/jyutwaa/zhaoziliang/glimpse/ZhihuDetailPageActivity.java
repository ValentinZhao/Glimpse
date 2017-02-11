package com.jyutwaa.zhaoziliang.glimpse;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jyutwaa.zhaoziliang.glimpse.Activity.BaseDetailPageActivity;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IZhihuDetailPage;

/**
 * Created by zhaoziliang on 17/2/11.
 */

public class ZhihuDetailPageActivity extends BaseDetailPageActivity implements IZhihuDetailPage{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_detail_layout);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showDetailPage(ZhihuDetailPage zhihuDetailPage) {

    }
}
