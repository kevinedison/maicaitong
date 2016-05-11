package com.portal.bean.entity;

import java.sql.Timestamp;

import com.portal.bean.entity.base.PagingEntity;

public class MessageRelation extends PagingEntity
{
    
    private String userId;
    
    private String contactUserId;
    
    private String recentMessage;
    
    private Timestamp updateTime;
    
    private Integer isContact;
    
    private String unread;
    
    private String messageType;
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId == null ? null : userId.trim();
    }
    
    public String getContactUserId()
    {
        return contactUserId;
    }
    
    public void setContactUserId(String contactUserId)
    {
        this.contactUserId = contactUserId == null ? null : contactUserId.trim();
    }
    
    public String getRecentMessage()
    {
        return recentMessage;
    }
    
    public void setRecentMessage(String recentMessage)
    {
        this.recentMessage = recentMessage == null ? null : recentMessage.trim();
    }
    
    public Timestamp getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Timestamp updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public Integer getIsContact()
    {
        return isContact;
    }
    
    public void setIsContact(Integer isContact)
    {
        this.isContact = isContact;
    }
    
    public String getUnread()
    {
        return unread;
    }
    
    public void setUnread(String unread)
    {
        this.unread = unread;
    }
    
    public String getMessageType()
    {
        return messageType;
    }
    
    public void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }
    
}