package com.jyutwaa.zhaoziliang.glimpse.Api;

import com.jyutwaa.zhaoziliang.glimpse.Model.MainBgImage.ImageResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhaoziliang on 17/2/5.
 */

public interface MainImageApi {
    @GET("http://lab.zuimeia.com/wallpaper/category/1/?page_size=1")
    Observable<ImageResponse> getImage();
}
