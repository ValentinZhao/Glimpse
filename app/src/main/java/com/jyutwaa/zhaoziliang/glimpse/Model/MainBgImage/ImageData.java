package com.jyutwaa.zhaoziliang.glimpse.Model.MainBgImage;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhaoziliang on 17/2/5.
 */

public class ImageData {

    @SerializedName("images")
    private ArrayList<ImageDetail> images;

    @SerializedName("base_url")
    private String base_url;

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public ArrayList<ImageDetail> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageDetail> images) {
        this.images = images;
    }
}
