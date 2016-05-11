package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class Order extends PagingEntity
{
    private String serviceId;
    
    private String buyerId;
    
    private String saleId;
    
    private Double price;
    
    private Integer count;
    
    private Double totalPrice;
    
    private String serviceContent;
    
    private String buyerInfo;
    
    private Integer payType;
    
    private Integer isComment;
    
    private Integer[] statusArr;
    
    public String getServiceId()
    {
        return serviceId;
    }
    
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    
    public String getBuyerId()
    {
        return buyerId;
    }
    
    public void setBuyerId(String buyerId)
    {
        this.buyerId = buyerId;
    }
    
    public String getSaleId()
    {
        return saleId;
    }
    
    public void setSaleId(String saleId)
    {
        this.saleId = saleId;
    }
    
    public Double getPrice()
    {
        return price;
    }
    
    public void setPrice(Double price)
    {
        this.price = price;
    }
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
    
    public Double getTotalPrice()
    {
        return totalPrice;
    }
    
    public void setTotalPrice(Double totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    public String getServiceContent()
    {
        return serviceContent;
    }
    
    public void setServiceContent(String serviceContent)
    {
        this.serviceContent = serviceContent;
    }
    
    public String getBuyerInfo()
    {
        return buyerInfo;
    }
    
    public void setBuyerInfo(String buyerInfo)
    {
        this.buyerInfo = buyerInfo;
    }
    
    public Integer getIsComment()
    {
        return isComment;
    }
    
    public void setIsComment(Integer isComment)
    {
        this.isComment = isComment;
    }
    
    public Integer[] getStatusArr()
    {
        return statusArr;
    }
    
    public void setStatusArr(Integer[] statusArr)
    {
        this.statusArr = statusArr;
    }
    
    public Integer getPayType()
    {
        return payType;
    }
    
    public void setPayType(Integer payType)
    {
        this.payType = payType;
    }
    
}