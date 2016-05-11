package com.portal.bean.console;

import java.util.List;
import java.util.Map;

import com.portal.bean.entity.base.PagingEntity;

public class Category extends PagingEntity
{
    private String code;
    
    private String name;
    
    private String brand;
    
    private String subType;
    
    private List<String> brands;
    
    private Map<String, String> subTypes;
    
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
    
    public List<String> getBrands()
    {
        return brands;
    }
    
    public void setBrands(List<String> brands)
    {
        this.brands = brands;
    }
    
    public Map<String, String> getSubTypes()
    {
        return subTypes;
    }
    
    public void setSubTypes(Map<String, String> subTypes)
    {
        this.subTypes = subTypes;
    }
}
