package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Comment extends PagingEntity
{
    
    private String userId;
    
    private String orderId;
    
    private String serviceId;
    
    private String comment;
    
    private Integer rate;
    
    private String pictures;
    
    private String tarUserId;
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getServiceId()
    {
        return serviceId;
    }
    
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    
    public String getComment()
    {
        return comment;
    }
    
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    
    public Integer getRate()
    {
        return rate;
    }
    
    public void setRate(Integer rate)
    {
        this.rate = rate;
    }
    
    public String getPictures()
    {
        return pictures;
    }
    
    public void setPictures(String pictures)
    {
        this.pictures = pictures;
    }
    
    public String getTarUserId()
    {
        return tarUserId;
    }
    
    public void setTarUserId(String tarUserId)
    {
        this.tarUserId = tarUserId;
    }
    
}