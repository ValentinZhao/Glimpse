package com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaoziliang on 17/2/9.
 */

public class ZhihuDetailPage {
    @SerializedName("body")
    String body;

    @SerializedName("title")
    String title;

    @SerializedName("image")
    String image;

    @SerializedName("share_url")
    String share_url;

    @SerializedName("css")
    String[] css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String[] getCss() {
        return css;
    }

    public void setCss(String[] css) {
        this.css = css;
    }
}
