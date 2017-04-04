package com.jyutwaa.zhaoziliang.glimpse.Api;

import com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili.CommentBody;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhaoziliang on 17/4/2.
 */

public interface BilibiliCommentApi {

    @GET("/feedback")
    Observable<CommentBody> getCommentBody();
}
