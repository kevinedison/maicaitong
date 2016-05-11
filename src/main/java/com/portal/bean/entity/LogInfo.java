package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class LogInfo extends PagingEntity
{
    /**
     * 页面URL
     */
    private String pageUrl;
    
    /**
     * 客户端IP
     */
    private String clientIp;
    
    /**
     * 服务端IP
     */
    private String hostIp;
    
    /**
     * 请求参数
     */
    private String reqParam;
    
    /**
     * 请求ID
     */
    private String reqId;
    
    public String getPageUrl()
    {
        return pageUrl;
    }
    
    public void setPageUrl(String pageUrl)
    {
        this.pageUrl = pageUrl;
    }
    
    public String getClientIp()
    {
        return clientIp;
    }
    
    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
    }
    
    public String getHostIp()
    {
        return hostIp;
    }
    
    public void setHostIp(String hostIp)
    {
        this.hostIp = hostIp;
    }
    
    public String getReqParam()
    {
        return reqParam;
    }
    
    public void setReqParam(String reqParam)
    {
        this.reqParam = reqParam;
    }
    
    public String getReqId()
    {
        return reqId;
    }
    
    public void setReqId(String reqId)
    {
        this.reqId = reqId;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("clientIp=")
            .append(clientIp)
            .append(" hostIp=")
            .append(hostIp)
            .append(" pageUrl=")
            .append(pageUrl)
            .append(" reqParam=")
            .append(reqParam)
            .append(" reqid=")
            .append(reqId)
            .append(" operator=")
            .append(this.getOperator())
            .append(" operatorTime=")
            .append(this.getOperateTime());
        return sb.toString();
    }
    
}
