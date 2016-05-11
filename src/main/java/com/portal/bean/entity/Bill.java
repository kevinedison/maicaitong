package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Bill extends PagingEntity
{
    private String userId;
    
    private String orderId;
    
    private String payInfo;
    
    private Double payAmount;
    
    private Double orderFee;
    
    public String getPayInfo()
    {
        return payInfo;
    }
    
    public void setPayInfo(String payInfo)
    {
        this.payInfo = payInfo;
    }
    
    public Double getPayAmount()
    {
        return payAmount;
    }
    
    public void setPayAmount(Double payAmount)
    {
        this.payAmount = payAmount;
    }
    
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
    
    public Double getOrderFee()
    {
        return orderFee;
    }
    
    public void setOrderFee(Double orderFee)
    {
        this.orderFee = orderFee;
    }
    
}