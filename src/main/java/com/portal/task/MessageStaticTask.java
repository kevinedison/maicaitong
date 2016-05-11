package com.portal.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.portal.bean.entity.MessageRelation;
import com.portal.common.context.BaseContext;
import com.portal.common.mapper.JsonMapper;
import com.portal.constant.BaseConstant;
import com.portal.manager.SystemManager;
import com.portal.mapper.MessageMapper;

public class MessageStaticTask
{
    private static final Logger LOGGER = LoggerFactory.getLogger("task-log");
    
    private static final MessageStaticTask instance = new MessageStaticTask();
    
    private static Map<String, Integer> unreadMap = new ConcurrentHashMap<String, Integer>();
    
    private MessageStaticTask()
    {
        
    }
    
    public static MessageStaticTask getInstance()
    {
        return instance;
    }
    
    public static void execute()
    {
        Long start = System.currentTimeMillis();
        unreadMap.clear();
        LOGGER.info("start static user unread message task");
        MessageMapper messageMapper = BaseContext.getBean("messageMapper", MessageMapper.class);
        
        MessageRelation messageRelation = new MessageRelation();
        Integer messageListCount = messageMapper.seletctRelationCount(messageRelation);
        LOGGER.info("the messageList count is:" + messageListCount);
        
        int times = messageListCount / BaseConstant.QUEYRCOUNT_PER_TIME;
        int last = messageListCount % BaseConstant.QUEYRCOUNT_PER_TIME;
        
        List<MessageRelation> messageRelList;
        Map<String, Integer> tempMap;
        JsonMapper mapper = new JsonMapper();
        
        for (int i = 0; i <= times; i++)
        {
            if (i == times)
            {
                messageRelation.setLimit(last);
            }
            else
            {
                messageRelation.setLimit(BaseConstant.QUEYRCOUNT_PER_TIME);
            }
            
            messageRelation.setOffset(i * BaseConstant.QUEYRCOUNT_PER_TIME);
            
            messageRelList = messageMapper.seletctRelation(messageRelation);
            
            if (CollectionUtils.isEmpty(messageRelList))
            {
                continue;
            }
            
            for (MessageRelation relation : messageRelList)
            {
                String unread = relation.getUnread();
                
                if (!StringUtils.isEmpty(unread))
                {
                    tempMap = mapper.fromJson(unread, Map.class);
                    
                    for (Map.Entry<String, Integer> temp : tempMap.entrySet())
                    {
                        String key = temp.getKey();
                        Integer value = temp.getValue();
                        if (unreadMap.containsKey(key))
                        {
                            unreadMap.put(key, unreadMap.get(key) + value);
                        }
                        else
                        {
                            unreadMap.put(key, value);
                        }
                    }
                }
                
            }
        }
        
//        for (Map.Entry<String, Integer> temp : unreadMap.entrySet())
//        {
//            System.out.println(temp.getKey());
//            System.out.println(temp.getValue());
//        }
        
        LOGGER.info("unreadMap.size()==" + unreadMap.size());
        
        SystemManager.setMessageMap(unreadMap);
        
        LOGGER.info("end static user unread message task:" + (System.currentTimeMillis() - start));
    }
}
