package com.jyutwaa.zhaoziliang.glimpse.Model.MainBgImage;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaoziliang on 17/2/5.
 */

public class ImageDetail {

    @SerializedName("description")
    private String description;

    @SerializedName("image_url")
    private String image_url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
