package com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaoziliang on 17/4/2.
 */

public class CommentHotList {

    @SerializedName("nick")
    String nickName;

    @SerializedName("face")
    String headshot_url;

    @SerializedName("good")
    String like_count;//点赞数

    @SerializedName("create_at")
    String createTime;

    @SerializedName("reply_count")
    String reply_count;

    @SerializedName("sex")
    String gender;

    @SerializedName("msg")
    String comment;
}
