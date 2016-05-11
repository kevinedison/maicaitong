package com.portal.bean.request;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.Location;
import com.portal.common.util.Identities;
import com.portal.constant.StatusConstant;

public class LocationSaveRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1442990138141764246L;
	
	private String id;
	
    private String countryId;
    
    private String name;
    
    private String code;
    
    private String title;
    
    private String coordinate;
    
    private String phoneCode;
    
    private String cover;
    
    private String picture;
    
    private String description;
	
	public Location toEntity(String operator) {
		Location location = new Location();
		BeanUtils.copyProperties(this, location);
		
		location.setOperator(operator);
		location.setOperateTime(new Date());
        
		location.setStatus(StatusConstant.STATUS_ONLINE);
		return location;
	}

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }

    public String getPhoneCode()
    {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode)
    {
        this.phoneCode = phoneCode;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
