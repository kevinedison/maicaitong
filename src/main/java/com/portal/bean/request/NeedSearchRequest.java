package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.Need;

public class NeedSearchRequest extends RequestPage
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private String id;
    
    private Integer status;
    
    private String country;
    
    public Need toEntity()
    {
        Need need = new Need();
        BeanUtils.copyProperties(this, need);
        need.setOffset(getOffset());
        need.setLimit(getLimit());
        return need;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
    
}
