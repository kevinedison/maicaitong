package com.portal.bean.request.console;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.console.Brand;

public class BrandSearchRequest extends RequestPage
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2008141879891024379L;
    
    private String code;
    
    private String name;
    
    public Brand toEntity(String operator)
    {
        Brand brand = new Brand();
        BeanUtils.copyProperties(this, brand);
        
        brand.setOffset(getOffset());
        brand.setLimit(getLimit());
        
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
    
}
