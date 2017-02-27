package com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaoziliang on 17/2/19.
 */

public class TopListType {

    @SerializedName("type1")
    public TopListTypeRank integrated_list; //综合板块本周排名

    @SerializedName("type3")
    public TopListTypeRank cover_list; //翻唱板块本周排名

    @SerializedName("type4")
    public TopListTypeRank esport_list;//网游电竞板块本周排名

    @SerializedName("type5")
    public TopListTypeRank variety_list;//综艺板块本周排名

    @SerializedName("type11")
    public TopListTypeRank tv_list;//电视剧板块本周排名

    @SerializedName("type13")
    public TopListTypeRank serial_cartoon_list;//连载动画板块本周排名

    @SerializedName("type23")
    public TopListTypeRank movie_list;//电影板块本周排名

    @SerializedName("type36")
    public TopListTypeRank funny_sci_human_list;//趣味科普人文板块本周排名

    @SerializedName("type129")
    public TopListTypeRank otaku_dance_list;//宅舞板块本周排名

    @SerializedName("type119")
    public TopListTypeRank kichiku_list;//鬼畜板块本周排名

    public TopListTypeRank getIntegrated_list() {
        return integrated_list;
    }

    public void setIntegrated_list(TopListTypeRank integrated_list) {
        this.integrated_list = integrated_list;
    }

    public TopListTypeRank getCover_list() {
        return cover_list;
    }

    public void setCover_list(TopListTypeRank cover_list) {
        this.cover_list = cover_list;
    }

    public TopListTypeRank getEsport_list() {
        return esport_list;
    }

    public void setEsport_list(TopListTypeRank esport_list) {
        this.esport_list = esport_list;
    }

    public TopListTypeRank getVariety_list() {
        return variety_list;
    }

    public void setVariety_list(TopListTypeRank variety_list) {
        this.variety_list = variety_list;
    }

    public TopListTypeRank getTv_list() {
        return tv_list;
    }

    public void setTv_list(TopListTypeRank tv_list) {
        this.tv_list = tv_list;
    }

    public TopListTypeRank getSerial_cartoon_list() {
        return serial_cartoon_list;
    }

    public void setSerial_cartoon_list(TopListTypeRank serial_cartoon_list) {
        this.serial_cartoon_list = serial_cartoon_list;
    }

    public TopListTypeRank getMovie_list() {
        return movie_list;
    }

    public void setMovie_list(TopListTypeRank movie_list) {
        this.movie_list = movie_list;
    }

    public TopListTypeRank getFunny_sci_human_list() {
        return funny_sci_human_list;
    }

    public void setFunny_sci_human_list(TopListTypeRank funny_sci_human_list) {
        this.funny_sci_human_list = funny_sci_human_list;
    }

    public TopListTypeRank getOtaku_dance_list() {
        return otaku_dance_list;
    }

    public void setOtaku_dance_list(TopListTypeRank otaku_dance_list) {
        this.otaku_dance_list = otaku_dance_list;
    }

    public TopListTypeRank getKichiku_list() {
        return kichiku_list;
    }

    public void setKichiku_list(TopListTypeRank kichiku_list) {
        this.kichiku_list = kichiku_list;
    }
}
