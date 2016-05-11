package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.MessageRelation;
import com.portal.bean.entity.SimpUser;

public class MessageRelationInfo extends MessageRelation
{
    
    // 用户信息
    private SimpUser contactInfo;
    
    public MessageRelationInfo()
    {
        
    }
    
    public MessageRelationInfo(MessageRelation messageRelation)
    {
        BeanUtils.copyProperties(messageRelation, this);
    }
    
    public SimpUser getContactInfo()
    {
        return contactInfo;
    }
    
    public void setContactInfo(SimpUser contactInfo)
    {
        this.contactInfo = contactInfo;
    }
    
}
