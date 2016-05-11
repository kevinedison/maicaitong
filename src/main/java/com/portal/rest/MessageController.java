package com.portal.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.portal.bean.dto.MessageInfo;
import com.portal.bean.dto.MessageRelationInfo;
import com.portal.bean.entity.Message;
import com.portal.bean.entity.MessageRelation;
import com.portal.bean.request.MessageRelationSearchRequest;
import com.portal.bean.request.MessageSaveRequest;
import com.portal.bean.request.MessageSearchRequest;
import com.portal.bean.request.MessageUpdateRequest;
import com.portal.common.mapper.JsonMapper;
import com.portal.constant.BaseConstant;
import com.portal.constant.BusinessCode;
import com.portal.manager.SessionManager;
import com.portal.manager.SystemManager;
import com.portal.manager.UserManager;
import com.portal.service.MessageService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class MessageController
{
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    private MessageService messageService;
    
    @RequestMapping(value = "/user/message", method = RequestMethod.POST)
    @ResponseBody
    public Response userSaveMessage(HttpServletRequest request,
        @Valid @RequestBody MessageSaveRequest messageSaveRequest)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Message entity = messageSaveRequest.toEntity(userId);
        entity.setSenderId(userId);
        
        String receiverId = messageSaveRequest.getReceiverId();
        if (null == userManager.getUserInfo(receiverId, false))
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        if (userId.equals(receiverId))
        {
            response.setCode(BusinessCode.CANNOT_MESSAGE_SELF.getCode());
            return response;
        }
        
        messageService.save(entity);
        return response;
    }
    
    @RequestMapping(value = "/user/message", method = RequestMethod.DELETE)
    @ResponseBody
    public Response userDelMessage(HttpServletRequest request,
        @Valid @RequestBody MessageUpdateRequest messagetUpdateRequest)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Message entity = messagetUpdateRequest.toEntity(userId);
        entity.setSenderId(userId);
        
        Message dbMessage = messageService.selectById(messagetUpdateRequest.getId());
        if (null == dbMessage || userId.equals(dbMessage.getSenderId()))
        {
            response.setCode(BusinessCode.NOT_RIGHT.getCode());
            return response;
        }
        
        messageService.updateById(entity);
        return response;
    }
    
    @RequestMapping(value = "/user/contact", method = RequestMethod.POST)
    @ResponseBody
    public Response userSaveContact(HttpServletRequest request, @Valid @RequestBody MessageRelation messageRelation)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        messageRelation.setUserId(userId);
        
        if (StringUtils.isEmpty(messageRelation.getContactUserId()))
        {
            response.setCode(BusinessCode.PARAMETER_NULL.getCode());
            return response;
        }
        
        if (null == userManager.getSimpUserInfo(messageRelation.getContactUserId(), Boolean.FALSE))
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        MessageRelation queryMessageRelation = messageService.seletctSaveRelation(messageRelation);
        
        if (null == queryMessageRelation)
        {
            // 插入
            messageRelation.setIsContact(1);
            messageService.saveRelation(messageRelation);
        }
        else if (1 == queryMessageRelation.getIsContact())
        {
            response.setCode(BusinessCode.ALREADY_CONTACT.getCode());
            return response;
        }
        else
        {
            // 更新
            queryMessageRelation.setIsContact(1);
            messageService.updateRelationByRelationId(queryMessageRelation);
        }
        
        return response;
    }
    
    @RequestMapping(value = "/user/contact", method = RequestMethod.DELETE)
    @ResponseBody
    public Response userDelContact(HttpServletRequest request, @Valid IdRequest idRequest)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        MessageRelation queryMessageRelation = new MessageRelation();
        queryMessageRelation.setId(idRequest.getId());
        queryMessageRelation.setUserId(userId);
        queryMessageRelation.setIsContact(1);
        
        queryMessageRelation = messageService.seletctSaveRelation(queryMessageRelation);
        
        if (null == queryMessageRelation)
        {
            response.setCode(BusinessCode.CONTACT_NOT_EXIST.getCode());
            return response;
        }
        
        queryMessageRelation.setIsContact(0);
        messageService.updateRelationByRelationId(queryMessageRelation);
        
        return response;
    }
    
    @RequestMapping(value = "/user/messagerel", method = RequestMethod.DELETE)
    @ResponseBody
    public Response userDelMessageRelation(HttpServletRequest request, @Valid IdRequest idRequest)
    {
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        // 查询关系是否存在
        MessageRelation messageRelation = new MessageRelation();
        messageRelation.setUserId(userId);
        messageRelation.setId(idRequest.getId());
        messageRelation.setLimit(BaseConstant.PAGING_LIMIT_MAX);
        messageRelation.setOffset(BaseConstant.PAGING_OFF_SET_START);
        PagingData<MessageRelation> relationData = messageService.seletctRelation(messageRelation);
        List<MessageRelation> relationList = relationData.getData();
        
        if (CollectionUtils.isEmpty(relationList))
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        MessageRelation relation = relationList.get(0);
        String unread = relation.getUnread();
        
        Map<String, Integer> unreadMap = new HashMap<String, Integer>();
        if (!StringUtils.isEmpty(unread))
        {
            JsonMapper mapper = new JsonMapper();
            unreadMap = mapper.fromJson(unread, Map.class);
        }
        
        String empty = "{\"" + userId + "\":0,\"" + relation.getContactUserId() + "\":0}";
        
        int userCount = null == unreadMap.get(userId) ? 0 : unreadMap.get(userId);
        int contactCount =
            null == unreadMap.get(relation.getContactUserId()) ? 0 : unreadMap.get(relation.getContactUserId());
        SystemManager.minusMessageCount(userId, userCount);
        SystemManager.minusMessageCount(relation.getContactUserId(), contactCount);
        
        messageRelation.setUnread(empty);
        messageService.updateRelationByRelationId(messageRelation);
        messageService.deleteMessageByRelId(idRequest.getId());
        return response;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param messageSearchRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/message", method = RequestMethod.GET)
    @ResponseBody
    public Response userGetMessage(HttpServletRequest request, @Valid MessageSearchRequest messageSearchRequest)
    {
        ResponseData<PagingData<MessageInfo>> response = new ResponseData<>();
        Message message = messageSearchRequest.toEntity();
        
        String userId = sessionManager.getSessionUserId(request);
        // 关系ID touser 不能同时为空
        if (StringUtils.isEmpty(message.getRelationId()) && StringUtils.isEmpty(messageSearchRequest.getToUser()))
        {
            response.setCode(BusinessCode.PARAMETER_NULL.getCode());
            return response;
        }
        // 查询关系
        MessageRelation messageRelation = new MessageRelation();
        messageRelation.setId(message.getRelationId());
        messageRelation.setUserId(userId);
        messageRelation.setContactUserId(messageSearchRequest.getToUser());
        messageRelation.setOffset(BaseConstant.PAGING_OFF_SET_START);
        messageRelation.setLimit(BaseConstant.PAGING_LIMIT_MAX);
        PagingData<MessageRelation> relationData = messageService.seletctRelation(messageRelation);
        List<MessageRelation> relationList = relationData.getData();
        // 未绑定 则返回空
        if (CollectionUtils.isEmpty(relationList))
        {
            return response;
        }
        
        // 将未读消息数置0
        clearUnread(relationList.get(0), userId);
        
        message.setRelationId(relationList.get(0).getId());
        
        PagingData<Message> messageData = messageService.selectByIndex(message);
        List<Message> messageList = messageData.getData();
        List<MessageInfo> messageInfoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(messageList))
        {
            for (Message temp : messageList)
            {
                MessageInfo messageInfo = new MessageInfo(temp);
                messageInfo.setSender(userManager.getUserInfo(temp.getSenderId(), Boolean.FALSE));
                messageInfo.setReceiver(userManager.getUserInfo(temp.getReceiverId(), Boolean.FALSE));
                messageInfoList.add(messageInfo);
            }
        }
        
        PagingData<MessageInfo> messageInfoData = new PagingData<>();
        messageInfoData.setData(messageInfoList);
        messageInfoData.setCount(messageData.getCount());
        
        response.setData(messageInfoData);
        return response;
    }
    
    @RequestMapping(value = "/user/message/list", method = RequestMethod.GET)
    @ResponseBody
    public Response userRelationList(HttpServletRequest request,
        @Valid MessageRelationSearchRequest messageRelationSearchRequest)
    {
        String userId = sessionManager.getSessionUserId(request);
        
        MessageRelation messageRelation = messageRelationSearchRequest.toEntity();
        messageRelation.setUserId(userId);
        
        return getMessageRelationList(messageRelation, userId);
    }
    
    @RequestMapping(value = "/user/contact/list", method = RequestMethod.GET)
    @ResponseBody
    public Response userContactList(HttpServletRequest request,
        @Valid MessageRelationSearchRequest messageRelationSearchRequest)
    {
        String userId = sessionManager.getSessionUserId(request);
        
        MessageRelation messageRelation = messageRelationSearchRequest.toEntity();
        messageRelation.setUserId(userId);
        messageRelation.setIsContact(1);
        
        return getMessageRelationList(messageRelation, userId);
    }
    
    private ResponseData<PagingData<MessageRelationInfo>> getMessageRelationList(MessageRelation messageRelation,
        String userId)
    {
        ResponseData<PagingData<MessageRelationInfo>> response = new ResponseData<>();
        PagingData<MessageRelation> messageData = messageService.seletctRelation(messageRelation);
        List<MessageRelation> messageRelationList = messageData.getData();
        List<MessageRelationInfo> messageInfoList = new ArrayList<>();
        
        if (!CollectionUtils.isEmpty(messageRelationList))
        {
            for (MessageRelation temp : messageRelationList)
            {
                MessageRelationInfo messageRelationInfo = null;
                if (!userId.equals(temp.getUserId()))
                {
                    temp.setContactUserId(temp.getUserId());
                    temp.setUserId(userId);
                }
                setUnread(temp, userId);
                messageRelationInfo = new MessageRelationInfo(temp);
                messageRelationInfo.setContactInfo(userManager.getUserInfo(temp.getContactUserId(), Boolean.FALSE));
                messageInfoList.add(messageRelationInfo);
            }
        }
        
        PagingData<MessageRelationInfo> pagingData = new PagingData<>();
        pagingData.setData(messageInfoList);
        pagingData.setCount(messageData.getCount());
        response.setData(pagingData);
        return response;
    }
    
    private void setUnread(MessageRelation messageRelation, String userId)
    {
        if (StringUtils.isEmpty(messageRelation.getUnread()))
        {
            messageRelation.setUnread("0");
        }
        else
        {
            JsonMapper mapper = new JsonMapper();
            Map<String, Integer> readMapper = mapper.fromJson(messageRelation.getUnread(), Map.class);
            if (null != readMapper)
            {
                messageRelation.setUnread(String.valueOf(readMapper.get(userId)));
            }
        }
    }
    
    private void clearUnread(MessageRelation messageRelation, String userId)
    {
        String unread = messageRelation.getUnread();
        
        if (StringUtils.isEmpty(unread))
        {
            return;
        }
        
        JsonMapper mapper = new JsonMapper();
        Map<String, Integer> readMapper = mapper.fromJson(messageRelation.getUnread(), Map.class);
        
        if (null != readMapper)
        {
            // 刷新内存
            int msgCount = readMapper.get(userId);
            SystemManager.minusMessageCount(userId, msgCount);
            
            readMapper.put(userId, 0);
            String newUnread = mapper.toJson(readMapper);
            // 更新relation数据
            messageRelation.setUnread(newUnread);
            messageService.updateRelationByRelationId(messageRelation);
        }
    }
}
