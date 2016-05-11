package com.portal.bean.console;

import com.portal.bean.entity.base.PagingEntity;

public class StaticService extends PagingEntity
{
    private String serviceId;
    
    private String serviceContent;
    
    private String userId;
    
    private Integer count;
    
    private String staticTime;
    
    public String getServiceId()
    {
        return serviceId;
    }
    
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
    
    public String getStaticTime()
    {
        return staticTime;
    }
    
    public void setStaticTime(String staticTime)
    {
        this.staticTime = staticTime;
    }
    
    public String getServiceContent()
    {
        return serviceContent;
    }
    
    public void setServiceContent(String serviceContent)
    {
        this.serviceContent = serviceContent;
    }
}
