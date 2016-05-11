package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.Location;
import com.portal.bean.entity.SimpUser;

public class SimpUserInfo extends SimpUser
{
    private Location countryInfo;
    
    public SimpUserInfo()
    {
    }
    
    public SimpUserInfo(SimpUser simpUser)
    {
        BeanUtils.copyProperties(simpUser, this);
    }
    
    public Location getCountryInfo()
    {
        return countryInfo;
    }
    
    public void setCountryInfo(Location countryInfo)
    {
        this.countryInfo = countryInfo;
    }
    
}
