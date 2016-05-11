package com.portal.rest.console;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.dto.OrderInfo;
import com.portal.bean.dto.ServiceInfo;
import com.portal.bean.dto.SimpUserInfo;
import com.portal.bean.entity.Order;
import com.portal.bean.entity.UserService;
import com.portal.bean.request.OrderSearchRequest;
import com.portal.bean.request.ServiceSearchRequest;
import com.portal.common.mapper.JsonMapper;
import com.portal.manager.AdminSessionManager;
import com.portal.manager.UserManager;
import com.portal.service.OrderService;
import com.portal.service.UserServService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UserServiceController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);
    
    @Autowired
    AdminSessionManager adminSessionManager;
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    UserServService userServService;
    
    @Autowired
    OrderService orderService;
    
    @RequestMapping(value = "console/service/search", method = RequestMethod.GET)
    @ResponseBody
    public Response searchService(@Valid ServiceSearchRequest serviceSearchRequest)
    {
        LOGGER.info("query service start");
        ResponseData<PagingData<ServiceInfo>> responseData = new ResponseData<PagingData<ServiceInfo>>();
        
        UserService service = serviceSearchRequest.toEntity();
        PagingData<UserService> serviceData = userServService.selectByIndex(service);
        
        List<UserService> serviceList = serviceData.getData();
        List<ServiceInfo> serviceInfoList = new ArrayList<ServiceInfo>();
        
        if (!CollectionUtils.isEmpty(serviceList))
        {
            for (UserService temp : serviceList)
            {
                ServiceInfo serviceInfo = new ServiceInfo(temp);
                SimpUserInfo sUserInfo = userManager.getSimpUserInfo(temp.getUserId(), Boolean.FALSE);
                serviceInfo.setUserInfo(sUserInfo);
                serviceInfoList.add(serviceInfo);
            }
        }
        
        PagingData<ServiceInfo> pagingData = new PagingData<ServiceInfo>();
        pagingData.setData(serviceInfoList);
        pagingData.setCount(serviceData.getCount());
        responseData.setData(pagingData);
        
        LOGGER.info("query service end");
        return responseData;
    }
    
    @RequestMapping(value = "console/order/search", method = RequestMethod.GET)
    @ResponseBody
    public Response getUserOrder(@Valid OrderSearchRequest orderSearchRequest)
    {
        Order order = orderSearchRequest.toEntity();
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
                orderInfo.setUserService(mapper.fromJson(temp.getServiceContent(), UserService.class));
                orderInfoList.add(orderInfo);
            }
        }
        PagingData<OrderInfo> orderInfoData = new PagingData<OrderInfo>();
        orderInfoData.setData(orderInfoList);
        orderInfoData.setCount(orderData.getCount());
        response.setData(orderInfoData);
        return response;
    }
}
