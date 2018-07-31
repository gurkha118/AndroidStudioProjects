package com.example.user.jsonparsing;

import android.provider.MediaStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Items {
    @SerializedName("snippet")
    @Expose
    private Snippet snippet;

    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;


    public Snippet getSnippet() {
        return snippet;

    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;

    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }


}
