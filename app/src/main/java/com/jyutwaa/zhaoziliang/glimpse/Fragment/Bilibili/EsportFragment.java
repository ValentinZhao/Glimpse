package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliEsportFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class EsportFragment extends BaseFragment implements IBilibiliEsportFragment {

    RecyclerView rv_content;
    ProgressBar mProgressBar;
    ViewStub vs_no_connection;


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

    @Override
    protected int getLayoutIdentifier() {
        return R.layout.fragment_bilibili_esport;
    }

    @Override
    protected void initWidgets() {
        rv_content = (RecyclerView) mView.findViewById(R.id.rv_esport);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_esport);
        vs_no_connection = (ViewStub) mView.findViewById(R.id.vs_esport_no_connection);
    }
}
