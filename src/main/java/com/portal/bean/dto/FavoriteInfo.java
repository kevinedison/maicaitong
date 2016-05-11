package com.portal.bean.dto;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.Favorite;

public class FavoriteInfo<T> extends Favorite
{
    
    private T content;
    
    private SimpUserInfo simpUser;
    
    public FavoriteInfo()
    {
        
    }
    
    public FavoriteInfo(Favorite favorite)
    {
        BeanUtils.copyProperties(favorite, this);
    }
    
    public T getContent()
    {
        return content;
    }
    
    public void setContent(T content)
    {
        this.content = content;
    }
    
    public SimpUserInfo getSimpUser()
    {
        return simpUser;
    }
    
    public void setSimpUser(SimpUserInfo simpUser)
    {
        this.simpUser = simpUser;
    }
    
}
