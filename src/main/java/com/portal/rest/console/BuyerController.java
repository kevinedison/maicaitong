package com.portal.rest.console;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.UpdateRequest;
import com.portal.bean.entity.User;
import com.portal.bean.request.BuyerSearchRequest;
import com.portal.constant.BusinessCode;
import com.portal.manager.AdminSessionManager;
import com.portal.manager.UserManager;
import com.portal.service.UsersService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class BuyerController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerController.class);
    
    @Autowired
    UsersService userService;
    
    @Autowired
    AdminSessionManager adminSessionManager;
    
    @Autowired
    UserManager userManager;
    
    @RequestMapping(value = "/console/user/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getUser(@Valid BuyerSearchRequest buyerSearchRequest, Integer auth)
    {
        LOGGER.info("query user begin");
        ResponseData<PagingData<User>> responseData = new ResponseData<PagingData<User>>();
        
        User user = buyerSearchRequest.toEntity();
        user.setIdentityAuth(auth);
        PagingData<User> userData = userService.selectByIndex(user);
        responseData.setData(userData);
        LOGGER.info("query user end");
        return responseData;
    }
    
    @RequestMapping(value = "/console/buyer/approve", method = RequestMethod.PUT)
    @ResponseBody
    public Response approveBuyer(HttpServletRequest request, @Valid @RequestBody UpdateRequest updateRequest)
    {
        Response responseData = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        
        User dbUser = userService.selectById(updateRequest.getId());
        if (dbUser == null)
        {
            responseData.setCode(BusinessCode.NOT_EXIST.getCode());
            return responseData;
        }
        
        User user = new User();
        
        user.setUserId(updateRequest.getId());
        user.setOperator(admin);
        user.setOperateTime(new Date());
        user.setIdentityAuth(updateRequest.getStatus());
        user.setRemark(updateRequest.getRemark());
        userService.updateById(user);
        
        userManager.getUserInfo(updateRequest.getId(), true);
        return responseData;
    }
}
