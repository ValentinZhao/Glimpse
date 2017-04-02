package com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhaoziliang on 17/4/2.
 */

public class CommentBody {

    @SerializedName("hotList")
    ArrayList<CommentHotList> hotLists;

    @SerializedName("list")
    ArrayList<CommentNormalList> normalLists;

    public ArrayList<CommentHotList> getHotLists() {
        return hotLists;
    }

    public void setHotLists(ArrayList<CommentHotList> hotLists) {
        this.hotLists = hotLists;
    }

    public ArrayList<CommentNormalList> getNormalLists() {
        return normalLists;
    }

    public void setNormalLists(ArrayList<CommentNormalList> normalLists) {
        this.normalLists = normalLists;
    }
}
