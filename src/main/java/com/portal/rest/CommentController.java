package com.portal.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.portal.bean.dto.CommentInfo;
import com.portal.bean.dto.ServiceInfo;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.Comment;
import com.portal.bean.entity.Order;
import com.portal.bean.entity.UserService;
import com.portal.bean.request.CommentSaveRequest;
import com.portal.bean.request.CommentSearchRequest;
import com.portal.constant.BusinessCode;
import com.portal.manager.SessionManager;
import com.portal.manager.UserManager;
import com.portal.service.CommentService;
import com.portal.service.OrderService;
import com.portal.service.UserServService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class CommentController
{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UserServService userServService;
    
    @Autowired
    private OrderService orderService;
    
    /**
     * <一句话功能简述>评论订单 订单状态 4 6 7 8及iscomment为0 可以评论 <功能详细描述>
     * 
     * @param request
     * @param commentSearchRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/comment", method = RequestMethod.POST)
    @ResponseBody
    public Response userSaveOrderComment(HttpServletRequest request,
        @Valid @RequestBody CommentSaveRequest commentSearchRequest)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        Comment comment = commentSearchRequest.toEntity(userId);
        
        // 根据订单ID查询订单
        Order order = new Order();
        order.setId(comment.getOrderId());
        order.setBuyerId(userId);
        Integer[] statusArr = {4, 6, 7, 8};
        order.setStatusArr(statusArr);
        Order queryOrder = orderService.selectOne(order);
        
        if (null == queryOrder)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        if (1 == queryOrder.getIsComment())
        {
            response.setCode(BusinessCode.ORDER_ALREAD_COMMENT.getCode());
            return response;
        }
        
        comment.setServiceId(queryOrder.getServiceId());
        commentService.saveComment(comment, queryOrder.getSaleId());
        
        // 刷新用户缓存
        sessionManager.refreshUserSession(request, sessionManager.getUserSession(request));
        
        return response;
    }
    
    /**
     * @RequestMapping(value = "/user/comment", method = RequestMethod.DELETE)
     * @ResponseBody public Response userDeleteOrderComment(@Valid IdRequest idRequest) { Response response = new
     *               Response();
     * 
     *               String userId = sessionManager.getSessionUserId();
     * 
     *               Comment dbComment = commentService.selectById(idRequest.getId()); if (dbComment == null) {
     *               response.setCode(BusinessCode.NOT_EXIST.getCode()); return response; }
     * 
     *               if (!dbComment.getCommentAccount().equals(userId)) {
     *               response.setCode(BusinessCode.NOT_RIGHT.getCode()); return response; }
     * 
     *               commentService.deleteById(idRequest.getId()); return response; }
     */
    
    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    @ResponseBody
    public Response commentList(@Valid CommentSearchRequest commentSearchRequest)
    {
        Comment entity = commentSearchRequest.toEntity();
        return getCommentList(entity);
    }
    
    @RequestMapping(value = "/user/comment/list", method = RequestMethod.GET)
    @ResponseBody
    public Response userCommentList(HttpServletRequest request, @Valid CommentSearchRequest commentSearchRequest)
    {
        String userId = sessionManager.getSessionUserId(request);
        Comment entity = commentSearchRequest.toEntity();
        entity.setUserId(userId);
        return getCommentList(entity);
    }
    
    private ResponseData<PagingData<CommentInfo>> getCommentList(Comment comment)
    {
        ResponseData<PagingData<CommentInfo>> response = new ResponseData<PagingData<CommentInfo>>();
        PagingData<Comment> pagingComments = commentService.selectByIndex(comment);
        if (!CollectionUtils.isEmpty(pagingComments.getData()))
        {
            PagingData<CommentInfo> pagingCommentInfos = new PagingData<CommentInfo>();
            List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
            CommentInfo tempCommentInfo = null;
            for (Comment c : pagingComments.getData())
            {
                tempCommentInfo = new CommentInfo(c);
                tempCommentInfo.setUser(userManager.getUserInfo(c.getUserId(), false));
                UserService userService = userServService.selectById(c.getServiceId());
                
                if (null != userService)
                {
                    ServiceInfo serviceInfo = new ServiceInfo(userService);
                    serviceInfo.setUserInfo(userManager.getSimpUserInfo(serviceInfo.getUserId(), Boolean.FALSE));
                    tempCommentInfo.setServiceInfo(serviceInfo);
                }
                commentInfos.add(tempCommentInfo);
            }
            
            pagingCommentInfos.setCount(pagingComments.getCount());
            pagingCommentInfos.setData(commentInfos);
            response.setData(pagingCommentInfos);
        }
        return response;
    }
}
