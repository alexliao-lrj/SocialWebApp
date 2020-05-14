package com.socialweb.dev.entity;

import java.io.Serializable;

public class UserProfileKey implements Serializable {
    private String userId;
    private String word;

    public UserProfileKey(){}

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

    @Override
    public boolean equals(Object that) {
        if(that instanceof UserProfileKey){
            UserProfileKey u = (UserProfileKey) that;
            return userId.equals(u.userId) && word.equals(u.word);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return userId.hashCode() * 32 + word.hashCode();
    }
}
