package com.socialweb.dev.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "suggestions")
@IdClass(SuggestionKey.class)
public class Suggestion implements Serializable {
    @Id
    private String userId;
    @Id
    private Integer suggestPostId;

    public Suggestion(){}

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
}
