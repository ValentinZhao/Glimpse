package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliIntegratedFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class OtakuDanceFragment extends BaseFragment implements IBilibiliIntegratedFragment {

    @Override
    protected int getLayoutIdentifier() {
        return R.layout.fragment_bilibili_otaku;
    }

    @Override
    protected void initWidgets() {

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
