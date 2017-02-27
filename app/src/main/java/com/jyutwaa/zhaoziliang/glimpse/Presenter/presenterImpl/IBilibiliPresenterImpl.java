package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;
import android.widget.Toast;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;
import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListTypeItem;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliIntegratedFragment;
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

    IBilibiliIntegratedFragment mIBlibiliFragment;
    Context mContext;
    CacheUtil mCacheUtils;
    public IBilibiliPresenterImpl(Context context, IBilibiliIntegratedFragment mIBlibiliFragment) {
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
                        for(TopListTypeItem item : topListType.getIntegrated_list().getAllItems()){
                            item.setVideoUrl(Config.BILIBILI_VIDEO_BASE_URL + item.getAid());
                            Toast.makeText(mContext, "onError()!", Toast.LENGTH_SHORT).show();
                        }
                        return topListType;
                    }
                }).subscribe(new Observer<TopListType>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "onError()!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        mIBlibiliFragment.hideProgressbar();
                        mIBlibiliFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TopListType topListType) {
                        Toast.makeText(mContext, "onNext()!", Toast.LENGTH_SHORT).show();
                        mIBlibiliFragment.hideProgressbar();
                        mIBlibiliFragment.updateList(topListType);
                    }
                });
        addSubscription(subscription);
    }

}
