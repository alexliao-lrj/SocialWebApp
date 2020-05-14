package com.socialweb.dev.entity;

import java.io.Serializable;

public class FavKey implements Serializable {
    private Integer postId;
    private String userId;

    public FavKey(){}

    public FavKey(Integer postId, String userId){
        this.postId = postId;
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof FavKey){
            FavKey f = (FavKey) that;
            return f.postId == postId && f.userId.equals(userId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return postId * 32 + userId.hashCode();
    }
}
