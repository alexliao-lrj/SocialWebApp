package com.socialweb.dev.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "favorite")
@IdClass(FavKey.class)
public class Favorite implements Serializable {
    @Id
    private Integer postId;
    @Id
    private String userId;

    public Favorite(){}

    public Favorite(Integer postId, String userId){
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

}
