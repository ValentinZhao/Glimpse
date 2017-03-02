package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliEsportPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliEsportFragment;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/3/2.
 */

public class IBilibiliEsportPresenterImpl extends BasePresenterImpl implements IBilibiliEsportPresenter{

    Context mContext;
    IBilibiliEsportFragment mIBilibiliEsportFragment;

    public IBilibiliEsportPresenterImpl(Context mContext, IBilibiliEsportFragment mIBilibiliEsportFragment) {
        this.mContext = mContext;
        this.mIBilibiliEsportFragment = mIBilibiliEsportFragment;
    }

    @Override
    public void getEsportTopList() {
        Subscription subscription = ApiManager.getInstance().getBilibiliApiService().getTopList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TopListType, TopListType>() {
                    @Override
                    public TopListType call(TopListType topListType) {
                        for(TopListTypeItem item : topListType.getEsport_list().getAllItems()){
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
                        e.printStackTrace();
                        mIBilibiliEsportFragment.hideProgressbar();
                        mIBilibiliEsportFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TopListType topListType) {
                        mIBilibiliEsportFragment.hideProgressbar();
                        mIBilibiliEsportFragment.updateList(topListType);
                    }
                });
        addSubscription(subscription);
    }
}
