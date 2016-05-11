package com.portal.rest.console;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.portal.bean.console.StaticService;
import com.portal.bean.console.StaticServiceInfo;
import com.portal.bean.entity.Need;
import com.portal.bean.entity.UserService;
import com.portal.bean.request.console.ServiceStaticSearchRequest;
import com.portal.common.mapper.JsonMapper;
import com.portal.manager.AdminSessionManager;
import com.portal.manager.UserManager;
import com.portal.service.StaticServiceService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class StaticController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticController.class);
    
    @Autowired
    AdminSessionManager adminSessionManager;
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    StaticServiceService staticServiceService;
    
    @RequestMapping(value = "/console/serviceorder/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getServiceOrderList(HttpServletRequest request,
        @Valid ServiceStaticSearchRequest serviceStaticSearchRequest)
    {
        LOGGER.info("query static service order begin");
        ResponseData<PagingData<StaticServiceInfo>> responseData = new ResponseData<PagingData<StaticServiceInfo>>();
        
        StaticService staticService =
            serviceStaticSearchRequest.toEntity(adminSessionManager.getSessionAdminId(request));
        
        PagingData<StaticService> serviceOrderData = staticServiceService.selectByIndex(staticService);
        
        List<StaticService> serviceOrderList = serviceOrderData.getData();
        
        List<StaticServiceInfo> serviceOrderInfoList = new ArrayList<StaticServiceInfo>();
        
        if (!CollectionUtils.isEmpty(serviceOrderList))
        {
            JsonMapper mapper = new JsonMapper();
            for (StaticService temp : serviceOrderList)
            {
                StaticServiceInfo serviceOrderInfo = new StaticServiceInfo(temp);
                serviceOrderInfo.setUserInfo(userManager.getUserInfo(temp.getUserId(), Boolean.FALSE));
                String serviceContent = temp.getServiceContent();
                
                if (StringUtils.isNotEmpty(serviceContent))
                {
                    if (temp.getServiceId().startsWith("N"))
                    {
                        serviceOrderInfo.setServiceInfo(mapper.fromJson(serviceContent, Need.class));
                    }
                    else if (temp.getServiceId().startsWith("S"))
                    {
                        serviceOrderInfo.setServiceInfo(mapper.fromJson(serviceContent, UserService.class));
                    }
                }
                
                serviceOrderInfoList.add(serviceOrderInfo);
            }
        }
        
        PagingData<StaticServiceInfo> orderInfoData = new PagingData<StaticServiceInfo>();
        orderInfoData.setData(serviceOrderInfoList);
        orderInfoData.setCount(serviceOrderData.getCount());
        
        responseData.setData(orderInfoData);
        LOGGER.info("query static service order end");
        return responseData;
    }
    
    @RequestMapping(value = "/console/pageview/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getPageViewList(HttpServletRequest request,
        @Valid ServiceStaticSearchRequest serviceStaticSearchRequest)
    {
        LOGGER.info("query static service order begin");
        ResponseData<PagingData<StaticServiceInfo>> responseData = new ResponseData<PagingData<StaticServiceInfo>>();
        
        StaticService staticService =
            serviceStaticSearchRequest.toEntity(adminSessionManager.getSessionAdminId(request));
        
        PagingData<StaticService> serviceOrderData = staticServiceService.selectPageViewByIndex(staticService);
        
        List<StaticService> serviceOrderList = serviceOrderData.getData();
        
        List<StaticServiceInfo> serviceOrderInfoList = new ArrayList<StaticServiceInfo>();
        
        if (!CollectionUtils.isEmpty(serviceOrderList))
        {
            JsonMapper mapper = new JsonMapper();
            for (StaticService temp : serviceOrderList)
            {
                StaticServiceInfo serviceOrderInfo = new StaticServiceInfo(temp);
                serviceOrderInfo.setUserInfo(userManager.getUserInfo(temp.getUserId(), Boolean.FALSE));
                String serviceContent = temp.getServiceContent();
                
                if (StringUtils.isNotEmpty(serviceContent))
                {
                    if (temp.getServiceId().startsWith("N"))
                    {
                        serviceOrderInfo.setServiceInfo(mapper.fromJson(serviceContent, Need.class));
                    }
                    else if (temp.getServiceId().startsWith("S"))
                    {
                        serviceOrderInfo.setServiceInfo(mapper.fromJson(serviceContent, UserService.class));
                    }
                }
                
                serviceOrderInfoList.add(serviceOrderInfo);
            }
        }
        
        PagingData<StaticServiceInfo> orderInfoData = new PagingData<StaticServiceInfo>();
        orderInfoData.setData(serviceOrderInfoList);
        orderInfoData.setCount(serviceOrderData.getCount());
        
        responseData.setData(orderInfoData);
        LOGGER.info("query static service order end");
        return responseData;
    }
    
}
