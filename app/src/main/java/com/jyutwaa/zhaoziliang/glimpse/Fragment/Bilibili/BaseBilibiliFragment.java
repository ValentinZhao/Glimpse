package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;

/**
 * Created by zhaoziliang on 17/3/2.
 */

public abstract class BaseBilibiliFragment extends BaseFragment {

    public LinearLayoutManager mLinearLayoutManager;
    public RecyclerView.OnScrollListener mScrollListener;

    public RecyclerView rv_content;
    public ProgressBar mProgressBar;
    public ViewStub vs_no_connection;
    public RelativeLayout rl_no_connection;

    boolean isConnected = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        checkNetworkConnectivity();
        launchFragment();
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

                    if(viewItemCount + pastItemCount >= totalItemCount){//have reached the bottom
                        Snackbar.make(mView, "没有更多啦(～￣▽￣)～", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    private void checkNetworkConnectivity() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null){
            isConnected = info.isConnected() && info != null;
        }
        if(!isConnected && mProgressBar != null){
            mProgressBar.setVisibility(View.INVISIBLE);
            if(rl_no_connection == null){
                rl_no_connection = (RelativeLayout) vs_no_connection.inflate();
            }
        }
    }

    protected abstract void launchFragment();
}
