package com.jyutwaa.zhaoziliang.glimpse.Model.Bilibili;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhaoziliang on 17/2/19.
 */

public class TopListTypeItem {
    @SerializedName("aid")
    String aid;

    @SerializedName("typename")
    String typeName;

    @SerializedName("title")
    String title;

    @SerializedName("play")
    String playCount;

    @SerializedName("favorites")
    String favoritesCount;

    @SerializedName("author")
    String author;

    @SerializedName("description")
    String description;

    @SerializedName("pic")
    String iconUrl;

    @SerializedName("comment")
    String commentCount;

    String videoUrl;

    public boolean hasFadedIn = false;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(String favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
