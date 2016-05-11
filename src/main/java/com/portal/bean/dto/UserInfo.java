package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.Location;
import com.portal.bean.entity.User;

public class UserInfo extends User
{
    private Location countryInfo;
    
    private String orderStatusCount;
    
    public UserInfo()
    {
    }
    
    public UserInfo(User user)
    {
        BeanUtils.copyProperties(user, this);
    }
    
    public Location getCountryInfo()
    {
        return countryInfo;
    }
    
    public void setCountryInfo(Location countryInfo)
    {
        this.countryInfo = countryInfo;
    }
    
    public String getOrderStatusCount()
    {
        return orderStatusCount;
    }
    
    public void setOrderStatusCount(String orderStatusCount)
    {
        this.orderStatusCount = orderStatusCount;
    }
    
}
