package com.portal.bean.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.NeedAccept;
import com.portal.constant.StatusConstant;

public class NeedAcpSaveRequest extends Request
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2889425051312376511L;
    
    @NotNull
    private String needId;
    
    private String description;
    
    @NotNull
    private Double originprice;
    
    @NotNull
    private Double postprice;
    
    @NotNull
    private Double serviceprice;
    
    @NotNull
    private Double addprice;
    
    @NotNull
    private Double price;
    
    @NotNull
    private String time;
    
    private String picture;
    
    public NeedAccept toEntity(String operator)
    {
        NeedAccept needAcp = new NeedAccept();
        BeanUtils.copyProperties(this, needAcp);
        
        needAcp.setOperator(operator);
        needAcp.setOperateTime(new Date());
        
        needAcp.setStatus(StatusConstant.STATUS_ONLINE);
        return needAcp;
    }
    
    public String getNeedId()
    {
        return needId;
    }
    
    public void setNeedId(String needId)
    {
        this.needId = needId;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public Double getPrice()
    {
        return price;
    }
    
    public void setPrice(Double price)
    {
        this.price = price;
    }
    
    public Double getServiceprice()
    {
        return serviceprice;
    }
    
    public void setServiceprice(Double serviceprice)
    {
        this.serviceprice = serviceprice;
    }
    
    public String getTime()
    {
        return time;
    }
    
    public void setTime(String time)
    {
        this.time = time;
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public void setPicture(String picture)
    {
        this.picture = picture;
    }
    
    public Double getOriginprice()
    {
        return originprice;
    }
    
    public void setOriginprice(Double originprice)
    {
        this.originprice = originprice;
    }
    
    public Double getPostprice()
    {
        return postprice;
    }
    
    public void setPostprice(Double postprice)
    {
        this.postprice = postprice;
    }
    
    public Double getAddprice()
    {
        return addprice;
    }
    
    public void setAddprice(Double addprice)
    {
        this.addprice = addprice;
    }
}
