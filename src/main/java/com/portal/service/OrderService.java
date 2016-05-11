package com.portal.service;

import org.springframework.transaction.annotation.Transactional;

import com.portal.bean.Response;
import com.portal.bean.entity.Order;
import com.portal.wechat.bean.WechatPayNotifyResult;
import com.portal.wechat.bean.WechatPayRefundInfo;

public interface OrderService extends CrudService<Order>
{
    @Transactional
    Integer updatePayInfo(Order order, WechatPayNotifyResult wechatPayNotifyResult);
    
    WechatPayNotifyResult selectPayResult(WechatPayNotifyResult entity);
    
    @Transactional
    Long saveRefundInfo(WechatPayRefundInfo entity);
    
    @Transactional
    Response directPay(Order order);
}
