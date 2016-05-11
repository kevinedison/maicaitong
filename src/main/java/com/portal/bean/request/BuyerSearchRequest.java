package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.User;

public class BuyerSearchRequest extends RequestPage
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private String country;
    
    private String identity;
    
    private String realName;
    
    private Integer recommendation;
    
    private String nickName;
    
    public User toEntity()
    {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        
        // 查询买家
        user.setIdentityAuth(1);
        user.setOffset(getOffset());
        user.setLimit(getLimit());
        user.setStatus(1);
        return user;
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    public String getIdentity()
    {
        return identity;
    }
    
    public void setIdentity(String identity)
    {
        this.identity = identity;
    }
    
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    public Integer getRecommendation()
    {
        return recommendation;
    }
    
    public void setRecommendation(Integer recommendation)
    {
        this.recommendation = recommendation;
    }
    
    public String getNickName()
    {
        return nickName;
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
}
