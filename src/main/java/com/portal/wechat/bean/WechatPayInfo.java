package com.portal.wechat.bean;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.portal.wechat.Configure;
import com.portal.wechat.RandomStringGenerator;
import com.portal.wechat.Signature;

/**
 * H5调起支付API
 */
public class WechatPayInfo
{
    
    // 每个字段具体的意思请查看API文档
    private String appId;
    
    private String nonceStr;
    
    private String timeStamp;
    
    private String packages;
    
    private String signType;
    
    private String paySign;
    
    /**
     * @param body 预支付ID
     */
    public WechatPayInfo(String body)
    {
        // 微信分配的公众号ID（开通公众号之后可以获取到）
        setAppId(Configure.getAppid());
        
        // 随机字符串，不长于32 位
        setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
        setTimeStamp(String.valueOf(new Date().getTime()));
        setSignType("MD5");
        this.setPackages(body);
        
        // 根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        setPaySign(sign);// 把签名数据设置到Sign这个属性中
        
    }
    
    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            Object obj;
            try
            {
                obj = field.get(this);
                if (obj != null)
                {
                    // 特殊处理java关键字
                    if ("packages".equals(field.getName()))
                    {
                        map.put("package", obj);
                    }
                    else
                    {
                        map.put(field.getName(), obj);
                    }
                    
                }
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    public String getAppId()
    {
        return appId;
    }
    
    public void setAppId(String appId)
    {
        this.appId = appId;
    }
    
    public String getNonceStr()
    {
        return nonceStr;
    }
    
    public void setNonceStr(String nonceStr)
    {
        this.nonceStr = nonceStr;
    }
    
    public String getTimeStamp()
    {
        return timeStamp;
    }
    
    public void setTimeStamp(String timeStamp)
    {
        this.timeStamp = timeStamp;
    }
    
    public String getPackages()
    {
        return packages;
    }
    
    public void setPackages(String packages)
    {
        this.packages = packages;
    }
    
    public String getSignType()
    {
        return signType;
    }
    
    public void setSignType(String signType)
    {
        this.signType = signType;
    }
    
    public String getPaySign()
    {
        return paySign;
    }
    
    public void setPaySign(String paySign)
    {
        this.paySign = paySign;
    }
    
}
