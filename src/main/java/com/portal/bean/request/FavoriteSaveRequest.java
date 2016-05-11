package com.portal.bean.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.Favorite;
import com.portal.constant.StatusConstant;

public class FavoriteSaveRequest extends Request
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -404071043021358806L;
    
    @NotNull
    private String targetId;
    
    private Date operateTime;
    
    public Favorite toEntity(String operator)
    {
        Favorite favorite = new Favorite();
        BeanUtils.copyProperties(this, favorite);
        
        favorite.setOperator(operator);
        favorite.setOperateTime(new Date());
        
        favorite.setStatus(StatusConstant.STATUS_ONLINE);
        return favorite;
    }
    
    public String getTargetId()
    {
        return targetId;
    }
    
    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
}
