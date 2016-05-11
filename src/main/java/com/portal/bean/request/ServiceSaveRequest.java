package com.portal.bean.request;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.UserService;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.constant.StatusConstant;

public class ServiceSaveRequest extends Request
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -404071043021358806L;
    
    @NotNull
    private String title;
    
    @NotNull
    private String category;
    
    private String brand;
    
    private String subtype;
    
    private String original;
    
    
    @NotNull
    private Double price;
    
    private Integer priceType;
    private Double  originPrice;
    private Double  postPrice;
    private Double  servicePrice;
    
    private String description;
    
    @NotNull
    private String cover;
    
    private String servicedetail;
    
    private Integer status;
    
    public UserService toEntity(String operator)
    {
        UserService userService = new UserService();
        BeanUtils.copyProperties(this, userService);
        
        userService.setId(BaseConstant.SERVICE_PREFIX + Identities.getSystemId());
        
        userService.setOperator(operator);
        userService.setUserId(operator);
        if(this.getStatus() == null) {
            userService.setStatus(StatusConstant.STATUS_ONLINE);
        }
        
        userService.setRecommendation(StatusConstant.RECOMMENDATION_NOT);
        return userService;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getBrand()
    {
        return brand;
    }
    
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    
    public String getSubtype()
    {
        return subtype;
    }
    
    public void setSubtype(String subtype)
    {
        this.subtype = subtype;
    }
    
    public String getOriginal()
    {
        return original;
    }
    
    public void setOriginal(String original)
    {
        this.original = original;
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
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getCover()
    {
        return cover;
    }
    
    public void setCover(String cover)
    {
        this.cover = cover;
    }
    
    public String getServicedetail()
    {
        return servicedetail;
    }
    
    public void setServicedetail(String servicedetail)
    {
        this.servicedetail = servicedetail;
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
