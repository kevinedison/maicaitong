package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.Comment;

public class CommentSearchRequest extends RequestPage
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private String userId;
    
    private String orderId;
    
    private String serviceId;
    
    private String tarUserId;
    
    public Comment toEntity()
    {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        
        comment.setOffset(getOffset());
        comment.setLimit(getLimit());
        
        return comment;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getServiceId()
    {
        return serviceId;
    }
    
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    
    public String getTarUserId()
    {
        return tarUserId;
    }
    
    public void setTarUserId(String tarUserId)
    {
        this.tarUserId = tarUserId;
    }
    
}
