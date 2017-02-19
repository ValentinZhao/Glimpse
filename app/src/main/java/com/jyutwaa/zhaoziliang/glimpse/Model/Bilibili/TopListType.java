package com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhaoziliang on 17/2/19.
 */

public class TopListType {

    @SerializedName("type1")
    public ArrayList<TopListTypeItem> integrated_list; //综合板块本周排名

    @SerializedName("type3")
    public ArrayList<TopListTypeItem> cover_list; //翻唱板块本周排名

    @SerializedName("type4")
    public ArrayList<TopListTypeItem> esport_list;//网游电竞板块本周排名

    @SerializedName("type5")
    public ArrayList<TopListTypeItem> variety_list;//综艺板块本周排名

    @SerializedName("type11")
    public ArrayList<TopListTypeItem> tv_list;//电视剧板块本周排名

    @SerializedName("type13")
    public ArrayList<TopListTypeItem> serial_cartoon_list;//连载动画板块本周排名

    @SerializedName("type23")
    public ArrayList<TopListTypeItem> movie_list;//电影板块本周排名

    @SerializedName("type36")
    public ArrayList<TopListTypeItem> funny_sci_human_list;//趣味科普人文板块本周排名

    @SerializedName("type129")
    public ArrayList<TopListTypeItem> otaku_dance_list;//宅舞板块本周排名

    @SerializedName("type119")
    public ArrayList<TopListTypeItem> kichiku_list;//鬼畜板块本周排名
}
