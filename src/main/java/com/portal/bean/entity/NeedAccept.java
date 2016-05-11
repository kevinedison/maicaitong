package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class NeedAccept extends PagingEntity
{
    private String acpUser;    
    private String needUser;   
    private String needId;   
    private String description;
    private Double originprice;
    private Double postprice;
    private Double serviceprice;
    private Double addprice;
    private Double price;
    private String time;   
    private String picture;
    
    public String getAcpUser()
    {
        return acpUser;
    }
    
    public void setAcpUser(String acpUser)
    {
        this.acpUser = acpUser == null ? null : acpUser.trim();
    }
    
    public String getNeedUser()
    {
        return needUser;
    }
    
    public void setNeedUser(String needUser)
    {
        this.needUser = needUser == null ? null : needUser.trim();
    }
    
    public String getNeedId()
    {
        return needId;
    }
    
    public void setNeedId(String needId)
    {
        this.needId = needId == null ? null : needId.trim();
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description == null ? null : description.trim();
    }
    
    public String getTime()
    {
        return time;
    }
    
    public void setTime(String time)
    {
        this.time = time == null ? null : time.trim();
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public void setPicture(String picture)
    {
        this.picture = picture == null ? null : picture.trim();
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