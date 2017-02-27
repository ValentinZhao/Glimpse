package com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhaoziliang on 17/2/27.
 */

public class TopListTypeRank {

    @SerializedName("0")
    TopListTypeItem top1;

    @SerializedName("1")
    TopListTypeItem top2;

    @SerializedName("2")
    TopListTypeItem top3;

    @SerializedName("3")
    TopListTypeItem top4;

    @SerializedName("4")
    TopListTypeItem top5;

    @SerializedName("5")
    TopListTypeItem top6;

    @SerializedName("6")
    TopListTypeItem top7;

    @SerializedName("7")
    TopListTypeItem top8;

    @SerializedName("8")
    TopListTypeItem top9;

    ArrayList<TopListTypeItem> mTopListItems = new ArrayList<>();

    public ArrayList<TopListTypeItem> getAllItems(){
        mTopListItems.add(top1);
        mTopListItems.add(top2);
        mTopListItems.add(top3);
        mTopListItems.add(top4);
        mTopListItems.add(top5);
        mTopListItems.add(top6);
        mTopListItems.add(top7);
        mTopListItems.add(top8);
        mTopListItems.add(top9);
        return mTopListItems;
    }

    public TopListTypeItem getTop1() {
        return top1;
    }

    public void setTop1(TopListTypeItem top1) {
        this.top1 = top1;
    }

    public TopListTypeItem getTop2() {
        return top2;
    }

    public void setTop2(TopListTypeItem top2) {
        this.top2 = top2;
    }

    public TopListTypeItem getTop3() {
        return top3;
    }

    public void setTop3(TopListTypeItem top3) {
        this.top3 = top3;
    }

    public TopListTypeItem getTop4() {
        return top4;
    }

    public void setTop4(TopListTypeItem top4) {
        this.top4 = top4;
    }

    public TopListTypeItem getTop5() {
        return top5;
    }

    public void setTop5(TopListTypeItem top5) {
        this.top5 = top5;
    }

    public TopListTypeItem getTop6() {
        return top6;
    }

    public void setTop6(TopListTypeItem top6) {
        this.top6 = top6;
    }

    public TopListTypeItem getTop7() {
        return top7;
    }

    public void setTop7(TopListTypeItem top7) {
        this.top7 = top7;
    }

    public TopListTypeItem getTop8() {
        return top8;
    }

    public void setTop8(TopListTypeItem top8) {
        this.top8 = top8;
    }

    public TopListTypeItem getTop9() {
        return top9;
    }

    public void setTop9(TopListTypeItem top9) {
        this.top9 = top9;
    }
}
