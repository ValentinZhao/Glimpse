package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;

import com.jyutwaa.zhaoziliang.glimpse.Presenter.IBilibiliPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IBilibiliFragment;

import rx.Subscription;

/**
 * Created by zhaoziliang on 17/2/24.
 */

public class IBilibiliPresenterImpl extends BasePresenterImpl implements IBilibiliPresenter{

    IBilibiliFragment mIBlibiliFragment;
    Context mContext;
    Subscription subscription;

    public IBilibiliPresenterImpl(Context context, IBilibiliFragment mIBlibiliFragment) {
        mContext = context;
        this.mIBlibiliFragment = mIBlibiliFragment;
    }

    @Override
    public void getTopList() {
        subscription
    }

}
