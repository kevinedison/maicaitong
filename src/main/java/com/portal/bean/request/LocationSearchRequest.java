package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestSearch;
import com.portal.bean.entity.Location;

public class LocationSearchRequest extends RequestSearch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1442990138141764246L;
	
	private Integer recommendation;
	
	public Location toEntity() {
		Location location = new Location();
		BeanUtils.copyProperties(this, location);
		return location;
	}

    public Integer getRecommendation()
    {
        return recommendation;
    }

    public void setRecommendation(Integer recommendation)
    {
        this.recommendation = recommendation;
    }
}
