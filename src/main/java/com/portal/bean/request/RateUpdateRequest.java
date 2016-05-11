package com.portal.bean.request;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.portal.bean.UpdateRequest;
import com.portal.bean.entity.Rate;

public class RateUpdateRequest extends UpdateRequest {
    /**
     * 
     */
    private static final long serialVersionUID = 1442990138141764246L;

    private String name;
    
    private String code;
    
    private String rate;
    
    private String amount;
    
    private String result;
     
    public Rate toEntity(String operator) {
        Rate rate = new Rate();
        BeanUtils.copyProperties(this, rate);
        rate.setOperator(operator);
        rate.setOperateTime(new Date());     
        return rate;
    }

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

    public String getRate()
    {
        return rate;
    }

    public void setRate(String rate)
    {
        this.rate = rate;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
