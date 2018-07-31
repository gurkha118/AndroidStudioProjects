package com.example.user.jsonparsing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippet {
    @SerializedName("publishedAt")
    @Expose
    private String publishedat;

    @SerializedName("channelId")
    @Expose
    private String channelid;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("thumbnails")
    @Expose Thumbnails thumbnails;


    public String getPublishedat() {
        return publishedat;
    }

    public void setPublishedat(String publishedat) {
        this.publishedat = publishedat;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnails getThumbnails() { return thumbnails; }

    public void setThumbnails(Thumbnails thumbnails) { this.thumbnails = thumbnails; }
}
