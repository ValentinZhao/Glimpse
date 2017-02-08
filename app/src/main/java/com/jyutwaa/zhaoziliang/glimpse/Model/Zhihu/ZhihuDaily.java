package com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhaoziliang on 17/2/5.
 */

public class ZhihuDaily {

    @SerializedName("date")
    private String date;

    @SerializedName("stories")
    private ArrayList<ZhihuDailyItem> stories;

    @SerializedName("top_stories")
    private ArrayList<ZhihuDailyItem> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuDailyItem> stories) {
        this.stories = stories;
    }

    public ArrayList<ZhihuDailyItem> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(ArrayList<ZhihuDailyItem> top_stories) {
        this.top_stories = top_stories;
    }
}
