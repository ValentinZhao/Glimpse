package com.jyutwaa.zhaoziliang.glimpse.Fragment;

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
    private String currentLoadDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        initWidgets(view);
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
                super.onScrolled(recyclerView, dx, dy);
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

        connectivityCallback = new ConnectivityManager.NetworkCallback(){
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

    /**
     * we load top-stories of Zhihu as the first sight(list) of this fragment
     */
    private void loadZhihuStories() {
        if(mZhihuAdapter.getItemCount() > 0){
            mZhihuAdapter.clearData();
        }
        currentLoadDate = "0";
        mIZhihuPresenterImpl.getLatestDaily();
    }

    /**
     * we load stories of Zhihu by date if users want to load more stories
     */
    private void loadMoreZhihuStories() {
        mZhihuAdapter.loadingStart(); //call notifyItemInserted then we request more stories
        mIZhihuPresenterImpl.getTheDaily(currentLoadDate);
    }

    private void initViewAndLaunch() {
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
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        initViewAndLaunch();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(monitoringConnectivity){
            ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                manager.unregisterNetworkCallback(connectivityCallback);
            }
            monitoringConnectivity = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIZhihuPresenterImpl.unsubscribe();
    }


    /**
     * getLatestDaily() and getTheDaily(ZhihuDaily) both call this method eventually
     * the whole process: users scroll the list to the bottom -> mScrolListener response and set isLoading to true
     *   getMoreDaily() -> adapter.loadingFirst -> getTheDaily(date) ->
     *   updateList(called in onNext) -> call updateList(ZhihuDaily) which is overrided here -> set isLoading to false
     *   update the currentLoadDate and set fresh ZhihuDailyItems to adapter
     */
    @Override
    public void updateList(ZhihuDaily zhihuDaily) {
        if(isLoading){
            isLoading = false;
            mZhihuAdapter.loadingfinish();
        }
        currentLoadDate = zhihuDaily.getDate();
        mZhihuAdapter.addItems(zhihuDaily.getStories());
        //in case what we load cannot fullfill the screen
        if(!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)){
            loadMoreZhihuStories();
        }
    }

    @Override
    public void showProgressbar() {
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressbar() {
        if(progressBar != null){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError(String message) {
        if(recyclerView != null){
            Snackbar.make(recyclerView, "网络异常,请点击重试", Snackbar.LENGTH_SHORT).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentLoadDate.equals("0")){
                        mIZhihuPresenterImpl.getLatestDaily();
                    } else {
                        mIZhihuPresenterImpl.getTheDaily(currentLoadDate);
                    }
                }
            }).show();
        }
    }
}
