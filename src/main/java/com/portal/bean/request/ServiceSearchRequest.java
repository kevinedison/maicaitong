package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.UserService;

public class ServiceSearchRequest extends RequestPage
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private String userId;
    
    private Integer status;
    
    private String id;
    
    private String country;
    
    private String category;
    
    private String brand;
    
    private String subtype;
    
    private String title;
    
    public UserService toEntity()
    {
        UserService service = new UserService();
        BeanUtils.copyProperties(this, service);
        service.setOffset(getOffset());
        service.setLimit(getLimit());
        return service;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getBrand()
    {
        return brand;
    }
    
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    
    public String getSubtype()
    {
        return subtype;
    }
    
    public void setSubtype(String subtype)
    {
        this.subtype = subtype;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
}
