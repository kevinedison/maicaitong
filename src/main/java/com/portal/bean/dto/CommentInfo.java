package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.Comment;
import com.portal.bean.entity.SimpUser;

public class CommentInfo extends Comment
{
    
    private SimpUser user;
    
    private ServiceInfo serviceInfo;
    
    public CommentInfo()
    {
        
    }
    
    public CommentInfo(Comment comment)
    {
        BeanUtils.copyProperties(comment, this);
    }
    
    public SimpUser getUser()
    {
        return user;
    }
    
    public void setUser(SimpUser user)
    {
        this.user = user;
    }
    
    public ServiceInfo getServiceInfo()
    {
        return serviceInfo;
    }
    
    public void setServiceInfo(ServiceInfo serviceInfo)
    {
        this.serviceInfo = serviceInfo;
    }
    
}
