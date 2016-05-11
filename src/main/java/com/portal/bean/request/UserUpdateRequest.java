package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.User;

public class UserUpdateRequest extends Request
{
    /**
     * 
     */
    private static final long serialVersionUID = -404071043021358806L;
    
    private String nickName;
    
    private String avatar;
    
    private Integer gender;
    
    private String hometown;
    
    private String age;
    
    private String introduce;
    
    private String constellation;
    
    private String servicetype;
    
    private String recipient;
    
    public User toEntity(String operator)
    {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        user.setOperator(operator);
        return user;
    }
    
    public String getNickName()
    {
        return nickName;
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
    public String getAvatar()
    {
        return avatar;
    }
    
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    
    public Integer getGender()
    {
        return gender;
    }
    
    public void setGender(Integer gender)
    {
        this.gender = gender;
    }
    
    public String getHometown()
    {
        return hometown;
    }
    
    public void setHometown(String hometown)
    {
        this.hometown = hometown;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public String getIntroduce()
    {
        return introduce;
    }
    
    public void setIntroduce(String introduce)
    {
        this.introduce = introduce;
    }
    
    public String getConstellation()
    {
        return constellation;
    }
    
    public void setConstellation(String constellation)
    {
        this.constellation = constellation;
    }
    
    public String getServicetype()
    {
        return servicetype;
    }
    
    public void setServicetype(String servicetype)
    {
        this.servicetype = servicetype;
    }
    
    public String getRecipient()
    {
        return recipient;
    }
    
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }
    
}
