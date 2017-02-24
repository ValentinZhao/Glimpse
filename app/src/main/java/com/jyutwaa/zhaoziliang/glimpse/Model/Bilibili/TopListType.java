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

    public ArrayList<TopListTypeItem> getKichiku_list() {
        return kichiku_list;
    }

    public void setKichiku_list(ArrayList<TopListTypeItem> kichiku_list) {
        this.kichiku_list = kichiku_list;
    }

    public ArrayList<TopListTypeItem> getIntegrated_list() {
        return integrated_list;
    }

    public void setIntegrated_list(ArrayList<TopListTypeItem> integrated_list) {
        this.integrated_list = integrated_list;
    }

    public ArrayList<TopListTypeItem> getCover_list() {
        return cover_list;
    }

    public void setCover_list(ArrayList<TopListTypeItem> cover_list) {
        this.cover_list = cover_list;
    }

    public ArrayList<TopListTypeItem> getEsport_list() {
        return esport_list;
    }

    public void setEsport_list(ArrayList<TopListTypeItem> esport_list) {
        this.esport_list = esport_list;
    }

    public ArrayList<TopListTypeItem> getVariety_list() {
        return variety_list;
    }

    public void setVariety_list(ArrayList<TopListTypeItem> variety_list) {
        this.variety_list = variety_list;
    }

    public ArrayList<TopListTypeItem> getTv_list() {
        return tv_list;
    }

    public void setTv_list(ArrayList<TopListTypeItem> tv_list) {
        this.tv_list = tv_list;
    }

    public ArrayList<TopListTypeItem> getSerial_cartoon_list() {
        return serial_cartoon_list;
    }

    public void setSerial_cartoon_list(ArrayList<TopListTypeItem> serial_cartoon_list) {
        this.serial_cartoon_list = serial_cartoon_list;
    }

    public ArrayList<TopListTypeItem> getMovie_list() {
        return movie_list;
    }

    public void setMovie_list(ArrayList<TopListTypeItem> movie_list) {
        this.movie_list = movie_list;
    }

    public ArrayList<TopListTypeItem> getFunny_sci_human_list() {
        return funny_sci_human_list;
    }

    public void setFunny_sci_human_list(ArrayList<TopListTypeItem> funny_sci_human_list) {
        this.funny_sci_human_list = funny_sci_human_list;
    }

    public ArrayList<TopListTypeItem> getOtaku_dance_list() {
        return otaku_dance_list;
    }

    public void setOtaku_dance_list(ArrayList<TopListTypeItem> otaku_dance_list) {
        this.otaku_dance_list = otaku_dance_list;
    }
}
