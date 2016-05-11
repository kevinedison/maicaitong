package com.portal.bean.request.console;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.console.Category;

public class CategorySearchRequest extends RequestPage
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2008141879891024379L;
    
    private String code;
    
    private String name;
    
    public Category toEntity(String operator)
    {
        Category category = new Category();
        BeanUtils.copyProperties(this, category);
        
        category.setOffset(getOffset());
        category.setLimit(getLimit());
        
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
    
}
