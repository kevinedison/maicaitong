package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class UserStatic extends PagingEntity
{
    private String userId;
    
    private Long orderCount;
    
    private Long commentCount;
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Long getOrderCount()
    {
        return orderCount;
    }
    
    public void setOrderCount(Long orderCount)
    {
        this.orderCount = orderCount;
    }
    
    public Long getCommentCount()
    {
        return commentCount;
    }
    
    public void setCommentCount(Long commentCount)
    {
        this.commentCount = commentCount;
    }
    
}