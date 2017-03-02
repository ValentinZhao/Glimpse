package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;
import android.util.Log;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili.SerialFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliSerialPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Utils.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/2/28.
 */

public class IBilibiliSerialPresenterImpl extends BasePresenterImpl implements IBilibiliSerialPresenter {

    Context mContext;
    SerialFragment mIBilibiliSerialFragment;
    CacheUtil mCacheUtils;
    public IBilibiliSerialPresenterImpl(Context mContext, SerialFragment mIBilibiliSerialFragment) {
        this.mContext = mContext;
        this.mIBilibiliSerialFragment = mIBilibiliSerialFragment;
        this.mCacheUtils = CacheUtil.get(mContext);
    }

    @Override
    public void getSerialTopList() {
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
                        mIBilibiliSerialFragment.hideProgressbar();
                        mIBilibiliSerialFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TopListType topListType) {
                        mIBilibiliSerialFragment.hideProgressbar();
                        mIBilibiliSerialFragment.updateList(topListType);
                    }
                });
        addSubscription(subscription);
    }
}
