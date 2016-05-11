package com.portal.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.portal.bean.IdRequest;
import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.dto.ServiceInfo;
import com.portal.bean.dto.SimpUserInfo;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.UserService;
import com.portal.bean.request.ServiceSaveRequest;
import com.portal.bean.request.ServiceSaveTempRequest;
import com.portal.bean.request.ServiceSearchRequest;
import com.portal.bean.request.ServiceUpdateRequest;
import com.portal.bean.request.ServiceUpdateTempRequest;
import com.portal.constant.BusinessCode;
import com.portal.manager.SessionManager;
import com.portal.manager.UserManager;
import com.portal.service.UserServService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ServiceController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
    
    @Autowired
    UserServService userServService;
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    UserManager userManager;
    
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    @ResponseBody
    public Response serviceDetail(@Valid IdRequest idRequest)
    {
        LOGGER.info("query service start");
        ResponseData<ServiceInfo> responseData = new ResponseData<ServiceInfo>();
        
        UserService userService = userServService.selectById(idRequest.getId());
        if (userService != null)
        {
            ServiceInfo serviceInfo = new ServiceInfo(userService);
            SimpUserInfo sUserInfo = userManager.getSimpUserInfo(userService.getUserId(), Boolean.FALSE);
            serviceInfo.setUserInfo(sUserInfo);
            
            responseData.setData(serviceInfo);
        }
        
        LOGGER.info("query service end");
        return responseData;
    }
    
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    @ResponseBody
    public Response searchService(@Valid ServiceSearchRequest serviceSearchRequest)
    {
        LOGGER.info("query service start");
        ResponseData<PagingData<ServiceInfo>> responseData = new ResponseData<PagingData<ServiceInfo>>();
        
        UserService service = serviceSearchRequest.toEntity();
        // 只能搜索正常的服务
        service.setStatus(1);
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
    
    @RequestMapping(value = "/user/service", method = RequestMethod.GET)
    @ResponseBody
    public Response serviceUserDetail(HttpServletRequest request, @Valid IdRequest idRequest)
    {
        LOGGER.info("query service start");
        ResponseData<ServiceInfo> responseData = new ResponseData<ServiceInfo>();
        
        UserService userService = userServService.selectById(idRequest.getId());
        if (userService != null)
        {
            if (!userService.getUserId().equals(sessionManager.getSessionUserId(request)))
            {
                LOGGER.info("query user {} service，but not right {} ",
                    userService.getUserId(),
                    sessionManager.getSessionUserId(request));
                responseData.setCode(BusinessCode.NOT_RIGHT.getCode());
                return responseData;
            }
            ServiceInfo serviceInfo = new ServiceInfo(userService);
            SimpUserInfo sUserInfo = userManager.getSimpUserInfo(userService.getUserId(), Boolean.FALSE);
            serviceInfo.setUserInfo(sUserInfo);
            responseData.setData(serviceInfo);
        }
        
        LOGGER.info("query service end");
        return responseData;
    }
    
    @RequestMapping(value = "/user/service/list", method = RequestMethod.GET)
    @ResponseBody
    public Response searchUserService(HttpServletRequest request, @Valid ServiceSearchRequest serviceSearchRequest)
    {
        LOGGER.info("query user service start");
        ResponseData<PagingData<ServiceInfo>> responseData = new ResponseData<PagingData<ServiceInfo>>();
        
        UserService service = serviceSearchRequest.toEntity();
        // 根据用户ID查询服务
        service.setUserId(sessionManager.getSessionUserId(request));
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
        
        LOGGER.info("query user service end");
        return responseData;
    }
    
    @RequestMapping(value = "/user/service", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delService(HttpServletRequest request, @Valid IdRequest idRequest)
    {
        LOGGER.info("delete service start");
        Response response = new Response();
        String userId = sessionManager.getSessionUserId(request);
        
        UserService deleteUserService = new UserService();
        deleteUserService.setId(idRequest.getId());
        deleteUserService.setUserId(userId);
        userServService.deleteById(deleteUserService);
        LOGGER.info("delete service end");
        return response;
    }
    
    @RequestMapping(value = "/user/service", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateService(HttpServletRequest request, @RequestBody ServiceUpdateRequest serviceUpdateRequest)
    {
        LOGGER.info("edit service start");
        Response response = new Response();
        String userId = sessionManager.getSessionUserId(request);
        UserService userService = serviceUpdateRequest.toEntity(userId);
        UserService dbUserService = userServService.selectById(userService.getId());
        if (dbUserService != null)
        {
            if (dbUserService.getUserId().equals(userId))
            {
                userServService.updateById(userService);
            }
            else
            {
                response.setCode(BusinessCode.NOT_EXIST.getCode());
                return response;
            }
        }
        
        LOGGER.info("edit service end");
        return response;
    }
    
    @RequestMapping(value = "/user/service", method = RequestMethod.POST)
    @ResponseBody
    public Response addService(@Valid @RequestBody ServiceSaveRequest serviceSaveRequest, HttpServletRequest request)
    {
        LOGGER.info("add service start");
        ResponseData<String> response = new ResponseData<>();
        UserInfo userInfo = sessionManager.getUserSession(request);
        UserService userService = serviceSaveRequest.toEntity(userInfo.getUserId());
        userService.setCountry(userInfo.getCountry());
        userServService.saveService(userService, userInfo);
        
        LOGGER.info("add service end");
        return response;
    }
    
    @RequestMapping(value = "/user/servicetemp", method = RequestMethod.POST)
    @ResponseBody
    public Response addTempService(@Valid @RequestBody ServiceSaveTempRequest serviceSaveTempRequest,
        HttpServletRequest request)
    {
        LOGGER.info("add service start");
        ResponseData<String> response = new ResponseData<>();
        UserInfo userInfo = sessionManager.getUserSession(request);
        UserService userService = serviceSaveTempRequest.toEntity(userInfo.getUserId());
        userService.setCountry(userInfo.getCountry());
        userServService.saveService(userService, userInfo);
        
        LOGGER.info("add service end");
        return response;
    }
    
    @RequestMapping(value = "/user/servicetemp", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateTempService(@Valid @RequestBody ServiceUpdateTempRequest serviceUpdateTempRequest,
        HttpServletRequest request)
    {
        
        LOGGER.info("edit service start");
        Response response = new Response();
        String userId = sessionManager.getSessionUserId(request);
        UserService userService = serviceUpdateTempRequest.toEntity(userId);
        UserService dbUserService = userServService.selectById(userService.getId());
        if (dbUserService != null)
        {
            if (dbUserService.getUserId().equals(userId))
            {
                userServService.updateById(userService);
            }
            else
            {
                response.setCode(BusinessCode.NOT_EXIST.getCode());
                return response;
            }
        }
        
        LOGGER.info("edit service end");
        return response;
    }
}
