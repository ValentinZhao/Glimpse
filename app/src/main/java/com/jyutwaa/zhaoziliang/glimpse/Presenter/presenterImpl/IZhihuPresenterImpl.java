package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;

import com.google.gson.Gson;
import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDaily;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDailyItem;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IZhuhuPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IZhihuFragment;
import com.jyutwaa.zhaoziliang.glimpse.Utils.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/2/9.
 */

public class IZhihuPresenterImpl extends BasePresenterImpl implements IZhuhuPresenter{

    private IZhihuFragment mIZhihuFragment;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();

    public IZhihuPresenterImpl(Context context, IZhihuFragment mIZhihuFragment) {
        mCacheUtil = CacheUtil.get(context);
        this.mIZhihuFragment = mIZhihuFragment;
    }

    @Override
    public void getLatestDaily() {
        Subscription subscription = ApiManager.getInstance().getZhihuApiService().getLatestDaily()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        String date = zhihuDaily.getDate();
                        for(ZhihuDailyItem item : zhihuDaily.getStories()){
                            item.setDate(date);
                        }
                        return zhihuDaily;
                    }
                }).subscribe(new Observer<ZhihuDaily>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mIZhihuFragment.hideProgressbar();
                mIZhihuFragment.showError(e.getMessage());
            }

            @Override
            public void onNext(ZhihuDaily zhihuDaily) {
                mIZhihuFragment.hideProgressbar();
                mCacheUtil.put(Config.ZHIHU, gson.toJson(zhihuDaily));
                mIZhihuFragment.updateList(zhihuDaily);
            }
        });
        addSubscription(subscription);
    }

    @Override
    public void getTheDaily(String date) {
        Subscription subscription = ApiManager.getInstance().getZhihuApiService().getTheDaily(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {
                        String date = zhihuDaily.getDate();
                        for(ZhihuDailyItem item : zhihuDaily.getStories()){
                            item.setDate(date);
                        }
                        return zhihuDaily;
                    }
                }).subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIZhihuFragment.hideProgressbar();
                        mIZhihuFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mIZhihuFragment.hideProgressbar();
                        mIZhihuFragment.updateList(zhihuDaily);
                    }
                });
        addSubscription(subscription);
    }
}