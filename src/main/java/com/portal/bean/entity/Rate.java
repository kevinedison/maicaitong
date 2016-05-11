package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Rate extends PagingEntity
{
    
    private String name;
    
    private String code;
    
    private Double rate;
    
    private Double amount;
    
    private Double result;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Double getRate()
    {
        return rate;
    }

    public void setRate(Double rate)
    {
        this.rate = rate;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public Double getResult()
    {
        return result;
    }

    public void setResult(Double result)
    {
        this.result = result;
    }
}
