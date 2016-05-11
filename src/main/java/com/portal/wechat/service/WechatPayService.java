package com.portal.wechat.service;

import com.portal.wechat.BaseService;
import com.portal.wechat.bean.WechatPayReqData;

public class WechatPayService extends BaseService
{
    
    public WechatPayService(String api)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        super(api);
    }
    
    /**
     * 请求支付服务
     * 
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(WechatPayReqData wechatPayReqData)
        throws Exception
    {
        
        // --------------------------------------------------------------------
        // 发送HTTPS的Post请求到API地址
        // --------------------------------------------------------------------
        String responseString = sendPost(wechatPayReqData);
        
        return responseString;
    }
    
}
