package com.portal.bean.entity.base;

import java.util.Date;

public abstract class OperateEntity extends IdEntity
{
    // 状态
    private String  remark;
    
    // 状态
    private Integer status;
    
    // 最后一次修改人
    private String operator;
    
    // 最后一次修改时间
    private Date operateTime;
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getOperator()
    {
        return operator;
    }
    
    public void setOperator(String operator)
    {
        this.operator = operator;
    }
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
