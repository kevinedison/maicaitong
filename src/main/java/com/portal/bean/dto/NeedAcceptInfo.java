package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.NeedAccept;

public class NeedAcceptInfo extends NeedAccept
{
    // 接单者信息
    private SimpUserInfo acpUserInfo;
    
    // 求购者信息
    private SimpUserInfo needUserInfo;
    
    public NeedAcceptInfo()
    {
    }
    
    public NeedAcceptInfo(NeedAccept needAccept)
    {
        BeanUtils.copyProperties(needAccept, this);
    }
    
    public SimpUserInfo getAcpUserInfo()
    {
        return acpUserInfo;
    }
    
    public void setAcpUserInfo(SimpUserInfo acpUserInfo)
    {
        this.acpUserInfo = acpUserInfo;
    }
    
    public SimpUserInfo getNeedUserInfo()
    {
        return needUserInfo;
    }
    
    public void setNeedUserInfo(SimpUserInfo needUserInfo)
    {
        this.needUserInfo = needUserInfo;
    }
    
}
