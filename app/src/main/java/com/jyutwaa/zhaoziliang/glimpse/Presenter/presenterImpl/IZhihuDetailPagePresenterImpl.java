package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IZhihuDetailPagePresenter;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IZhihuDetailPage;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/2/11.
 */

public class IZhihuDetailPagePresenterImpl extends BasePresenterImpl implements IZhihuDetailPagePresenter{

    private IZhihuDetailPage mIZhihuDetailPage;

    @Override
    public void getDetailPage(String id) {
        Subscription subscription = ApiManager.getInstance().getZhihuApiService().getZhihuDetailPage(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDetailPage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mIZhihuDetailPage.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuDetailPage zhihuDetailPage) {
                        mIZhihuDetailPage.showDetailPage(zhihuDetailPage);
                    }
                });
        addSubscription(subscription);
    }
}
