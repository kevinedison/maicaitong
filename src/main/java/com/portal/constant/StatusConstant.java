package com.portal.constant;

public interface StatusConstant
{
    /**
     * 服务草稿
     */
    Integer STATUS_DRAFT = -1;
    
    /**
     * 预发布状态
     */
    Integer STATUS_PRE = 0;
    
    /**
     * 在线
     */
    Integer STATUS_ONLINE = 1;
    
    /**
     * 下线
     */
    Integer STATUS_OFFLINE = 2;
    
    /**
     * 求购生成的临时订单
     */
    Integer ORDER_NEED = 0;
    
    /**
     * 提交订单，待付款
     */
    Integer ORDER_WAIT_PAYMENT = 1;
    
    /**
     * 已付款，待发货
     */
    Integer ORDE_WAIT_SENDOUT = 2;
    
    /**
     * 待收货(卖家更改 已发货)
     */
    Integer ORDER_WAIT_RECEIVE = 3;
    
    /**
     * 已收货 待结算(买家更改 确认收货)
     */
    Integer ORDER_WAIT_SETTLEMENT = 4;
    
    /**
     * 已结算
     */
    Integer ORDER_ALREADY_SETTLEMENT = 6;
    
    /**
     * 已申请退款
     */
    Integer ORDER_APPLY_REFUND = 7;
    
    /**
     * 退款成功
     */
    Integer ORDER_ALREADY_REFUND = 8;
    
    /**
     * 订单取消
     */
    Integer ORDER_CANCEL = 9;
    
    /**
     * 不推荐
     */
    Integer RECOMMENDATION_NOT = 0;
    
    /**
     * 不推荐
     */
    Integer RECOMMENDATION_YES = 1;
    
    /**
     * NEED 求购中
     */
    Integer NEED_DEPLOY = 1;
    
    /**
     * NEED 已接单
     */
    Integer NEED_DEAL = 2;
    
    /**
     * NEED 失败
     */
    Integer NEED_FAIL = 3;
    
    /**
     * NEED 成交
     */
    Integer NEED_SUCCESS = 4;
}
