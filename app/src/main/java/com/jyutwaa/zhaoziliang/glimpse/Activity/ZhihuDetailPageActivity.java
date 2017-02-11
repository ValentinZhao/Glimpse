package com.jyutwaa.zhaoziliang.glimpse.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ListView mListView = (ListView) findViewById(R.id.listView);
        for (int i = 0; i <= 30; i++) {
            list.add("测试数据" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ZhihuDetailPageActivity.this, android.R.layout.simple_list_item_1, list){

            @Override
            public int getCount() {
                return super.getCount();
            }

            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {

                System.out.println("getView");
                return super.getView(position, convertView, parent);
            }

        };
        mListView.setAdapter(adapter);



    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showDetailPage(ZhihuDetailPage zhihuDetailPage) {

    }
}
