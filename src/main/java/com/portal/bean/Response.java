package com.portal.bean;

import com.portal.common.context.BaseContext;

public class Response extends Model
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 8775508366956939838L;
    
    private String code = "000000";
    
    private String msg = "操作成功";
    
    public boolean isSuccess()
    {
        return true;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
        this.msg = BaseContext.getMessage(code);
    }
    
    public String getMsg()
    {
        return msg;
    }
    
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
