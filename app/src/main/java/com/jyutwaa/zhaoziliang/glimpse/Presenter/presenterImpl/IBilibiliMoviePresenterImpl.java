package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;
import android.util.Log;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili.MovieFragment;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliMoviePresenter;
import com.jyutwaa.zhaoziliang.glimpse.Utils.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/2/28.
 */

public class IBilibiliMoviePresenterImpl extends BasePresenterImpl implements IBilibiliMoviePresenter {

    Context mContext;
    MovieFragment mIBilibiliMovieFragment;
    CacheUtil mCacheUtils;
    public IBilibiliMoviePresenterImpl(Context mContext, MovieFragment mIBilibiliMovieFragment) {
        this.mContext = mContext;
        this.mIBilibiliMovieFragment = mIBilibiliMovieFragment;
        this.mCacheUtils = CacheUtil.get(mContext);
    }

    @Override
    public void getMovieTopList() {
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
                        mIBilibiliMovieFragment.hideProgressbar();
                        mIBilibiliMovieFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TopListType topListType) {
                        mIBilibiliMovieFragment.hideProgressbar();
                        mIBilibiliMovieFragment.updateList(topListType);
                    }
                });
        addSubscription(subscription);
    }
}
