package com.portal.bean.request;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.portal.bean.IdRequest;
import com.portal.bean.entity.Message;

public class MessageUpdateRequest extends IdRequest
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -1099366489150163581L;
    
    private String content;
    
    private String location;
    
    private Integer important;
    
    public Message toEntity(String operator)
    {
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        
        message.setOperator(operator);
        message.setOperateTime(new Date());
        return message;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public Integer getImportant()
    {
        return important;
    }
    
    public void setImportant(Integer important)
    {
        this.important = important;
    }
}
