package com.portal.bean.entity;

import java.sql.Timestamp;

import com.portal.bean.entity.base.PagingEntity;

public class Message extends PagingEntity
{
    
    private String relationId;
    
    private String message;
    
    private String location;
    
    private String senderId;
    
    private String receiverId;
    
    private Integer readStatus;
    
    private Timestamp messageTime;
    
    private String messageType;
    
    public String getRelationId()
    {
        return relationId;
    }
    
    public void setRelationId(String relationId)
    {
        this.relationId = relationId == null ? null : relationId.trim();
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message == null ? null : message.trim();
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location == null ? null : location.trim();
    }
    
    public String getSenderId()
    {
        return senderId;
    }
    
    public void setSenderId(String senderId)
    {
        this.senderId = senderId == null ? null : senderId.trim();
    }
    
    public String getReceiverId()
    {
        return receiverId;
    }
    
    public void setReceiverId(String receiverId)
    {
        this.receiverId = receiverId == null ? null : receiverId.trim();
    }
    
    public Timestamp getMessageTime()
    {
        return messageTime;
    }
    
    public void setMessageTime(Timestamp messageTime)
    {
        this.messageTime = messageTime;
    }
    
    public Integer getReadStatus()
    {
        return readStatus;
    }
    
    public void setReadStatus(Integer readStatus)
    {
        this.readStatus = readStatus;
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