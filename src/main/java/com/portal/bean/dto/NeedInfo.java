package com.portal.bean.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.Need;

public class NeedInfo extends Need
{
    private SimpUserInfo userInfo;
    
    private List<NeedAcceptInfo> needAcceptList;
    
    private Boolean acpFlag;
    
    public NeedInfo()
    {
    }
    
    public NeedInfo(Need need)
    {
        BeanUtils.copyProperties(need, this);
    }
    
    public SimpUserInfo getUserInfo()
    {
        return userInfo;
    }
    
    public void setUserInfo(SimpUserInfo userInfo)
    {
        this.userInfo = userInfo;
    }
    
    public List<NeedAcceptInfo> getNeedAcceptList()
    {
        return needAcceptList;
    }
    
    public void setNeedAcceptList(List<NeedAcceptInfo> needAcceptList)
    {
        this.needAcceptList = needAcceptList;
    }
    
    public Boolean getAcpFlag()
    {
        return acpFlag;
    }
    
    public void setAcpFlag(Boolean acpFlag)
    {
        this.acpFlag = acpFlag;
    }
    
}
