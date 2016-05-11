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
import com.portal.bean.dto.SimpUserInfo;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.Feedback;
import com.portal.bean.entity.SimpUser;
import com.portal.bean.entity.User;
import com.portal.bean.request.BuyerSearchRequest;
import com.portal.bean.request.FeedbackSaveRequest;
import com.portal.bean.request.UserAuthRequest;
import com.portal.bean.request.UserUpdateRequest;
import com.portal.manager.SessionManager;
import com.portal.manager.UserManager;
import com.portal.service.FeedbackService;
import com.portal.service.UsersService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UserController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    UsersService userService;

    
    @Autowired
    FeedbackService feedbackService;
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    UserManager userManager;
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public Response getProfile(@Valid IdRequest idRequest)
    {
        ResponseData<SimpUserInfo> responseData = new ResponseData<SimpUserInfo>();
        
        SimpUser simpUser = userManager.getUserInfo(idRequest.getId(), Boolean.FALSE);
        SimpUserInfo userInfo = null;
        if (null != simpUser)
        {
            userInfo = new SimpUserInfo(simpUser);
        }
        responseData.setData(userInfo);
        return responseData;
    }
    
    @RequestMapping(value = "/user/buyerauth", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateProfile(HttpServletRequest request, @Valid @RequestBody UserAuthRequest userAuthRequest)
    {
        Response responseData = new Response();
        
        UserInfo userInfo = sessionManager.getUserSession(request);
        User user = userAuthRequest.toEntity(userInfo);
        user.setUserId(userInfo.getUserId());
        
        userService.updateById(user);
        
        sessionManager.refreshUserSession(request, userManager.getUserInfo(userInfo.getUserId(), true));
        return responseData;
    }
    
    @RequestMapping(value = "/user/info", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateUserInfo(HttpServletRequest request, @Valid @RequestBody UserUpdateRequest userUpdateRequest)
    {
        Response responseData = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        User user = userUpdateRequest.toEntity(userId);
        user.setUserId(userId);
        
        userService.updateById(user);
        
        sessionManager.refreshUserSession(request, userManager.getUserInfo(userId, true));
        return responseData;
    }
    
    @RequestMapping(value = "/buyer/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getBuyer(@Valid BuyerSearchRequest buyerSearchRequest)
    {
        LOGGER.info("query buyer begin");
        ResponseData<PagingData<SimpUserInfo>> responseData = new ResponseData<PagingData<SimpUserInfo>>();
        
        PagingData<SimpUserInfo> pagingData = new PagingData<SimpUserInfo>();
        
        User user = buyerSearchRequest.toEntity();
        
        PagingData<User> userData = userService.selectByIndex(user);
        List<User> userList = userData.getData();
        List<SimpUserInfo> simpUserList = new ArrayList<SimpUserInfo>();
        
        if (!CollectionUtils.isEmpty(userList))
        {
            for (User temp : userList)
            {
                SimpUserInfo sUserInfo = new SimpUserInfo(temp);
                simpUserList.add(sUserInfo);
            }
        }
        
        pagingData.setData(simpUserList);
        pagingData.setCount(userData.getCount());
        responseData.setData(pagingData);
        
        LOGGER.info("query buyer end");
        return responseData;
    }
    
    @RequestMapping(value = "/user/feedback", method = RequestMethod.POST)
    @ResponseBody
    public Response userFeedBack(HttpServletRequest request, @Valid @RequestBody FeedbackSaveRequest feedbackSaveRequest)
    {
        Response responseData = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        Feedback feedback = feedbackSaveRequest.toEntity(userId);
        feedback.setUserId(userId);
        
        feedbackService.save(feedback);
        return responseData;
    }
}
