package com.portal.bean.request;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.User;

public class UserAuthRequest extends Request
{
    /**
     * 
     */
    private static final long serialVersionUID = -404071043021358806L;
    
    @NotNull
    private String certificateNo;
    
    @NotNull
    private String certificate;
    
    @NotNull
    private String realName;
    
    @NotNull
    private String country;
    
    @NotNull
    private String identity;
    
    public User toEntity(UserInfo userInfo)
    {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        user.setOperator(userInfo.getUserId());
        // 如果已认证则不进行变更处理
        if (1 != userInfo.getIdentityAuth())
        {
            user.setIdentityAuth(2);
        }
        
        return user;
    }
    
    public String getCertificateNo()
    {
        return certificateNo;
    }
    
    public void setCertificateNo(String certificateNo)
    {
        this.certificateNo = certificateNo;
    }
    
    public String getCertificate()
    {
        return certificate;
    }
    
    public void setCertificate(String certificate)
    {
        this.certificate = certificate;
    }
    
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
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
    
}
