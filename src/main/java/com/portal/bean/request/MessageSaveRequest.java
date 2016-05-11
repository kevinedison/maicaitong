package com.portal.bean.request;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.Message;
import com.portal.constant.BaseConstant;

public class MessageSaveRequest extends Request
{
    /**
	 * 
	 */
    private static final long serialVersionUID = -404071043021358806L;
    
    @NotNull
    private String message;
    
    private String location;
    
    @NotNull
    private String receiverId;
    
    private String relationId;
    
    @NotNull
    private String messageType;
    
    public Message toEntity(String operator)
    {
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        
        message.setOperator(operator);
        
        message.setReadStatus(BaseConstant.MESSAGE_UNREAD);
        return message;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public String getReceiverId()
    {
        return receiverId;
    }
    
    public void setReceiverId(String receiverId)
    {
        this.receiverId = receiverId;
    }
    
    public String getRelationId()
    {
        return relationId;
    }
    
    public void setRelationId(String relationId)
    {
        this.relationId = relationId;
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
