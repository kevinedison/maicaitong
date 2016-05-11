package com.portal.bean.request;

import com.portal.bean.Request;

public class RateExchangeRequest extends Request {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1442990138141764246L;
    
    private String from;
 
    private String to;

    private Double amount;

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }
}
