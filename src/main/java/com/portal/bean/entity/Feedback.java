package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Feedback extends PagingEntity
{
    
    private String question;
    
    private String picture;
    
    private String userId;

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }
}
