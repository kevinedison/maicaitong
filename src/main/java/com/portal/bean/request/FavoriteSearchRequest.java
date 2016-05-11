package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.Favorite;

public class FavoriteSearchRequest extends RequestPage
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    private String userId;
    
    private String targetId;
    
    public Favorite toEntity()
    {
        Favorite favorite = new Favorite();
        BeanUtils.copyProperties(this, favorite);
        
        favorite.setOffset(getOffset());
        favorite.setLimit(getLimit());
        
        return favorite;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getTargetId()
    {
        return targetId;
    }
    
    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }
    
}
