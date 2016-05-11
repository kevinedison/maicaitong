package com.portal.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.entity.Bill;
import com.portal.bean.entity.Need;
import com.portal.bean.entity.NeedAccept;
import com.portal.bean.entity.Order;
import com.portal.bean.entity.User;
import com.portal.bean.entity.UserService;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.constant.BusinessCode;
import com.portal.constant.StatusConstant;
import com.portal.manager.UserManager;
import com.portal.mapper.NeedMapper;
import com.portal.mapper.OrderMapper;
import com.portal.mapper.ServiceMapper;
import com.portal.mapper.UserMapper;
import com.portal.service.BillService;
import com.portal.service.OrderService;
import com.portal.wechat.bean.WechatPayNotifyResult;
import com.portal.wechat.bean.WechatPayRefundInfo;

@Service
public class OrderServiceImpl implements OrderService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    ServiceMapper serviceMapper;
    
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    BillService billService;
    
    @Autowired
    NeedMapper needMapper;
    
    @Override
    public Integer updatePayInfo(Order entity, WechatPayNotifyResult wechatPayNotifyResult)
    {
        int result = orderMapper.updateById(entity);
        
        // 更新求购状态
        if (entity.getServiceId().startsWith("N"))
        {
            updateNeedInfo(entity);
        }
        
        // 只要已付款就表示订单生成，更新服务订单数，后续如果退款订单数要-1
        LOGGER.info("begin update service count....");
        UserService userService = new UserService();
        userService.setId(entity.getServiceId());
        userService.setOrdercount(1);
        serviceMapper.updateById(userService);
        
        LOGGER.info("end update service....");
        
        // 更新销售订单
        LOGGER.info("begin update sale order count....");
        User user = new User();
        user.setUserId(entity.getSaleId());
        user.setOrderCount(1);
        userMapper.updateById(user);
        LOGGER.info("end update sale order count....");
        
        orderMapper.savePayInfo(wechatPayNotifyResult);
        return result;
    }
    
    @Override
    public WechatPayNotifyResult selectPayResult(WechatPayNotifyResult entity)
    {
        return orderMapper.selectPayResult(entity);
    }
    
    @Override
    public Long saveRefundInfo(WechatPayRefundInfo entity)
    {
        return orderMapper.saveRefundInfo(entity);
    }
    
    @Override
    public Long save(Order entity)
    {
        return orderMapper.save(entity);
    }
    
    @Override
    public Integer deleteById(Order entity)
    {
        return orderMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(Order entity)
    {
        return orderMapper.updateById(entity);
    }
    
    @Override
    public Order selectById(String id)
    {
        return orderMapper.selectById(id);
    }
    
    @Override
    public Order selectOne(Order entity)
    {
        return orderMapper.selectOne(entity);
    }
    
    @Override
    public PagingData<Order> selectByIndex(Order entity)
    {
        PagingData<Order> pagingData = new PagingData<Order>();
        int count = orderMapper.selectByIndexCount(entity);
        if (count > 0)
        {
            pagingData.setData(orderMapper.selectByIndex(entity));
        }
        pagingData.setCount(count);
        return pagingData;
    }
    
    private Response settleOrder(Order order)
    {
        Response response = new Response();
        
        try
        {
            Bill bill = new Bill();
            
            bill.setId(BaseConstant.BILL_PREFIX + Identities.getSystemId());
            bill.setOperator(order.getSaleId());
            bill.setUserId(order.getSaleId());
            bill.setOrderId(order.getId());
            bill.setOrderFee(order.getTotalPrice());
            bill.setPayInfo("orderpay");
            bill.setPayAmount(order.getTotalPrice());
            
            billService.save(bill);
        }
        catch (Exception e)
        {
            response.setCode(BusinessCode.SETTLE_FAIL.getCode());
        }
        
        return response;
    }
    
    @Override
    public Response directPay(Order order)
    {
        return settleOrder(order);
    }
    
    private void updateNeedInfo(Order entity)
    {
        // 获取当前接单列表
        List<NeedAccept> acceptList = needMapper.selectAcpListByNeedId(entity.getServiceId());
        
        // 接单人ID
        String acpUserId = entity.getSaleId();
        
        for (NeedAccept temp : acceptList)
        {
            // ID 匹配 更新状态为接受
            if (temp.getAcpUser().equals(acpUserId))
            {
                temp.setStatus(StatusConstant.NEED_DEAL);
            }
            else
            {
                temp.setStatus(StatusConstant.NEED_FAIL);
            }
        }
        
        // 批量更新
        needMapper.updateAcpList(acceptList);
        
        // 更新need 用户已接单
        Need need = new Need();
        need.setId(entity.getServiceId());
        need.setStatus(StatusConstant.NEED_DEAL);
        needMapper.updateById(need);
    }
}
