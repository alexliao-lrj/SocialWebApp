package com.socialweb.dev.entity;

import java.io.Serializable;

public class SuggestionKey implements Serializable {
    private String userId;
    private Integer suggestPostId;

    public SuggestionKey(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSuggestPostId() {
        return suggestPostId;
    }

    public void setSuggestPostId(Integer suggestPostId) {
        this.suggestPostId = suggestPostId;
    }

    @Override
    public boolean equals(Object that) {
        if(that instanceof SuggestionKey){
            SuggestionKey s = (SuggestionKey) that;
            return userId.equals(s.userId) && suggestPostId == s.suggestPostId;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return userId.hashCode() * 32 + suggestPostId;
    }
}
