package com.example.user.firebasedemo;

public class Comment {
    private String comment;
    private  String commenterid;
    private  String  postid;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenterid() {
        return commenterid;
    }

    public void setCommenterid(String commenterid) {
        this.commenterid = commenterid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}
