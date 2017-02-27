package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliIntegratedFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class CoverFragment extends BaseFragment implements IBilibiliIntegratedFragment {
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_bilibili_cover, null);
        return mView;
    }
    @Override
    public void updateList(TopListType topListType) {

    }

    @Override
    public void showProgressbar() {

    }

    @Override
    public void hideProgressbar() {

    }

    @Override
    public void showError(String message) {

    }
}
