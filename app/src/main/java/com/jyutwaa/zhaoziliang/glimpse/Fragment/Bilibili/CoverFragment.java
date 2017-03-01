package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IBilibiliCoverPresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliIntegratedFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class CoverFragment extends BaseFragment implements IBilibiliIntegratedFragment {

    View mView;
    IBilibiliCoverPresenterImpl mIBilibiliCoverPresenterImpl;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView.OnScrollListener mScrollListener;


    RecyclerView rv_content;
    ProgressBar mProgressBar;
    ViewStub vs_no_connection;

    boolean isConnected = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_bilibili_cover, null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets();
        checkNetworkConnectivity();
        initViewsAndLaunch();
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

    private void initViewsAndLaunch() {

    }

    private void checkNetworkConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        isConnected = info != null && info.isConnected();
        if(!isConnected){

        }
    }

    private void initWidgets() {
        rv_content = (RecyclerView) mView.findViewById(R.id.rv_cover);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_cover);
        vs_no_connection = (ViewStub) mView.findViewById(R.id.vs_cover_no_connection);
    }
}
