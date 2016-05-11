package com.portal.wechat.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.portal.bean.Request;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.wechat.bean.WechatPayRefundInfo;

public class WechatPayRefundRequest extends Request
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 54918082817361053L;
    
    /**
     * 订单号
     */
    @NotNull
    private String orderId;
    
    @NotNull
    private Integer refundFee;
    
    public WechatPayRefundInfo toEntity(String operator)
    {
        WechatPayRefundInfo wechatPayRefundInfo = new WechatPayRefundInfo();
        
        String refundId = BaseConstant.REFUND_PREFIX + Identities.getSystemId();
        wechatPayRefundInfo.setId(refundId);
        wechatPayRefundInfo.setOut_trade_no(orderId);
        wechatPayRefundInfo.setRefund_fee(refundFee);
        
        wechatPayRefundInfo.setOperator(operator);
        wechatPayRefundInfo.setOperateTime(new Date());
        
        wechatPayRefundInfo.setStatus(0);
        return wechatPayRefundInfo;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public Integer getRefundFee()
    {
        return refundFee;
    }
    
    public void setRefundFee(Integer refundFee)
    {
        this.refundFee = refundFee;
    }
    
}
