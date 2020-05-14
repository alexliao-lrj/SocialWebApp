package com.socialweb.dev.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "userprofiles")
@IdClass(UserProfileKey.class)
public class UserProfile implements Serializable {
    @Id
    private String userId;
    @Id
    private String word;

    private Double score;

    public UserProfile(){}

    public UserProfile(String userId, String word, Double score) {
        this.userId = userId;
        this.word = word;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
