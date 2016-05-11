package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Need extends PagingEntity
{
    private String title;
    
    private String userId;
    
    private String country;
    
    private String description;
    
    private String servicerequire;
    
    private Double price;
    
    private Integer count;
    
    private String cover;
    
    private String picture;
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title == null ? null : title.trim();
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId == null ? null : userId.trim();
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country == null ? null : country.trim();
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description == null ? null : description.trim();
    }
    
    public String getServicerequire()
    {
        return servicerequire;
    }
    
    public void setServicerequire(String servicerequire)
    {
        this.servicerequire = servicerequire == null ? null : servicerequire.trim();
    }
    
    public String getCover()
    {
        return cover;
    }
    
    public void setCover(String cover)
    {
        this.cover = cover == null ? null : cover.trim();
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public void setPicture(String picture)
    {
        this.picture = picture == null ? null : picture.trim();
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
    
}