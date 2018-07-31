package com.example.user.jsonparsing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails {
    @SerializedName("medium")
    @Expose Medium medium;

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
}

public class Medium{
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}





