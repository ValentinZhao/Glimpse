package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliFragment;
import com.jyutwaa.zhaoziliang.glimpse.Utils.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/2/24.
 */

public class IBilibiliPresenterImpl extends BasePresenterImpl implements IBilibiliPresenter{

    IBilibiliFragment mIBlibiliFragment;
    Context mContext;
    CacheUtil mCacheUtils;
    public IBilibiliPresenterImpl(Context context, IBilibiliFragment mIBlibiliFragment) {
        mContext = context;
        this.mIBlibiliFragment = mIBlibiliFragment;
        mCacheUtils = CacheUtil.get(context);
    }

    @Override
    public void getIntegratedTopList() {
        Subscription subscription = ApiManager.getInstance().getBilibiliApiService().getTopList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TopListType, TopListType>() {
                    @Override
                    public TopListType call(TopListType topListType) {
                        for(TopListTypeItem item : topListType.getIntegrated_list()){
                            item.setVideoUrl(Config.BILIBILI_VIDEO_BASE_URL + item.getAid());
                        }
                        return topListType;
                    }
                }).subscribe(new Observer<TopListType>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBlibiliFragment.hideProgressbar();
                        mIBlibiliFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TopListType topListType) {
                        mIBlibiliFragment.hideProgressbar();
                        mIBlibiliFragment.updateList(topListType);
                    }
                });
        addSubscription(subscription);
    }

}
