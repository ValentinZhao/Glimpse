package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IBilibiliEsportPresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliEsportFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class EsportFragment extends BaseFragment implements IBilibiliEsportFragment {

    RecyclerView rv_content;
    ProgressBar mProgressBar;
    ViewStub vs_no_connection;

    IBilibiliEsportPresenterImpl mIBilibiliEsportPresenterImpl;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView.OnScrollListener mScrollListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        checkNetworkConnectivity();
        initViewsAndLaunch();
    }

    @Override
    public void updateList(TopListType topListType) {

    }

    @Override
    public void showProgressbar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        if(rv_content != null){
            Snackbar.make(rv_content, "网络异常", Snackbar.LENGTH_SHORT).show();
        }
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


    private void initViewsAndLaunch() {

    }

    private void checkNetworkConnectivity() {

    }

    private void initListeners() {

    }
}
