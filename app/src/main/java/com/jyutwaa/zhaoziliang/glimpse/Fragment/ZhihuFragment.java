package com.jyutwaa.zhaoziliang.glimpse.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jyutwaa.zhaoziliang.glimpse.Adapter.ZhihuAdapter;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDaily;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IZhihuPresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IZhihuFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.View.GridItemDividerDecoration;

/**
 * Created by zhaoziliang on 17/2/7.
 */

public class ZhihuFragment extends BaseFragment implements IZhihuFragment{

    private IZhihuPresenterImpl mIZhihuPresenterImpl;
    private ZhihuAdapter mZhihuAdapter;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ViewStub vs_noConnection;
    private RelativeLayout rl_noConnection;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.OnScrollListener mScrollListener;

    private boolean isConnected;
    private boolean isLoading;
    private boolean monitoringConnectivity;
    private ConnectivityManager.NetworkCallback connectivityCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        initWidgets(view);
        initListeners();
        initView();
        checkNetowrkAvailability();
        return view;
    }

    private void initListeners() {
        mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastItemCount = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if(!isLoading && (visibleItemCount + pastItemCount) >= totalItemCount){
                        isLoading = true;
                        loadMoreZhihuStories();
                    }
                }
            }
        };
    }

    private void loadZhihuStories() {

    }

    private void loadMoreZhihuStories() {

    }

    private void initView() {
        mIZhihuPresenterImpl = new IZhihuPresenterImpl(getContext(), this);
        mZhihuAdapter = new ZhihuAdapter(getContext());
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mZhihuAdapter);
        recyclerView.addOnScrollListener(mScrollListener);
        if(isConnected){
            loadZhihuStories();
        }
    }

    private void checkNetowrkAvailability() {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        isConnected = info.isConnected() && info != null;
        if(!isConnected && progressBar != null){
            progressBar.setVisibility(View.INVISIBLE);
            if(rl_noConnection == null){
                rl_noConnection = (RelativeLayout) vs_noConnection.inflate();
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            manager.registerNetworkCallback(new NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), connectivityCallback);
            monitoringConnectivity = true;
        }
    }

    private void initWidgets(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_zhihu);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_zhihu);
        vs_noConnection = (ViewStub) view.findViewById(R.id.vs_no_connection);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void updateList(ZhihuDaily zhihuDaily) {

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
