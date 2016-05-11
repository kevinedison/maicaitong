package com.portal.bean.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.Need;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.constant.StatusConstant;

public class NeedSaveRequest extends Request
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1945166624737781450L;
    
    @NotNull
    private String title;
    
    private String description;
    
    private String servicerequire;
    
    @NotNull
    private Double price;
    
    @NotNull
    private Integer count;
    
    private String picture;
    
    private Date operateTime;
    
    public Need toEntity(String operator)
    {
        Need need = new Need();
        BeanUtils.copyProperties(this, need);
        
        need.setId(BaseConstant.NEED_PREFIX + Identities.getSystemId());
        
        need.setOperator(operator);
        need.setOperateTime(new Date());
        need.setUserId(operator);
        
        need.setStatus(StatusConstant.STATUS_ONLINE);
        return need;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getServicerequire()
    {
        return servicerequire;
    }
    
    public void setServicerequire(String servicerequire)
    {
        this.servicerequire = servicerequire;
    }
    
    public Double getPrice()
    {
        return price;
    }
    
    public void setPrice(Double price)
    {
        this.price = price;
    }
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public void setPicture(String picture)
    {
        this.picture = picture;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
}
