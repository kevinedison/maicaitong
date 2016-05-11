package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Favorite extends PagingEntity
{ 
    private String userId;
    
    private String targetId;
    
    private Integer targetType;
    
    public Integer getTargetType()
    {
        return targetType;
    }
    
    public void setTargetType(Integer targetType)
    {
        this.targetType = targetType;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId == null ? null : userId.trim();
    }
    
    public String getTargetId()
    {
        return targetId;
    }
    
    public void setTargetId(String targetId)
    {
        this.targetId = targetId == null ? null : targetId.trim();
    }
    
}