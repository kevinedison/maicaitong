package com.portal.bean.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.Order;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.constant.StatusConstant;

public class OrderSaveRequest extends Request
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -4506132873353589229L;
    
    @NotNull
    private String serviceId;
    
    private Date operateTime;
    
    @NotNull
    private Integer count;
    
    public Order toEntity(String operator)
    {
        Order order = new Order();
        BeanUtils.copyProperties(this, order);
        
        order.setId(BaseConstant.ORDER_PREFIX + Identities.getSystemId());
        
        order.setOperator(operator);
        
        order.setStatus(StatusConstant.ORDER_WAIT_PAYMENT);
        return order;
    }
    
    public String getServiceId()
    {
        return serviceId;
    }
    
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
}
