package com.portal.manager.rate;

public class RateResult {
    
    private String errNum;
    private String  errMsg;
    private RateCurrency retData;
    
    public String getErrNum()
    {
        return errNum;
    }
    public void setErrNum(String errNum)
    {
        this.errNum = errNum;
    }
    public String getErrMsg()
    {
        return errMsg;
    }
    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }
    public RateCurrency getRetData()
    {
        return retData;
    }
    public void setRetData(RateCurrency retData)
    {
        this.retData = retData;
    }
}

