package com.portal.wechat.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.portal.wechat.Configure;
import com.portal.wechat.RandomStringGenerator;
import com.portal.wechat.Signature;

/**
 * 请求被扫支付API需要提交的数据
 */
public class WechatPayReqData
{
    
    // 每个字段具体的意思请查看API文档
    private String appid;
    
    private String mch_id;
    
    private String device_info;
    
    private String nonce_str;
    
    private String sign;
    
    private String body;
    
    private String detail;
    
    private String attach;
    
    private String out_trade_no;
    
    private int total_fee = 0;
    
    private String spbill_create_ip;
    
    private String time_start;
    
    private String time_expire;
    
    private String goods_tag;
    
    private String trade_type;
    
    private String product_id;
    
    private String limit_pay;
    
    private String openid;
    
    private String notify_url;
    
    /**
     * @param body 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
     * @param detail 商品名称明细列表
     * @param outTradeNo 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
     * @param totalFee 订单总金额，单位为“分”，只能整数
     * @param deviceInfo 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
     * @param spBillCreateIP 订单生成的机器IP
     * @param goodsTag 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
     * @param tradeType 交易类型 取值如下：JSAPI，NATIVE，APP
     * @param deviceInfo 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
     * @param notifyUrl 接收微信支付异步通知回调地址
     * @param openId 用户识别标识
     */
    public WechatPayReqData(String body, String detail, String outTradeNo, int totalFee, String spBillCreateIP,
        String goodsTag, String tradeType, String deviceInfo, String notifyUrl, String openId)
    {
        
        // 微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(Configure.getAppid());
        
        // 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(Configure.getMchid());
        // "NATIVE"
        setTrade_type(tradeType);
        
        // 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        setBody(body);
        
        // 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回，有助于商户自己可以注明该笔消费的具体内容，方便后续的运营和记录
        setDetail(detail);
        
        // 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        setOut_trade_no(outTradeNo);
        
        // 订单总金额，单位为“分”，只能整数
        setTotal_fee(totalFee);
        
        // 订单生成的机器IP
        setSpbill_create_ip(spBillCreateIP);
        
        // 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
        setGoods_tag(goodsTag);
        
        // 随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
        
        setDevice_info(deviceInfo);
        
        setNotify_url(notifyUrl);
        
        setOpenid(openId);
        
        // 根据API给的签名规则进行签名
        String sign = Signature.getSign(toMap());
        setSign(sign);// 把签名数据设置到Sign这个属性中
        
    }
    
    public String getAppid()
    {
        return appid;
    }
    
    public void setAppid(String appid)
    {
        this.appid = appid;
    }
    
    public String getMch_id()
    {
        return mch_id;
    }
    
    public void setMch_id(String mch_id)
    {
        this.mch_id = mch_id;
    }
    
    public String getDevice_info()
    {
        return device_info;
    }
    
    public void setDevice_info(String device_info)
    {
        this.device_info = device_info;
    }
    
    public String getNonce_str()
    {
        return nonce_str;
    }
    
    public void setNonce_str(String nonce_str)
    {
        this.nonce_str = nonce_str;
    }
    
    public String getSign()
    {
        return sign;
    }
    
    public void setSign(String sign)
    {
        this.sign = sign;
    }
    
    public String getBody()
    {
        return body;
    }
    
    public void setBody(String body)
    {
        this.body = body;
    }
    
    public String getAttach()
    {
        return attach;
    }
    
    public void setAttach(String attach)
    {
        this.attach = attach;
    }
    
    public String getOut_trade_no()
    {
        return out_trade_no;
    }
    
    public void setOut_trade_no(String out_trade_no)
    {
        this.out_trade_no = out_trade_no;
    }
    
    public int getTotal_fee()
    {
        return total_fee;
    }
    
    public void setTotal_fee(int total_fee)
    {
        this.total_fee = total_fee;
    }
    
    public String getSpbill_create_ip()
    {
        return spbill_create_ip;
    }
    
    public void setSpbill_create_ip(String spbill_create_ip)
    {
        this.spbill_create_ip = spbill_create_ip;
    }
    
    public String getTime_start()
    {
        return time_start;
    }
    
    public void setTime_start(String time_start)
    {
        this.time_start = time_start;
    }
    
    public String getTime_expire()
    {
        return time_expire;
    }
    
    public void setTime_expire(String time_expire)
    {
        this.time_expire = time_expire;
    }
    
    public String getGoods_tag()
    {
        return goods_tag;
    }
    
    public void setGoods_tag(String goods_tag)
    {
        this.goods_tag = goods_tag;
    }
    
    public String getDetail()
    {
        return detail;
    }
    
    public void setDetail(String detail)
    {
        this.detail = detail;
    }
    
    public String getTrade_type()
    {
        return trade_type;
    }
    
    public void setTrade_type(String trade_type)
    {
        this.trade_type = trade_type;
    }
    
    public String getProduct_id()
    {
        return product_id;
    }
    
    public void setProduct_id(String product_id)
    {
        this.product_id = product_id;
    }
    
    public String getLimit_pay()
    {
        return limit_pay;
    }
    
    public void setLimit_pay(String limit_pay)
    {
        this.limit_pay = limit_pay;
    }
    
    public String getOpenid()
    {
        return openid;
    }
    
    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    
    public String getNotify_url()
    {
        return notify_url;
    }
    
    public void setNotify_url(String notify_url)
    {
        this.notify_url = notify_url;
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
                    map.put(field.getName(), obj);
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
    
}
