package com.portal.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.Bill;
import com.portal.bean.entity.Order;
import com.portal.bean.entity.User;
import com.portal.bean.entity.UserService;
import com.portal.common.context.BaseContext;
import com.portal.common.mapper.JsonMapper;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.constant.BusinessCode;
import com.portal.constant.StatusConstant;
import com.portal.manager.SessionManager;
import com.portal.manager.UserManager;
import com.portal.service.BillService;
import com.portal.service.OrderService;
import com.portal.service.UsersService;
import com.portal.wechat.Configure;
import com.portal.wechat.Util;
import com.portal.wechat.bean.MessageTemplate;
import com.portal.wechat.bean.WechatPayInfo;
import com.portal.wechat.bean.WechatPayNotifyResult;
import com.portal.wechat.bean.WechatPayRefundInfo;
import com.portal.wechat.bean.WechatPayReqData;
import com.portal.wechat.bean.WechatPayResData;
import com.portal.wechat.bean.WechatTransferResData;
import com.portal.wechat.request.WechatPayRefundRequest;
import com.portal.wechat.service.WechatPayService;
import com.thoughtworks.xstream.XStream;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class PayController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UsersService uService;
    
    @Autowired
    private SessionManager sessionManager;
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private UserManager userManager;
    
    @RequestMapping(value = "/pay/result", method = RequestMethod.POST)
    @ResponseBody
    public void payCallback(HttpServletRequest request, HttpServletResponse response)
    {
        XStream xtream = new XStream();
        response.setContentType("application/xml; charset=UTF-8");
        
        xtream.alias("xml", WechatPayNotifyResult.class);
        
        String responseXml = "";
        
        WechatPayNotifyResult result = null;
        try
        {
            result = (WechatPayNotifyResult)xtream.fromXML(request.getInputStream());
        }
        catch (IOException e1)
        {
            LOGGER.error("Get the weixin pay result catch an exception", e1);
            responseXml =
                "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[消息异常]]></return_msg></xml>";
        }
        
        LOGGER.info("result==" + result);
        
        if (null == result)
        {
            LOGGER.error("Get the weixin pay result,the result is null");
            responseXml =
                "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[消息异常]]></return_msg></xml>";
        }
        else if ("SUCCESS".equals(result.getReturn_code()) && "SUCCESS".equals(result.getResult_code()))
        {
            // 更新数据库订单数据
            Order order = new Order();
            order.setId(result.getOut_trade_no());
            
            // 先查询订单
            Order dbOrder = orderService.selectOne(order);
            
            if (null == dbOrder)
            {
                responseXml =
                    "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[该订单不存在或状态不正常]]></return_msg></xml>";
            }
            else if (dbOrder.getStatus() != 6 && dbOrder.getStatus() != 4)
            {
                // 直接支付
                if (null != dbOrder.getPayType() && 1 == dbOrder.getPayType())
                {
                    
                    Response transRes = orderService.directPay(dbOrder);
                    
                    // 直接支付失败，修改为非直接支付 同时将状态修改为待发货
                    dbOrder.setStatus(StatusConstant.ORDE_WAIT_SENDOUT);
                    if (BusinessCode.SETTLE_FAIL.getCode().equals(transRes.getCode()))
                    {
                        dbOrder.setPayType(0);
                    }
                    result.setOperatParam(dbOrder.getBuyerId());
                    orderService.updatePayInfo(dbOrder, result);
                }
                else
                {
                    // 担保支付
                    dbOrder.setStatus(StatusConstant.ORDE_WAIT_SENDOUT);
                    result.setOperatParam(dbOrder.getBuyerId());
                    orderService.updatePayInfo(dbOrder, result);
                }
                sessionManager.refreshUserSession(request, userManager.getUserInfo(dbOrder.getBuyerId(), true));
                
                responseXml =
                    "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                
                // 向买手发通知买家已付款
                sendPayNotify(dbOrder);
            }
            else
            {
                responseXml =
                    "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            }
        }
        else
        {
            responseXml =
                "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[微信返回支付失败]]></return_msg></xml>";
        }
        
        // 响应xml给微信
        try
        {
            response.getWriter().print(responseXml);
        }
        catch (IOException e)
        {
            LOGGER.error("write the payresult to the wechat catch an exception", e);
        }
    }
    
    @RequestMapping(value = "/user/pay/refund", method = RequestMethod.POST)
    @ResponseBody
    public Response payRefund(@Valid @RequestBody WechatPayRefundRequest wechatPayRefundRequest,
        HttpServletRequest request)
    {
        Response response = new Response();
        // 获取用户ID
        String userId = sessionManager.getSessionUserId(request);
        Order order = new Order();
        order.setBuyerId(userId);
        order.setId(wechatPayRefundRequest.getOrderId());
        
        order = orderService.selectOne(order);
        
        // 订单为空，直接返回失败
        if (null == order)
        {
            LOGGER.info("the order not exist or not belongs to you or havn't paymented....");
            response.setCode(BusinessCode.ORDER_CANNOT_BE_REFUND.getCode());
            return response;
        }
        
        // 查询成功订单的相关信息
        WechatPayRefundInfo wechatPayRefundInfo = wechatPayRefundRequest.toEntity(userId);
        
        // 查询微信支付的相关信息
        WechatPayNotifyResult wechatPayNotifyResult = new WechatPayNotifyResult();
        wechatPayNotifyResult.setOut_trade_no(wechatPayRefundInfo.getOut_trade_no());
        wechatPayNotifyResult.setOpenid(userId);
        
        wechatPayNotifyResult = orderService.selectPayResult(wechatPayNotifyResult);
        
        if (null == wechatPayNotifyResult)
        {
            LOGGER.info("the order have not paymented success yet,can not refund....");
            response.setCode(BusinessCode.ORDER_HAVE_NOT_PAYMENT.getCode());
            return response;
        }
        
        if (wechatPayRefundRequest.getRefundFee() > wechatPayNotifyResult.getTotal_fee())
        {
            LOGGER.info("apply refund fee over than the order total fee....");
            response.setCode(BusinessCode.REFUNDFEE_OVER_TOTALFEE.getCode());
            return response;
        }
        
        // 符合要求的申请入库，等待审批
        wechatPayRefundInfo.setTotal_fee(wechatPayNotifyResult.getTotal_fee());
        wechatPayRefundInfo.setTransaction_id(wechatPayNotifyResult.getTransaction_id());
        orderService.saveRefundInfo(wechatPayRefundInfo);
        
        return response;
    }
    
    @RequestMapping(value = "/user/pay/result", method = RequestMethod.GET)
    @ResponseBody
    public Response getPayResult(@Valid String orderId)
    {
        ResponseData<Integer> response = new ResponseData<>();
        String userId = "";// sessionManager.getSessionUserId();
        Order entity = new Order();
        entity.setId(orderId);
        entity.setOperator(userId);
        entity = orderService.selectOne(entity);
        
        if (null == entity)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        response.setData(entity.getStatus());
        
        return response;
    }
    
    /**
     * @RequestMapping(value = "/user/order/payment", method = RequestMethod.POST)
     * @ResponseBody public Response saveUserOrderPayment(@Valid @RequestBody PaymentSaveRequest paymentSaveRequest,
     *               HttpServletRequest request) { Response response = new Response();
     * 
     *               //SimpUser simpUser = sessionManager.getUserSession(request);
     * 
     *               Order entity = paymentSaveRequest.toEntity(simpUser.getId());
     * 
     *               // entity.set orderService.updateById(entity);
     * 
     *               return response; }
     */
    
    @RequestMapping(value = "/user/pay/unifiedorder", method = RequestMethod.POST)
    @ResponseBody
    public Response unifiedOrder(@Valid @RequestBody Order order, HttpServletRequest request)
    {
        ResponseData<WechatPayInfo> response = new ResponseData<WechatPayInfo>();
        
        String userId = sessionManager.getSessionUserId(request);
        // 获取用户ID
        Order queryOrder = new Order();
        queryOrder.setId(order.getId());
        queryOrder.setBuyerId(userId);
        
        queryOrder = orderService.selectOne(queryOrder);
        // 订单为空，直接返回失败
        if (null == queryOrder)
        {
            LOGGER.info("the order not exist....");
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        JsonMapper mapper = new JsonMapper();
        UserService userService = mapper.fromJson(queryOrder.getServiceContent(), UserService.class);
        
        String title = null == userService ? "" : userService.getTitle();
        String desc = null == userService ? "" : userService.getDescription();
        WechatPayResData res = sendWechatPay(queryOrder, title, desc);
        if (null != res && "SUCCESS".equals(res.getResult_code()))
        {
            User user = new User();
            user.setUserId(userId);
            user.setRecipient(order.getBuyerInfo());
            // 保存联系人信息
            uService.updateById(user);
            sessionManager.refreshUserSession(request, userManager.getUserInfo(userId, true));
            
            // 更新订单信息
            orderService.updateById(order);
            WechatPayInfo payInfo = new WechatPayInfo("prepay_id=" + res.getPrepay_id());
            response.setData(payInfo);
        }
        else
        {
            response.setCode(BusinessCode.PAY_FAIL.getCode());
        }
        return response;
    }
    
    private WechatPayResData sendWechatPay(Order order, String title, String desc)
    {
        Map<String, String> weixinConfig = (Map<String, String>)BaseContext.getBean("wechatConfig");
        Configure.setAppID(weixinConfig.get("appId"));
        Configure.setMchID(weixinConfig.get("mchId"));
        // API密钥
        Configure.setKey(weixinConfig.get("apiKey"));
        
        System.out.println(this.getClass().getResource("/"));
        
        Configure.setCertLocalPath(this.getClass().getResource("/").getPath() + "/cert/apiclient_cert.p12");
        Configure.setCertPassword(weixinConfig.get("mchId"));
        
        WechatPayResData resData = null;
        try
        {
            // 单位为元
            Double totalPrice = order.getTotalPrice();
            int totalPriceFen = new Double(totalPrice * 100).intValue();
            WechatPayService payService = new WechatPayService(weixinConfig.get("payUrl"));
            WechatPayReqData payDate =
                new WechatPayReqData(title, desc, order.getId(), totalPriceFen, "127.0.0.1", null, "JSAPI", "WEB",
                    weixinConfig.get("notifyUrl"), order.getBuyerId());
            
            String result = payService.request(payDate);
            LOGGER.info("Pay return:" + result);
            resData = (WechatPayResData)Util.getObjectFromXML(result, WechatPayResData.class);
        }
        catch (Exception e)
        {
            LOGGER.error("Send the payrequest to wechat catch an exception", e);
        }
        return resData;
    }
    
    @RequestMapping(value = "/user/transfer", method = RequestMethod.POST)
    @ResponseBody
    public Response transferBalance(@Valid @RequestBody Bill bill, HttpServletRequest request)
    {
        LOGGER.info("提现处理开始");
        ResponseData<String> response = new ResponseData<String>();
        
        UserInfo userInfo = sessionManager.getUserSession(request);
        
        if (bill.getPayAmount() > userInfo.getBalance())
        {
            response.setCode(BusinessCode.TRANSFER_EXCEED_BALANCE.getCode());
            return response;
        }
        
        bill.setId(BaseConstant.BILL_PREFIX + Identities.getSystemId());
        bill.setOperator(userInfo.getUserId());
        bill.setUserId(userInfo.getUserId());
        bill.setPayInfo("transfer");
        
        WechatTransferResData resData = billService.transfer(bill);
        
        if (null == resData)
        {
            response.setCode(BusinessCode.FAIL.getCode());
            return response;
        }
        else if ("SUCCESS".equals(resData.getReturn_code()))
        {
            if (!"SUCCESS".equals(resData.getResult_code()))
            {
                response.setCode(BusinessCode.TRANSFER_FAIL.getCode());
                response.setMsg(resData.getErr_code_des());
            }
            sessionManager.refreshUserSession(request, userInfo.getUserId());
        }
        else
        {
            response.setCode(BusinessCode.TRANSFER_FAIL.getCode());
            response.setMsg(resData.getReturn_msg());
        }
        
        LOGGER.info("提现处理结束");
        return response;
    }
    
    private void sendPayNotify(Order order)
    {
        MessageTemplate message = new MessageTemplate();
        message.setTemplate_id("-mGJqS0pVi4hBnlrACGHu6Ubam9Bt2RlEnQZKQtpla8");
        message.setTouser(order.getSaleId());
        
        Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
        
        UserInfo userInfo = userManager.getUserInfo(order.getBuyerId(), Boolean.FALSE);
        String userName = StringUtils.isEmpty(userInfo.getRealName()) ? userInfo.getNickName() : userInfo.getRealName();
        
        String serviceContent = order.getServiceContent();
        JsonMapper mapper = new JsonMapper();
        UserService userService = mapper.fromJson(serviceContent, UserService.class);
        
        Map<String, String> sub = new HashMap<String, String>();
        sub.put("value", "用户" + userName + "的订单" + order.getId() + "已经付款，请尽快安排发货");
        sub.put("color", "#173177");
        data.put("first", sub);
        
        sub = new HashMap<String, String>();
        sub.put("value", String.valueOf(order.getTotalPrice()));
        sub.put("color", "#173177");
        data.put("orderMoneySum", sub);
        
        sub = new HashMap<String, String>();
        sub.put("value", userService.getTitle());
        sub.put("color", "#173177");
        data.put("orderProductName", sub);
        
        sub = new HashMap<String, String>();
        sub.put("value", "如有问题请致电18510213635或直接在微信留言，带点啥将第一时间为您服务！");
        sub.put("color", "#173177");
        data.put("Remark", sub);
        message.setData(data);
        Util.sendNotify(message);
    }
}
