package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.Message;
import com.portal.bean.entity.SimpUser;

public class MessageInfo extends Message
{
    
    private SimpUser sender;
    
    private SimpUser receiver;
    
    public MessageInfo()
    {
        
    }
    
    public MessageInfo(Message message)
    {
        BeanUtils.copyProperties(message, this);
    }
    
    public SimpUser getSender()
    {
        return sender;
    }
    
    public void setSender(SimpUser sender)
    {
        this.sender = sender;
    }
    
    public SimpUser getReceiver()
    {
        return receiver;
    }
    
    public void setReceiver(SimpUser receiver)
    {
        this.receiver = receiver;
    }
    
}
