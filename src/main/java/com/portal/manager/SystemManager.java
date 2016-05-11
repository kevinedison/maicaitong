package com.portal.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SystemManager
{
    // 未读消息缓存
    private static final Map<String, Integer> messageMap = new ConcurrentHashMap<>();
    
    private static Lock messageLock = new ReentrantLock();
    
    public static void setMessageMap(Map<String, Integer> map)
    {
        try
        {
            messageLock.lock();
            
            messageMap.clear();
            if (CollectionUtils.isEmpty(map))
            {
                return;
            }
            messageMap.putAll(map);
            
        }
        finally
        {
            messageLock.unlock();
        }
    }
    
    public static Integer getMessageCount(String userId)
    {
        try
        {
            messageLock.lock();
            return null == messageMap.get(userId) ? 0 : messageMap.get(userId);
        }
        finally
        {
            messageLock.unlock();
        }
    }
    
    public static void minusMessageCount(String userId, Integer count)
    {
        try
        {
            messageLock.lock();
            if (null == messageMap.get(userId))
            {
                return;
            }
            
            if (messageMap.get(userId) - count < 0)
            {
                messageMap.put(userId, 0);
            }
            else
            {
                messageMap.put(userId, messageMap.get(userId) - count);
            }
        }
        finally
        {
            messageLock.unlock();
        }
    }
    
    public static void plusMessageCount(String userId, Integer count)
    {
        try
        {
            messageLock.lock();
            if (null == messageMap.get(userId))
            {
                messageMap.put(userId, count);
            }
            else
            {
                messageMap.put(userId, messageMap.get(userId) + count);
            }
            
            for (Map.Entry<String, Integer> temp : messageMap.entrySet())
            {
                System.out.println(temp.getKey());
                System.out.println(temp.getValue());
            }
            
        }
        finally
        {
            messageLock.unlock();
        }
    }
    
}
