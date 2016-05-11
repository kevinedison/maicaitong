package com.portal.bean.request.console;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.console.Category;

public class CategorySaveRequest extends Request
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -2456813228043347091L;
    
    @NotNull
    private String code;
    
    @NotNull
    private String name;
    
    private String brand;
    
    private String subType;
    
    public Category toEntity(String operator)
    {
        Category category = new Category();
        BeanUtils.copyProperties(this, category);
        
        category.setOperator(operator);
        return category;
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
    
    public String getBrand()
    {
        return brand;
    }
    
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    
    public String getSubType()
    {
        return subType;
    }
    
    public void setSubType(String subType)
    {
        this.subType = subType;
    }
    
}
