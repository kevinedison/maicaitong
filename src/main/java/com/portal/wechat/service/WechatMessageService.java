package com.portal.wechat.service;

import com.portal.wechat.BaseService;
import com.portal.wechat.bean.MessageTemplate;

public class WechatMessageService extends BaseService
{
    
    public WechatMessageService(String api)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        super(api);
    }
    
    /**
     * 请求推送消息
     * 
     * @param message 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(MessageTemplate message)
        throws Exception
    {
        
        // --------------------------------------------------------------------
        // 发送HTTPS的Post请求到API地址
        // --------------------------------------------------------------------
        String responseString = sendPostJson(message);
        
        return responseString;
    }
    
}
