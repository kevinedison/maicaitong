package com.portal.bean.entity;

import org.springframework.beans.BeanUtils;

import com.portal.bean.dto.UserInfo;

public class User extends SimpUser
{
    private String certificateNo;
    
    private String certificate;
    
    private String account;
    
    private String pwd;
    
    private String recipient;
    
    private Double balance;
    
    public UserInfo toUserInfo()
    {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(this, userInfo);
        return userInfo;
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
    
    public String getAccount()
    {
        return account;
    }
    
    public void setAccount(String account)
    {
        this.account = account;
    }
    
    public String getPwd()
    {
        return pwd;
    }
    
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    public String getRecipient()
    {
        return recipient;
    }
    
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }
    
    public Double getBalance()
    {
        return balance;
    }
    
    public void setBalance(Double balance)
    {
        this.balance = balance;
    }
    
}