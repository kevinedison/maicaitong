package com.portal.bean.request.console;

import javax.validation.constraints.NotNull;

import com.portal.bean.Request;

public class SubCategorySaveRequest extends Request
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
