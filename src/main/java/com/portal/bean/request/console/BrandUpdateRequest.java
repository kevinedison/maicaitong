package com.portal.bean.request.console;

import org.springframework.beans.BeanUtils;

import com.portal.bean.IdRequest;
import com.portal.bean.console.Brand;

public class BrandUpdateRequest extends IdRequest
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 4411144176365569234L;
    
    private String code;
    
    private String name;
    
    private String title;
    
    private String description;
    
    private String picture;
    
    private String cover;
    
    private Integer recommendation;
    
    public Brand toEntity(String operator)
    {
        Brand brand = new Brand();
        BeanUtils.copyProperties(this, brand);
        brand.setOperator(operator);
        return brand;
    }
    
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
    
}
