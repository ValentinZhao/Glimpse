package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jyutwaa.zhaoziliang.glimpse.Adapter.Bilibili.CoverAdapter;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IBilibiliCoverPresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliIntegratedFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.View.GridItemDividerDecoration;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class CoverFragment extends BaseFragment implements IBilibiliIntegratedFragment {

    View mView;
    IBilibiliCoverPresenterImpl mIBilibiliCoverPresenterImpl;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerView.OnScrollListener mScrollListener;
    CoverAdapter mAdapter;


    RecyclerView rv_content;
    ProgressBar mProgressBar;
    ViewStub vs_no_connection;
    RelativeLayout rl_no_connection;

    boolean isConnected = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkNetworkConnectivity();
        initListeners();
        initViewsAndLaunch();
    }

    @Override
    public void updateList(TopListType topListType) {
        if(topListType != null){
            mAdapter.addItems(topListType.getCover_list().getAllItems());
        }
    }

    @Override
    public void showProgressbar() {
        if(mProgressBar != null){
            mProgressBar.setVisibility(View.VISIBLE);
        }
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

    private void initViewsAndLaunch() {
        mIBilibiliCoverPresenterImpl = new IBilibiliCoverPresenterImpl(getContext(), this);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CoverAdapter(getContext());
        rv_content.setLayoutManager(mLinearLayoutManager);
        rv_content.setHasFixedSize(true);
        rv_content.addOnScrollListener(mScrollListener);
        rv_content.setItemAnimator(new DefaultItemAnimator());
        rv_content.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        rv_content.setAdapter(mAdapter);
        loadCoverTopList();
    }



    private void initListeners() {
        mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    int viewItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastItemCount = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if(viewItemCount + pastItemCount >= totalItemCount){
                        Snackbar.make(mView, "没有更多啦(～￣▽￣)～", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }


    private void checkNetworkConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        isConnected = info != null && info.isConnected();
        if(!isConnected){
            mProgressBar.setVisibility(View.INVISIBLE);
            if(rl_no_connection == null){
                rl_no_connection = (RelativeLayout) vs_no_connection.inflate();
            }
        }
    }

    @Override
    protected int getLayoutIdentifier() {
        return R.layout.fragment_bilibili_cover;
    }

    @Override
    protected void initWidgets() {
        rv_content = (RecyclerView) mView.findViewById(R.id.rv_cover);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_cover);
        vs_no_connection = (ViewStub) mView.findViewById(R.id.vs_cover_no_connection);
    }

    private void loadCoverTopList() {
        if(mAdapter.getItemCount() > 0){
            mAdapter.clearData();
        }
        mIBilibiliCoverPresenterImpl.getCoverTopList();
    }
}
