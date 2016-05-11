package com.portal.wechat.service;

import com.portal.wechat.BaseService;
import com.portal.wechat.bean.WechatTransferReqData;

public class WechatTransferService extends BaseService
{
    
    public WechatTransferService(String api)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        super(api);
    }
    
    /**
     * 请求提现服务
     * 
     * @param wechatTransferReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(WechatTransferReqData wechatTransferReqData)
        throws Exception
    {
        
        // --------------------------------------------------------------------
        // 发送HTTPS的Post请求到API地址
        // --------------------------------------------------------------------
        String responseString = sendPost(wechatTransferReqData);
        
        return responseString;
    }
    
}
