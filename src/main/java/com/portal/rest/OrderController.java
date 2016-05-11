package com.portal.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.dto.OrderInfo;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.Bill;
import com.portal.bean.entity.Need;
import com.portal.bean.entity.Order;
import com.portal.bean.entity.UserService;
import com.portal.bean.request.OrderSaveRequest;
import com.portal.bean.request.OrderSearchRequest;
import com.portal.bean.request.OrderUpdateRequest;
import com.portal.common.mapper.JsonMapper;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.constant.BusinessCode;
import com.portal.constant.StatusConstant;
import com.portal.manager.SessionManager;
import com.portal.manager.SignatureManager;
import com.portal.manager.UserManager;
import com.portal.service.BillService;
import com.portal.service.NeedService;
import com.portal.service.OrderService;
import com.portal.service.UserServService;
import com.portal.wechat.Util;
import com.portal.wechat.bean.MessageTemplate;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class OrderController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    UserServService userServService;
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    BillService billService;
    
    @Autowired
    SignatureManager signatureManager;
    
    @Autowired
    NeedService needService;
    
    /**
     * <一句话功能简述>订购服务 <功能详细描述>
     * 
     * @param request
     * @param orderSaveRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/order", method = RequestMethod.POST)
    @ResponseBody
    public Response saveUserOrder(HttpServletRequest request, @Valid @RequestBody OrderSaveRequest orderSaveRequest)
    {
        ResponseData<String> response = new ResponseData<>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Order entity = orderSaveRequest.toEntity(userId);
        
        // 根据ServiceID获取Serice
        UserService userService = userServService.selectById(orderSaveRequest.getServiceId());
        
        if (null == userService)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        // 不能订购自己的服务
        if (userService.getUserId().equals(userId))
        {
            response.setCode(BusinessCode.CANNOT_ORDER_SELF.getCode());
            return response;
        }
        JsonMapper mapper = new JsonMapper();
        
        // 设置服务相关数据
        entity.setBuyerId(userId);
        entity.setSaleId(userService.getUserId());
        entity.setServiceId(userService.getId());
        entity.setPrice(userService.getPrice());
        entity.setTotalPrice(userService.getPrice() * entity.getCount());
        entity.setServiceContent(mapper.toJson(userService));
        
        orderService.save(entity);
        
        response.setData(entity.getId());
        
        // 成功发送微信消息
        MessageTemplate message = new MessageTemplate();
        message.setTemplate_id("FID-qLS4zeG6Gsndm6pVa1I1GyyYPk0XsMMMvLt8G7k");
        message.setTouser(userId);
        
        Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
        Map<String, String> sub = new HashMap<String, String>();
        sub.put("value", "您的订单已提交成功");
        sub.put("color", "#173177");
        data.put("first", sub);
        
        sub = new HashMap<String, String>();
        sub.put("value", entity.getId());
        sub.put("color", "#173177");
        data.put("orderID", sub);
        
        sub = new HashMap<String, String>();
        sub.put("value", String.valueOf(entity.getTotalPrice()));
        sub.put("color", "#173177");
        data.put("orderMoneySum", sub);
        
        sub = new HashMap<String, String>();
        sub.put("value", "商品信息：" + userService.getTitle());
        sub.put("color", "#173177");
        data.put("backupFieldName", sub);
        
        sub = new HashMap<String, String>();
        sub.put("value", "如有问题请致电18510213635或直接在微信留言，带点啥将第一时间为您服务！");
        sub.put("color", "#173177");
        data.put("remark", sub);
        message.setData(data);
        Util.sendNotify(message);
        
        return response;
    }
    
    /**
     * 修改订单
     * 
     * @param orderSaveRequest
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/order", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateUserOrder(HttpServletRequest request,
        @Valid @RequestBody OrderUpdateRequest orderUpdateRequest)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Order order = orderUpdateRequest.toEntity(userId);
        
        // 先查询订单是否是自己的
        Order dbOrder = new Order();
        dbOrder.setId(order.getId());
        
        if (3 == order.getStatus())
        {
            dbOrder.setSaleId(userId);
        }
        else if (4 == order.getStatus())
        {
            dbOrder.setBuyerId(userId);
        }
        else
        {
            response.setCode(BusinessCode.PARAMETER_FORMAT_ERROR.getCode());
            return response;
        }
        
        dbOrder = orderService.selectOne(dbOrder);
        
        if (null == dbOrder)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        orderService.updateById(order);
        
        // 订单服务ID
        String serviceId = dbOrder.getServiceId();
        
        if (4 == order.getStatus() && serviceId.startsWith("N"))
        {
            // 更新need
            Need need = new Need();
            need.setStatus(StatusConstant.NEED_SUCCESS);
            need.setId(serviceId);
            needService.updateById(need);
        }
        
        // 发送通知消息
        sendNotify(order.getStatus(), dbOrder);
        
        return response;
    }
    
    /**
     * 撤销订单
     * 
     * @param orderSaveRequest
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/order", method = RequestMethod.DELETE)
    @ResponseBody
    public Response cancelUserOrder(HttpServletRequest request, @Valid String id)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Order order = new Order();
        order.setId(id);
        order.setOperator(userId);
        
        // 先查询订单是否是自己的
        Order dbOrder = orderService.selectOne(order);
        
        if (null == dbOrder)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        // 根据ServiceID获取Serice
        order.setStatus(StatusConstant.ORDER_CANCEL);
        orderService.updateById(order);
        
        return response;
    }
    
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getUserOrder(@Valid OrderSearchRequest orderSearchRequest)
    {
        Order order = orderSearchRequest.toEntity();
        Integer[] statusArr = {2, 3, 4, 6};
        order.setStatusArr(statusArr);
        return getOrderInfo(order);
    }
    
    @RequestMapping(value = "/user/pay/order", method = RequestMethod.GET)
    @ResponseBody
    public Response payBuyOrder(HttpServletRequest request, @Valid String id, String type)
    {
        ResponseData<OrderInfo> response = new ResponseData<>();
        String userId = sessionManager.getSessionUserId(request);
        
        if (StringUtils.isEmpty(id))
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        Order order = new Order();
        order.setId(id);
        
        if ("1".equals(type))
        {
            order.setSaleId(userId);
        }
        else
        {
            order.setBuyerId(userId);
        }
        Order queryOrder = orderService.selectOne(order);
        
        if (null == queryOrder)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        OrderInfo orderInfo = new OrderInfo(queryOrder);
        JsonMapper mapper = new JsonMapper();
        
        orderInfo.setBuyerUser(userManager.getSimpUserInfo(queryOrder.getBuyerId(), Boolean.FALSE));
        orderInfo.setSalerUser(userManager.getSimpUserInfo(queryOrder.getSaleId(), Boolean.FALSE));
        orderInfo.setUserService(mapper.fromJson(queryOrder.getServiceContent(), UserService.class));
        orderInfo.setBuyerInfo((userManager.getUserInfo(queryOrder.getBuyerId(), Boolean.FALSE)).getRecipient());
        response.setData(orderInfo);
        return response;
    }
    
    @RequestMapping(value = "/user/buy/order", method = RequestMethod.GET)
    @ResponseBody
    public Response getUserBuyOrder(HttpServletRequest request, @Valid OrderSearchRequest orderSearchRequest)
    {
        String userId = sessionManager.getSessionUserId(request);
        Order order = orderSearchRequest.toEntity();
        order.setBuyerId(userId);
        
        if (null == order.getStatus())
        {
            Integer[] statusArr = {1, 2, 3, 4, 6, 7, 8, 9};
            order.setStatusArr(statusArr);
        }
        
        return getOrderInfo(order);
        
    }
    
    @RequestMapping(value = "/user/sale/order", method = RequestMethod.GET)
    @ResponseBody
    public Response getUserSaleOrder(HttpServletRequest request, @Valid OrderSearchRequest orderSearchRequest)
    {
        String userId = sessionManager.getSessionUserId(request);
        Order order = orderSearchRequest.toEntity();
        order.setSaleId(userId);
        
        if (null == order.getStatus())
        {
            Integer[] statusArr = {2, 3, 4, 6, 7, 8, 9};
            order.setStatusArr(statusArr);
        }
        else if (4 == order.getStatus())
        {
            order.setPayType(0);
        }
        
        return getOrderInfo(order);
    }
    
    private ResponseData<PagingData<OrderInfo>> getOrderInfo(Order order)
    {
        ResponseData<PagingData<OrderInfo>> response = new ResponseData<>();
        // 如果是查询待评论的订单 需要转变状态
        if (null != order.getStatus() && 99 == order.getStatus())
        {
            Integer[] statusArr = {4, 6, 7, 8};
            order.setStatusArr(statusArr);
            order.setIsComment(0);
            order.setStatus(null);
        }
        
        PagingData<Order> orderData = orderService.selectByIndex(order);
        List<Order> orderList = orderData.getData();
        List<OrderInfo> orderInfoList = new ArrayList<>();
        JsonMapper mapper = new JsonMapper();
        if (!CollectionUtils.isEmpty(orderList))
        {
            for (Order temp : orderList)
            {
                OrderInfo orderInfo = new OrderInfo(temp);
                orderInfo.setBuyerUser(userManager.getSimpUserInfo(temp.getBuyerId(), Boolean.FALSE));
                orderInfo.setSalerUser(userManager.getSimpUserInfo(temp.getSaleId(), Boolean.FALSE));
                
                if (temp.getServiceId().startsWith(BaseConstant.NEED_PREFIX))
                {
                    orderInfo.setUserService(mapper.fromJson(temp.getServiceContent(), Need.class));
                }
                else
                {
                    orderInfo.setUserService(mapper.fromJson(temp.getServiceContent(), UserService.class));
                }
                
                orderInfoList.add(orderInfo);
            }
        }
        PagingData<OrderInfo> orderInfoData = new PagingData<OrderInfo>();
        orderInfoData.setData(orderInfoList);
        orderInfoData.setCount(orderData.getCount());
        response.setData(orderInfoData);
        return response;
    }
    
    @RequestMapping(value = "/user/settle", method = RequestMethod.POST)
    @ResponseBody
    public Response settleOrder(HttpServletRequest request, @Valid @RequestBody Order order)
    {
        ResponseData<String> response = new ResponseData<String>();
        String userId = sessionManager.getSessionUserId(request);
        order.setSaleId(userId);
        order.setStatus(StatusConstant.ORDER_WAIT_SETTLEMENT);
        
        Order queryOrder = orderService.selectOne(order);
        
        if (null == queryOrder)
        {
            response.setCode(BusinessCode.ORDER_CANNOTBE_SETTLE.getCode());
            return response;
        }
        
        Bill bill = new Bill();
        
        bill.setId(BaseConstant.BILL_PREFIX + Identities.getSystemId());
        bill.setOperator(userId);
        bill.setUserId(userId);
        bill.setOrderId(queryOrder.getId());
        bill.setOrderFee(queryOrder.getTotalPrice());
        bill.setPayInfo("orderpay");
        bill.setPayAmount(queryOrder.getTotalPrice());
        
        billService.save(bill);
        sessionManager.refreshUserSession(request, userId);
        response.setData(bill.getId());
        return response;
    }
    
    private void sendNotify(Integer status, Order order)
    {
        String buyerId = order.getBuyerId();
        String saleId = order.getSaleId();
        
        UserInfo buyerInfo = userManager.getUserInfo(buyerId, Boolean.FALSE);
        UserInfo saleInfo = userManager.getUserInfo(saleId, Boolean.FALSE);
        
        String buyerName =
            StringUtils.isEmpty(buyerInfo.getRealName()) ? buyerInfo.getNickName() : buyerInfo.getRealName();
        String saleName = StringUtils.isEmpty(saleInfo.getRealName()) ? saleInfo.getNickName() : saleInfo.getRealName();
        
        MessageTemplate message = new MessageTemplate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // 发货 通知买家
        if (3 == status)
        {
            // 快递公司：{{keyword3.DATA}}
            // 快递单号：{{keyword4.DATA}}
            
            message.setTemplate_id("ZE7nrshBlKHvnpNmr35AhHHT1vYxJ9kxJO_Zx_jXvOo");
            message.setTouser(buyerId);
            
            Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
            Map<String, String> sub = new HashMap<String, String>();
            sub.put("value", buyerName + "您好，您的订单已经发货，请注意查收");
            sub.put("color", "#173177");
            data.put("first", sub);
            
            sub = new HashMap<String, String>();
            sub.put("value", order.getId());
            sub.put("color", "#173177");
            data.put("keyword1", sub);
            
            sub = new HashMap<String, String>();
            sub.put("value", saleName);
            sub.put("color", "#173177");
            data.put("keyword2", sub);
            
            sub = new HashMap<String, String>();
            sub.put("value", sdf.format(new Date()));
            sub.put("color", "#173177");
            data.put("keyword5", sub);
            
            sub = new HashMap<String, String>();
            sub.put("value", "如有问题请致电18510213635或直接在微信留言，带点啥将第一时间为您服务！");
            sub.put("color", "#173177");
            data.put("remark", sub);
            message.setData(data);
        }
        // 收货 通知卖家
        else if (4 == status)
        {
            message.setTemplate_id("rRZawPIOGxbg5WM1lhZQO9eRYEIG9zl5woPhbHePQ_w");
            message.setTouser(saleId);
            JsonMapper mapper = new JsonMapper();
            UserService userService = mapper.fromJson(order.getServiceContent(), UserService.class);
            
            Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
            Map<String, String> sub = new HashMap<String, String>();
            sub.put("value", "您好，商品\"" + userService.getTitle() + "\"用户" + buyerName + "已经确认收货，请您及时上带点啥平台结算");
            sub.put("color", "#173177");
            data.put("first", sub);
            
            sub = new HashMap<String, String>();
            sub.put("value", sdf.format(new Date()));
            sub.put("color", "#173177");
            data.put("keyword1", sub);
            
            sub = new HashMap<String, String>();
            sub.put("value", order.getId());
            sub.put("color", "#173177");
            data.put("keyword2", sub);
            
            sub = new HashMap<String, String>();
            
            sub.put("value", "如有问题请致电18510213635或直接在微信留言，带点啥将第一时间为您服务！");
            sub.put("color", "#173177");
            data.put("remark", sub);
            message.setData(data);
        }
        Util.sendNotify(message);
    }
    
    public static void main(String[] args)
    {
        Map<String, Map<String, String>> lmap = new HashMap<String, Map<String, String>>();
        Map<String, String> sMap = new HashMap<String, String>();
        sMap.put("aaa", "111");
        sMap.put("bbb", "222");
        lmap.put("ttt", sMap);
        
        // sMap = new HashMap<String,String>();
        sMap.clear();
        sMap.put("ccc", "333");
        sMap.put("ddd", "444");
        lmap.put("t1", sMap);
        
        JsonMapper mapper = new JsonMapper();
        System.out.println(mapper.toJson(lmap));
        
    }
}
