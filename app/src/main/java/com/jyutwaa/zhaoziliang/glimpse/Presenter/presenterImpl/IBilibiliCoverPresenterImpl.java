package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;
import android.util.Log;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili.CoverFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliCoverPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Utils.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/2/28.
 */

public class IBilibiliCoverPresenterImpl extends BasePresenterImpl implements IBilibiliCoverPresenter{

    Context mContext;
    CoverFragment mIBilibiliCoverFragment;
    CacheUtil mCacheUtils;
    public IBilibiliCoverPresenterImpl(Context mContext, CoverFragment mIBilibiliCoverFragment) {
        this.mContext = mContext;
        this.mIBilibiliCoverFragment = mIBilibiliCoverFragment;
        this.mCacheUtils = CacheUtil.get(mContext);
    }

    @Override
    public void getCoverTopList() {
        Subscription subscription = ApiManager.getInstance().getBilibiliApiService().getTopList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TopListType, TopListType>() {
                    @Override
                    public TopListType call(TopListType topListType) {
                        for(TopListTypeItem item : topListType.getCover_list().getAllItems()){
                            item.setVideoUrl(Config.BILIBILI_VIDEO_BASE_URL + item.getAid());
                            Log.d("BILIBILI", Config.BILIBILI_VIDEO_BASE_URL + item.getAid());
                        }
                        return topListType;
                    }
                }).subscribe(new Observer<TopListType>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBilibiliCoverFragment.hideProgressbar();
                        mIBilibiliCoverFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TopListType topListType) {
                        mIBilibiliCoverFragment.hideProgressbar();
                        mIBilibiliCoverFragment.updateList(topListType);
                    }
                });
        addSubscription(subscription);
    }
}
