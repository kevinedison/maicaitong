package com.portal.bean.console;

import com.portal.bean.entity.base.PagingEntity;

public class Brand extends PagingEntity
{
    private String code;
    
    private String name;
    
    private String categoryCode;
    
    private String title;
    
    private String description;
    
    private String picture;
    
    private String cover;
    
    private Integer recommendation;
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
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
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public void setPicture(String picture)
    {
        this.picture = picture;
    }
    
    public String getCover()
    {
        return cover;
    }
    
    public void setCover(String cover)
    {
        this.cover = cover;
    }
    
    public Integer getRecommendation()
    {
        return recommendation;
    }
    
    public void setRecommendation(Integer recommendation)
    {
        this.recommendation = recommendation;
    }
    
    public String getCategoryCode()
    {
        return categoryCode;
    }
    
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }
}
