package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;

import com.jyutwaa.zhaoziliang.glimpse.Activity.Bilibili.BilibiliCommentActivity;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliCommentPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Utils.CacheUtil;

/**
 * Created by zhaoziliang on 17/4/15.
 */

public class IBilibiliCommentPresenterImpl extends BasePresenterImpl implements IBilibiliCommentPresenter{

    Context mContext;
    BilibiliCommentActivity mCommentActivity;
    CacheUtil mCacheUtil;

    public IBilibiliCommentPresenterImpl(Context mContext, BilibiliCommentActivity mCommentActivity) {
        this.mContext = mContext;
        this.mCommentActivity = mCommentActivity;
        this.mCacheUtil = CacheUtil.get(mContext);
    }

    @Override
    public void getCommentBody() {
//        Subscription subscription = ApiManager.getInstance().getBilibiliCommentApiService().getCommentBody()
    }

}
