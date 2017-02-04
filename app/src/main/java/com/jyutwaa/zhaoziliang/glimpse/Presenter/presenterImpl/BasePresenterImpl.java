package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import com.jyutwaa.zhaoziliang.glimpse.Presenter.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhaoziliang on 17/2/4.
 */

public class BasePresenterImpl implements BasePresenter {
    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription){
        if(this.mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribe() {
        if(this.mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
