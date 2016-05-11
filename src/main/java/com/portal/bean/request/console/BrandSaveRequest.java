package com.portal.bean.request.console;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.console.Brand;

public class BrandSaveRequest extends Request
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -2456813228043347091L;
    
    @NotNull
    private String code;
    
    @NotNull
    private String name;
    
    @NotNull
    private String title;
    
    @NotNull
    private String categoryCode;
    
    private String description;
    
    private String picture;
    
    private String cover;
    
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
    
    public String getCategoryCode()
    {
        return categoryCode;
    }
    
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }
    
}
