package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.Message;

public class MessageSearchRequest extends RequestPage
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private String relationId;
    
    private String toUser;
    
    public Message toEntity()
    {
        
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        
        message.setOffset(getOffset());
        message.setLimit(getLimit());
        
        return message;
    }
    
    public String getRelationId()
    {
        return relationId;
    }
    
    public void setRelationId(String relationId)
    {
        this.relationId = relationId;
    }
    
    public String getToUser()
    {
        return toUser;
    }
    
    public void setToUser(String toUser)
    {
        this.toUser = toUser;
    }
}
