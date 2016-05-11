package com.portal.mapper;

import java.util.List;

import com.portal.bean.entity.Order;
import com.portal.wechat.bean.WechatPayNotifyResult;
import com.portal.wechat.bean.WechatPayRefundInfo;

public interface OrderMapper extends CrudMapper<Order>
{
    Long savePayInfo(WechatPayNotifyResult wechatPayNotifyResult);
    
    WechatPayNotifyResult selectPayResult(WechatPayNotifyResult entity);
    
    Long saveRefundInfo(WechatPayRefundInfo entity);
    
    List<Order> selectOrderCount(Order order);
}
