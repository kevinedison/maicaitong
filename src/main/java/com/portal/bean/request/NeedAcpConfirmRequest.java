package com.portal.bean.request;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.portal.bean.IdRequest;
import com.portal.bean.entity.NeedAccept;

public class NeedAcpConfirmRequest extends IdRequest
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2889425051312376511L;
    
    @NotNull
    private String needId;
    
    public NeedAccept toEntity(String operator)
    {
        NeedAccept needAcp = new NeedAccept();
        BeanUtils.copyProperties(this, needAcp);
        return needAcp;
    }
    
    public String getNeedId()
    {
        return needId;
    }
    
    public void setNeedId(String needId)
    {
        this.needId = needId;
    }
    
}
