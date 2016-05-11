package com.portal.wechat.bean;

import com.portal.bean.entity.base.PagingEntity;

public class WechatPayRefundInfo extends PagingEntity
{
    
    /**
     * 订单号
     */
    private String out_trade_no;
    
    /**
     * 微信交易单号
     */
    private String transaction_id;
    
    private Integer total_fee;
    
    private Integer refund_fee;
    
    public String getOut_trade_no()
    {
        return out_trade_no;
    }
    
    public void setOut_trade_no(String out_trade_no)
    {
        this.out_trade_no = out_trade_no;
    }
    
    public String getTransaction_id()
    {
        return transaction_id;
    }
    
    public void setTransaction_id(String transaction_id)
    {
        this.transaction_id = transaction_id;
    }
    
    public Integer getTotal_fee()
    {
        return total_fee;
    }
    
    public void setTotal_fee(Integer total_fee)
    {
        this.total_fee = total_fee;
    }
    
    public Integer getRefund_fee()
    {
        return refund_fee;
    }
    
    public void setRefund_fee(Integer refund_fee)
    {
        this.refund_fee = refund_fee;
    }
    
}
