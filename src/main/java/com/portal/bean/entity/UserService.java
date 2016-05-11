package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class UserService extends PagingEntity
{
    private String title;
    
    private String userId;
    
    private String country;
    
    private String category;
    
    private String brand;
    
    private String subtype;
    
    private String original;
    
    private Double price;
    
    private Integer priceType;
    
    private Double originPrice;

    private Double postPrice;
    
    private Double servicePrice;
    
    private String description;
    
    private Integer recommendation;
    
    private Integer ordercount;
    
    private Integer commentcount;
    
    private String cover;
    
    private Integer favoritecount;
    
    private String attr;
    
    private String pricedetail;
    
    private String servicedetail;
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title == null ? null : title.trim();
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId == null ? null : userId.trim();
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country == null ? null : country.trim();
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category == null ? null : category.trim();
    }
    
    public String getBrand()
    {
        return brand;
    }
    
    public void setBrand(String brand)
    {
        this.brand = brand == null ? null : brand.trim();
    }
    
    public String getSubtype()
    {
        return subtype;
    }
    
    public void setSubtype(String subtype)
    {
        this.subtype = subtype == null ? null : subtype.trim();
    }
    
    public String getOriginal()
    {
        return original;
    }
    
    public void setOriginal(String original)
    {
        this.original = original == null ? null : original.trim();
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description == null ? null : description.trim();
    }
    
    public Integer getOrdercount()
    {
        return ordercount;
    }
    
    public void setOrdercount(Integer ordercount)
    {
        this.ordercount = ordercount;
    }
    
    public Integer getCommentcount()
    {
        return commentcount;
    }
    
    public void setCommentcount(Integer commentcount)
    {
        this.commentcount = commentcount;
    }
    
    public String getCover()
    {
        return cover;
    }
    
    public void setCover(String cover)
    {
        this.cover = cover == null ? null : cover.trim();
    }
    
    public Integer getFavoritecount()
    {
        return favoritecount;
    }
    
    public void setFavoritecount(Integer favoritecount)
    {
        this.favoritecount = favoritecount;
    }
    
    public Double getPrice()
    {
        return price;
    }
    
    public void setPrice(Double price)
    {
        this.price = price;
    }
    
    public Integer getPriceType()
    {
        return priceType;
    }

    public void setPriceType(Integer priceType)
    {
        this.priceType = priceType;
    }

    public Double getOriginPrice()
    {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice)
    {
        this.originPrice = originPrice;
    }

    public Double getPostPrice()
    {
        return postPrice;
    }

    public void setPostPrice(Double postPrice)
    {
        this.postPrice = postPrice;
    }

    public Double getServicePrice()
    {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice)
    {
        this.servicePrice = servicePrice;
    }
    
    public Integer getRecommendation()
    {
        return recommendation;
    }
    
    public void setRecommendation(Integer recommendation)
    {
        this.recommendation = recommendation;
    }
    
    public String getAttr()
    {
        return attr;
    }
    
    public void setAttr(String attr)
    {
        this.attr = attr;
    }
    
    public String getPricedetail()
    {
        return pricedetail;
    }
    
    public void setPricedetail(String pricedetail)
    {
        this.pricedetail = pricedetail;
    }
    
    public String getServicedetail()
    {
        return servicedetail;
    }
    
    public void setServicedetail(String servicedetail)
    {
        this.servicedetail = servicedetail;
    }
    
}