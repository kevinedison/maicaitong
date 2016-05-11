package com.portal.bean.request.console;

import com.portal.bean.IdRequest;

public class SubCategoryUpdateRequest extends IdRequest
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 4411144176365569234L;
    
    private String code;
    
    private String name;
    
    private String categoryCode;
    
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
    
    public String getCategoryCode()
    {
        return categoryCode;
    }
    
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
    }
    
}
