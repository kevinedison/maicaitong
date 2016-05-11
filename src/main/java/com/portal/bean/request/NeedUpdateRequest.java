package com.portal.bean.request;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.portal.bean.IdRequest;
import com.portal.bean.entity.Need;

public class NeedUpdateRequest extends IdRequest
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1945166624737781450L;
    
    private String title;
    
    private String description;
    
    private String servicerequire;
    
    private Double price;
    
    private Integer count;
    
    private String cover;
    
    private String picture;
    
    // 状态
    private Integer status;
    
    // 最后一次修改时间
    private Date operateTime;
    
    public Need toEntity(String operator)
    {
        Need need = new Need();
        BeanUtils.copyProperties(this, need);
        need.setOperator(operator);
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
    
    public String getCover()
    {
        return cover;
    }
    
    public void setCover(String cover)
    {
        this.cover = cover;
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public void setPicture(String picture)
    {
        this.picture = picture;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
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
