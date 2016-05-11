package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.Order;

public class OrderInfo<T> extends Order
{
    
    private T userService;
    
    private SimpUserInfo buyerUser;
    
    private SimpUserInfo salerUser;
    
    public OrderInfo()
    {
        
    }
    
    public OrderInfo(Order order)
    {
        BeanUtils.copyProperties(order, this);
    }
    
    public T getUserService()
    {
        return userService;
    }
    
    public void setUserService(T userService)
    {
        this.userService = userService;
    }
    
    public SimpUserInfo getBuyerUser()
    {
        return buyerUser;
    }
    
    public void setBuyerUser(SimpUserInfo buyerUser)
    {
        this.buyerUser = buyerUser;
    }
    
    public SimpUserInfo getSalerUser()
    {
        return salerUser;
    }
    
    public void setSalerUser(SimpUserInfo salerUser)
    {
        this.salerUser = salerUser;
    }
    
}
