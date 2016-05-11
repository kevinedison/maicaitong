package com.portal.bean.request;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.Bill;

public class BillSearchRequest extends RequestPage
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private Date start;
    
    private Date end;
    
    public Bill toEntity(String operator)
    {
        Bill bill = new Bill();
        BeanUtils.copyProperties(this, bill);
        
        bill.setUserId(operator);
        bill.setOffset(getOffset());
        bill.setLimit(getLimit());
        
        return bill;
    }
    
    public Date getStart()
    {
        return start;
    }
    
    public void setStart(Date start)
    {
        this.start = start;
    }
    
    public Date getEnd()
    {
        return end;
    }
    
    public void setEnd(Date end)
    {
        this.end = end;
    }
    
}
