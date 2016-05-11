package com.portal.bean.request;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.IdRequest;
import com.portal.bean.entity.Order;

public class OrderUpdateRequest extends IdRequest
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2203377057915868227L;
    
    @NotNull
    private Integer status;
    
    public Order toEntity(String operator)
    {
        Order order = new Order();
        BeanUtils.copyProperties(this, order);
        order.setOperator(operator);
        return order;
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
