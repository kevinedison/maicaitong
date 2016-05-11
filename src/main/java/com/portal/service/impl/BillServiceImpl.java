package com.portal.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.Bill;
import com.portal.bean.entity.Order;
import com.portal.bean.entity.User;
import com.portal.common.context.BaseContext;
import com.portal.constant.StatusConstant;
import com.portal.manager.UserManager;
import com.portal.mapper.BillMapper;
import com.portal.mapper.OrderMapper;
import com.portal.mapper.UserMapper;
import com.portal.rest.PayController;
import com.portal.service.BillService;
import com.portal.wechat.Configure;
import com.portal.wechat.Util;
import com.portal.wechat.bean.WechatTransferReqData;
import com.portal.wechat.bean.WechatTransferResData;
import com.portal.wechat.service.WechatTransferService;

@Service
public class BillServiceImpl implements BillService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);
    
    @Autowired
    BillMapper billMapper;
    
    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    UserManager userManager;
    
    @Override
    public Long save(Bill entity)
    {
        // 生成账单，同时如果是订单，同时要将订单修改为已结算
        if (!StringUtils.isEmpty(entity.getOrderId()))
        {
            Order order = new Order();
            order.setId(entity.getOrderId());
            order.setStatus(StatusConstant.ORDER_ALREADY_SETTLEMENT);
            orderMapper.updateById(order);
        }
        
        // 保存订单
        Long result = billMapper.save(entity);
        
        // 更新用户信息
        UserInfo userInfo = userManager.getUserInfo(entity.getUserId(), Boolean.FALSE);
        User user = new User();
        user.setUserId(entity.getUserId());
        user.setBalance(userInfo.getBalance() + entity.getPayAmount());
        
        userMapper.updateById(user);
        
        // 刷新缓存
        userManager.refreshUserInfo(entity.getUserId());
        
        return result;
    }
    
    @Override
    public Integer deleteById(Bill entity)
    {
        return null;
    }
    
    @Override
    public Integer updateById(Bill entity)
    {
        return null;
    }
    
    @Override
    public Bill selectById(String id)
    {
        return null;
    }
    
    @Override
    public Bill selectOne(Bill entity)
    {
        return null;
    }
    
    @Override
    public PagingData<Bill> selectByIndex(Bill entity)
    {
        PagingData<Bill> pagingData = new PagingData<Bill>();
        int count = billMapper.selectByIndexCount(entity);
        pagingData.setCount(count);
        
        if (count > 0)
        {
            pagingData.setData(billMapper.selectByIndex(entity));
        }
        
        return pagingData;
    }
    
    @Override
    public WechatTransferResData transfer(Bill bill)
    {
        // 保存订单
        billMapper.save(bill);
        
        // 更新用户信息
        UserInfo userInfo = userManager.getUserInfo(bill.getUserId(), Boolean.FALSE);
        // 发送提现请求到微信
        WechatTransferResData resData =
            sendTransfer(bill.getId(), userInfo.getUserId(), userInfo.getRealName(), bill.getPayAmount(), "支付买手");
        
        // 微信提现异常则需要回滚
        if (null != resData && "SUCCESS".equals(resData.getResult_code()) && "SUCCESS".equals(resData.getReturn_code()))
        {
            User user = new User();
            user.setUserId(bill.getUserId());
            user.setBalance(userInfo.getBalance() - bill.getPayAmount());
            
            userMapper.updateById(user);
            
            // 刷新缓存
            userManager.refreshUserInfo(bill.getUserId());
        }
        // 失败 需要将订单回滚
        else
        {
            Bill deleteBill = new Bill();
            deleteBill.setId(bill.getId());
            deleteBill.setUserId(userInfo.getUserId());
            billMapper.deleteById(deleteBill);
        }
        
        return resData;
    }
    
    private WechatTransferResData sendTransfer(String billId, String openId, String realName, Double amount, String desc)
    {
        Map<String, String> weixinConfig = (Map<String, String>)BaseContext.getBean("wechatConfig");
        Configure.setAppID(weixinConfig.get("appId"));
        Configure.setMchID(weixinConfig.get("mchId"));
        // API密钥
        Configure.setKey(weixinConfig.get("apiKey"));
        
        System.out.println(this.getClass().getResource("/"));
        
        Configure.setCertLocalPath(this.getClass().getResource("/").getPath() + "/cert/apiclient_cert.p12");
        Configure.setCertPassword(weixinConfig.get("mchId"));
        WechatTransferResData resData = null;
        int totalPriceFen = new Double(amount * 100).intValue();
        try
        {
            WechatTransferService transferService = new WechatTransferService(weixinConfig.get("transferUrl"));
            WechatTransferReqData transfer = new WechatTransferReqData(billId, openId, realName, totalPriceFen, desc);
            String result = transferService.request(transfer);
            
            LOGGER.info("Transfer return:" + result);
            resData = (WechatTransferResData)Util.getObjectFromXML(result, WechatTransferResData.class);
        }
        catch (Exception e)
        {
            LOGGER.error("Send the transfer request to wechat catch an exception", e);
        }
        return resData;
    }
}
