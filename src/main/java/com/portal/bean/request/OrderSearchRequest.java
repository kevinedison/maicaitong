package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.Order;

public class OrderSearchRequest extends RequestPage
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private String id;
    
    private String serviceId;
    
    private String saleId;
    
    private Integer status;
    
    public Order toEntity()
    {
        Order order = new Order();
        BeanUtils.copyProperties(this, order);
        
        order.setOffset(getOffset());
        order.setLimit(getLimit());
        
        return order;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getServiceId()
    {
        return serviceId;
    }
    
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    
    public String getSaleId()
    {
        return saleId;
    }
    
    public void setSaleId(String saleId)
    {
        this.saleId = saleId;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
}
