package com.portal.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.Order;
import com.portal.common.mapper.JsonMapper;
import com.portal.mapper.OrderMapper;

/***
 * 用户session管理
 * 
 * **/
@Component
public class SessionManager
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);
    
    public static final String SESSION_USER_INFO_KEY = "session-user-info";
    
    public static final String SESSION_MESSAGE_COUNT = "session-message-count";
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    OrderMapper orderMapper;
    
    /***
     * session
     * **/
    public boolean checkUserSession(HttpServletRequest request)
    {
        if (request == null || request.getSession() == null)
        {
            LOGGER.warn("the session is null.");
            return false;
        }
        
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute(SESSION_USER_INFO_KEY);
        if (userInfo == null || StringUtils.isEmpty(userInfo.getUserId()))
        {
            LOGGER.warn("get from session, but is null..");
            return false;
        }
        return true;
    }
    
    /***
     * session
     * **/
    public UserInfo getUserSession(HttpServletRequest request)
    {
        if (request == null || request.getSession() == null)
        {
            LOGGER.warn("the session is null.");
            return null;
        }
        
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute(SESSION_USER_INFO_KEY);
        return userInfo;
    }
    
    /***
     * session
     * **/
    public String getSessionUserId(HttpServletRequest request)
    {
        UserInfo userInfo = getUserSession(request);
        if (null == userInfo)
        {
            return "";
        }
        LOGGER.info("get user account from session, userId {} ..", userInfo.getUserId());
        return userInfo.getUserId();
    }
    
    /***
     * session
     * **/
    public boolean refreshUserSession(HttpServletRequest request, UserInfo userInfo)
    {
        LOGGER.info("===========================start refresh====================================");
        if (request == null || request.getSession() == null)
        {
            LOGGER.warn("the session is null.");
            return false;
        }
        
        Integer identityAuth = userInfo.getIdentityAuth();
        Order order = new Order();
        if (null != identityAuth && 1 == identityAuth)
        {
            order.setSaleId(userInfo.getUserId());
        }
        else
        {
            order.setBuyerId(userInfo.getUserId());
        }
        Integer[] statusArr = {4, 6, 7, 8};
        order.setStatusArr(statusArr);
        
        userInfo.setOrderStatusCount(setUserInfo(order));
        
        request.getSession().setAttribute(SESSION_USER_INFO_KEY, userInfo);
        return true;
    }
    
    /***
     * session
     * **/
    public boolean refreshUserSession(HttpServletRequest request, String userId)
    {
        if (request == null || request.getSession() == null)
        {
            LOGGER.warn("the session is null.");
            return false;
        }
        
        request.getSession().setAttribute(SESSION_USER_INFO_KEY, userManager.getUserInfo(userId, Boolean.FALSE));
        return true;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @param count
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Integer getMessageCount(HttpServletRequest request)
    {
        if (request == null || request.getSession() == null)
        {
            LOGGER.warn("the session is null.");
            return 0;
        }
        
        return SystemManager.getMessageCount(getSessionUserId(request));
    }
    
    private String setUserInfo(Order order)
    {
        // 查询订单状态数
        Map<String, Integer> orderStatus = new HashMap<String, Integer>();
        orderStatus.put("commentCount", 0);
        orderStatus.put("waitPay", 0);
        orderStatus.put("waitSend", 0);
        orderStatus.put("waitReceive", 0);
        orderStatus.put("waitSettle", 0);
        
        Order commentOrder = new Order();
        Order settleOrder = new Order();
        BeanUtils.copyProperties(order, commentOrder);
        BeanUtils.copyProperties(order, settleOrder);
        commentOrder.setIsComment(0);
        settleOrder.setStatus(4);
        settleOrder.setPayType(0);
        
        Integer commentCount = orderMapper.selectByIndexCount(commentOrder);
        Integer settleCount = orderMapper.selectByIndexCount(settleOrder);
        orderStatus.put("commentCount", commentCount);
        orderStatus.put("waitSettle", settleCount);
        
        List<Order> orderList = orderMapper.selectOrderCount(order);
        
        if (!CollectionUtils.isEmpty(orderList))
        {
            for (Order temp : orderList)
            {
                if (null == temp.getStatus())
                {
                    continue;
                }
                
                switch (temp.getStatus())
                {
                    case 1:
                        orderStatus.put("waitPay", temp.getCount());
                        break;
                    case 2:
                        orderStatus.put("waitSend", temp.getCount());
                        break;
                    case 3:
                        orderStatus.put("waitReceive", temp.getCount());
                        break;
                }
            }
        }
        
        JsonMapper mapper = new JsonMapper();
        return mapper.toJson(orderStatus);
    }
}
