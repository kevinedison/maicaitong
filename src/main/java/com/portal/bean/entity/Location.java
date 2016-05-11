package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Location extends PagingEntity
{
    
    private String countryId;
    
    private String name;
    
    private String code;
    
    private String title;
    
    private String coordinate;
    
    private String phoneCode;
    
    private String cover;
    
    private String picture;
    
    private String description;
    
    private Integer recommendation;
    
    private Integer buyerCount;
    
    private Integer serviceCount;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public void setPicture(String picture)
    {
        this.picture = picture;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public Integer getRecommendation()
    {
        return recommendation;
    }
    
    public void setRecommendation(Integer recommendation)
    {
        this.recommendation = recommendation;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getCountryId()
    {
        return countryId;
    }
    
    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }
    
    public String getCoordinate()
    {
        return coordinate;
    }
    
    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }
    
    public String getPhoneCode()
    {
        return phoneCode;
    }
    
    public void setPhoneCode(String phoneCode)
    {
        this.phoneCode = phoneCode;
    }
    
    public Integer getBuyerCount()
    {
        return buyerCount;
    }
    
    public void setBuyerCount(Integer buyerCount)
    {
        this.buyerCount = buyerCount;
    }
    
    public Integer getServiceCount()
    {
        return serviceCount;
    }
    
    public void setServiceCount(Integer serviceCount)
    {
        this.serviceCount = serviceCount;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }
    
}
