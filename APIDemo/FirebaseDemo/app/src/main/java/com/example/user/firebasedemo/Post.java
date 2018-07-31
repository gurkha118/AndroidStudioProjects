package com.example.user.firebasedemo;

import android.widget.EditText;

import java.util.HashMap;

public class Post {
    private String caption;
    private String description;
    private String image;
    private String postid;
    private String postuploaderid;
    private int commentcount;
    private  int like;
    private HashMap<String,Boolean> likes;

    public HashMap<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, Boolean> likes) {
        this.likes = likes;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostuploaderid() {
        return postuploaderid;
    }

    public void setPostuploaderid(String postuploaderid) {
        this.postuploaderid = postuploaderid;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
