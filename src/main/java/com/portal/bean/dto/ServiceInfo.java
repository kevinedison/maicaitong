package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.UserService;

public class ServiceInfo extends UserService
{
    private SimpUserInfo userInfo;
    
    public ServiceInfo()
    {
    }
    
    public ServiceInfo(UserService userService)
    {
        BeanUtils.copyProperties(userService, this);
    }
    
    public SimpUserInfo getUserInfo()
    {
        return userInfo;
    }
    
    public void setUserInfo(SimpUserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    
}
