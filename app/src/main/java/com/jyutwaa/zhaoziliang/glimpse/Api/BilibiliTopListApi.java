package com.jyutwaa.zhaoziliang.glimpse.Api;

import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.TopListType;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhaoziliang on 17/2/19.
 */

public interface BilibiliTopListApi {

    @GET("/index")
    Observable<TopListType> getTopList();
}
