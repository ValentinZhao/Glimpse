package com.jyutwaa.zhaoziliang.glimpse.Presenter;

/**
 * Created by zhaoziliang on 17/2/9.
 */

public interface IZhuhuPresenter extends BasePresenter {

    void getLatestDaily();

    void getTheDaily(String date);
}
