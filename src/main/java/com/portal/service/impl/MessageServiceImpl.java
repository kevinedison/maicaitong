package com.portal.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Message;
import com.portal.bean.entity.MessageRelation;
import com.portal.common.mapper.JsonMapper;
import com.portal.constant.BaseConstant;
import com.portal.manager.SystemManager;
import com.portal.manager.UserManager;
import com.portal.mapper.MessageMapper;
import com.portal.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
    
    @Autowired
    MessageMapper messageMapper;
    
    @Autowired
    UserManager userManager;
    
    @Override
    public Long save(Message entity)
    {
        if (StringUtils.isEmpty(entity.getRelationId()))
        {
            MessageRelation messageRelation = new MessageRelation();
            messageRelation.setUserId(entity.getSenderId());
            messageRelation.setContactUserId(entity.getReceiverId());
            messageRelation.setOffset(BaseConstant.PAGING_OFF_SET_START);
            messageRelation.setLimit(BaseConstant.PAGING_LIMIT_MAX);
            // 判断关系ID是否为空
            MessageRelation relationData = seletctSaveRelation(messageRelation);
            
            if (null == relationData)
            {
                // 插入关系表
                messageRelation.setRecentMessage(entity.getMessage());
                messageRelation.setIsContact(0);
                messageRelation.setUnread("{\"" + entity.getSenderId() + "\":0,\"" + entity.getReceiverId() + "\":1}");
                messageRelation.setMessageType(entity.getMessageType());
                messageMapper.saveRelation(messageRelation);
            }
            else
            {
                // 更新关系表
                messageRelation.setRecentMessage(entity.getMessage());
                messageRelation.setId(relationData.getId());
                messageRelation.setMessageType(entity.getMessageType());
                String empty = "{\"" + entity.getSenderId() + "\":0,\"" + entity.getReceiverId() + "\":0}";
                messageRelation.setUnread(getUnread(relationData.getUnread(), entity.getReceiverId(), empty));
                messageMapper.updateRelationByRelationId(messageRelation);
            }
            entity.setRelationId(messageRelation.getId());
        }
        else
        {
            MessageRelation messageRelation = new MessageRelation();
            messageRelation.setId(entity.getRelationId());
            messageRelation.setUserId(entity.getSenderId());
            messageRelation.setMessageType(entity.getMessageType());
            MessageRelation queryMessageRelation = messageMapper.seletctSaveRelation(messageRelation);
            String empty = "{\"" + entity.getSenderId() + "\":0,\"" + entity.getReceiverId() + "\":0}";
            messageRelation.setUnread(getUnread(queryMessageRelation.getUnread(), entity.getReceiverId(), empty));
            
            messageRelation.setRecentMessage(entity.getMessage());
            messageMapper.updateRelationByRelationId(messageRelation);
        }
        
        SystemManager.plusMessageCount(entity.getReceiverId(), 1);
        
        Message lastMessage = messageMapper.selectLatestTime(entity.getRelationId());
        setTime(lastMessage, entity);
        return messageMapper.save(entity);
    }
    
    private void setTime(Message lastMessage, Message entity)
    {
        Date data = new Date();
        Long now = data.getTime();
        
        if (null == lastMessage)
        {
            entity.setMessageTime(new Timestamp(now));
        }
        else
        {
            Long last = null != lastMessage.getMessageTime() ? lastMessage.getMessageTime().getTime() : now;
            if ((now - last) / 1000 / 60 >= BaseConstant.MAX_MESSAGE_TIME)
            {
                entity.setMessageTime(new Timestamp(now));
            }
        }
        entity.setOperateTime(new Timestamp(now));
    }
    
    private String getUnread(String unread, String key, String empty)
    {
        JsonMapper mapper = new JsonMapper();
        Map<String, Integer> unreadMap = new HashMap<>();
        if (!StringUtils.isEmpty(unread))
        {
            unreadMap = mapper.fromJson(unread, Map.class);
        }
        else
        {
            unreadMap = mapper.fromJson(empty, Map.class);
        }
        
        unreadMap.put(key, unreadMap.get(key) + 1);
        return mapper.toJson(unreadMap);
    }
    
    @Override
    public Integer deleteById(Message entity)
    {
        return messageMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(Message entity)
    {
        return messageMapper.updateById(entity);
    }
    
    @Override
    public Message selectById(String id)
    {
        return messageMapper.selectById(id);
    }
    
    @Override
    public Message selectOne(Message entity)
    {
        return messageMapper.selectOne(entity);
    }
    
    @Override
    public PagingData<Message> selectByIndex(Message entity)
    {
        PagingData<Message> pagingData = new PagingData<Message>();
        int count = messageMapper.selectByIndexCount(entity);
        pagingData.setCount(count);
        
        if (count > 0)
        {
            pagingData.setData(messageMapper.selectByIndex(entity));
        }
        
        return pagingData;
    }
    
    @Override
    public PagingData<MessageRelation> seletctRelation(MessageRelation messageRelation)
    {
        PagingData<MessageRelation> pagingData = new PagingData<>();
        int count = messageMapper.seletctRelationCount(messageRelation);
        pagingData.setCount(count);
        
        if (count > 0)
        {
            pagingData.setData(messageMapper.seletctRelation(messageRelation));
        }
        
        return pagingData;
    }
    
    @Override
    public Long saveRelation(MessageRelation messageRelation)
    {
        return messageMapper.saveRelation(messageRelation);
    }
    
    @Override
    public Integer updateRelationByRelationId(MessageRelation messageRelation)
    {
        return messageMapper.updateRelationByRelationId(messageRelation);
    }
    
    @Override
    public Integer deleteMessageByRelId(String id)
    {
        return messageMapper.deleteMessageByRelId(id);
    }
    
    @Override
    public MessageRelation seletctSaveRelation(MessageRelation messageRelation)
    {
        return messageMapper.seletctSaveRelation(messageRelation);
    }
    
    public static void main(String[] args)
    {
        String xxx = "{\"entity.getSenderId()\":0,\"entity.getReceiverId()\":1}";
        JsonMapper mapper = new JsonMapper();
        Map a = mapper.fromJson(xxx, Map.class);
        System.out.println(a.keySet().size());
    }
}
