package com.jyutwaa.zhaoziliang.glimpse.Api;

import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDaily;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuStory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zhaoziliang on 17/2/5.
 */

public interface ZhihuApi {

    @GET("/api/4/news/latest")
    Observable<ZhihuDaily> getLastestDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhihuDaily> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhihuStory> getStory(@Path("id") String id);
}
