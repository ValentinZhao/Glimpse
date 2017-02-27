package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jyutwaa.zhaoziliang.glimpse.Adapter.Bilibili.IntegratedAdapter;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IBilibiliPresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliIntegratedFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.View.GridItemDividerDecoration;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class IntegratedFragment extends BaseFragment implements IBilibiliIntegratedFragment {

    View mView;
    private IBilibiliPresenterImpl mIBilibiliPresenterImpl;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.OnScrollListener mScrollListener;
    private IntegratedAdapter mIntegratedAdapter;
    private ConnectivityManager.NetworkCallback mConnectivityCallBack;


    private RecyclerView rv_content;
    private ProgressBar mProgressBar;
    private ViewStub vs_no_connection;
    private RelativeLayout rl_no_connection;

    private boolean isConnected = false;
    private boolean monitoringConnectivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_bilibili_integrated, null);
        initWidgets();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        checkNetworkConnectivity();
        initViewsAndLaunch();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(monitoringConnectivity){
            ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                manager.unregisterNetworkCallback(mConnectivityCallBack);
            }
            monitoringConnectivity = false;
        }
    }

    @Override
    public void updateList(TopListType topListType) {
        if(topListType != null){
            mIntegratedAdapter.addItems(topListType.getIntegrated_list().getAllItems());
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

    private void initWidgets() {
        rv_content = (RecyclerView) mView.findViewById(R.id.rv_inte);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.progress_inte);
        vs_no_connection = (ViewStub) mView.findViewById(R.id.vs_inte_no_connection);
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

        mConnectivityCallBack = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(Network network) {
                isConnected = true;
            }

            @Override
            public void onLost(Network network) {
                isConnected = false;
            }
        };
    }

    private void checkNetworkConnectivity(){
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            manager.registerNetworkCallback(new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), mConnectivityCallBack);
            monitoringConnectivity = true;
        }
    }

    private void initViewsAndLaunch() {
        mIntegratedAdapter = new IntegratedAdapter(getContext());
        mIBilibiliPresenterImpl = new IBilibiliPresenterImpl(getContext(), this);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        rv_content.setLayoutManager(mLinearLayoutManager);
        rv_content.setHasFixedSize(true);
        rv_content.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        rv_content.setItemAnimator(new DefaultItemAnimator());
        rv_content.setAdapter(mIntegratedAdapter);
        rv_content.addOnScrollListener(mScrollListener);
        loadIntegratedTopList();
    }

    private void loadIntegratedTopList() {
        if(mIntegratedAdapter.getItemCount() > 0){
            mIntegratedAdapter.clearData();
        }
        mIBilibiliPresenterImpl.getIntegratedTopList();
    }
}
