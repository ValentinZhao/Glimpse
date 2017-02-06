package com.jyutwaa.zhaoziliang.glimpse.Model.MainBgImage;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaoziliang on 17/2/5.
 */

public class ImageResponse {

    @SerializedName("data")
    private ImageData imageData;

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }
}
