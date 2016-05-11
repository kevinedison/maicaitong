package com.portal.bean.console;

import org.springframework.beans.BeanUtils;

import com.portal.bean.dto.UserInfo;

public class StaticServiceInfo<T> extends StaticService
{
    private T serviceInfo;
    
    private UserInfo userInfo;
    
    public StaticServiceInfo(StaticService staticService)
    {
        BeanUtils.copyProperties(staticService, this);
    }
    
    public UserInfo getUserInfo()
    {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    
    public T getServiceInfo()
    {
        return serviceInfo;
    }
    
    public void setServiceInfo(T serviceInfo)
    {
        this.serviceInfo = serviceInfo;
    }
}
