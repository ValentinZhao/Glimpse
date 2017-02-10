package com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaoziliang on 17/2/8.
 */

public class ZhihuDailyItem {

    @SerializedName("images")
    private String[] images;

    @SerializedName("type")
    private int type;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    private String date;
    public boolean hasFadedIn = false;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
