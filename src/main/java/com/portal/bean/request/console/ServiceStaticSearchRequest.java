package com.portal.bean.request.console;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.console.StaticService;

public class ServiceStaticSearchRequest extends RequestPage
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2008141879891024379L;
    
    private String serviceId;
    
    private String serviceContent;
    
    private String userId;
    
    private String staticTime;
    
    public StaticService toEntity(String operator)
    {
        StaticService staticService = new StaticService();
        BeanUtils.copyProperties(this, staticService);
        
        staticService.setOffset(getOffset());
        staticService.setLimit(getLimit());
        
        return staticService;
    }
    
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
