package com.portal.bean.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.Comment;
import com.portal.constant.StatusConstant;

public class CommentSaveRequest extends Request
{
    /**
	 * 
	 */
    private static final long serialVersionUID = -404071043021358806L;
    
    private String serviceId;
    
    @NotNull
    private String orderId;
    
    private String pictures;
    
    private String comment;
    
    private Integer rate;
    
    @NotNull
    private String tarUserId;
    
    public Comment toEntity(String operator)
    {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        
        comment.setUserId(operator);
        comment.setOperator(operator);
        comment.setOperateTime(new Date());
        
        comment.setStatus(StatusConstant.STATUS_ONLINE);
        return comment;
    }
    
    public String getServiceId()
    {
        return serviceId;
    }
    
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    
    public String getPictures()
    {
        return pictures;
    }
    
    public void setPictures(String pictures)
    {
        this.pictures = pictures;
    }
    
    public String getComment()
    {
        return comment;
    }
    
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    
    public Integer getRate()
    {
        return rate;
    }
    
    public void setRate(Integer rate)
    {
        this.rate = rate;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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
