package com.portal.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.portal.bean.PagingData;
import com.portal.bean.dto.SimpUserInfo;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.MessageRelation;
import com.portal.bean.entity.User;
import com.portal.service.MessageService;
import com.portal.service.UsersService;

@Component
public class UserManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);
    
    private static Map<String, UserInfo> userCache = new HashMap<String, UserInfo>();
    
    private static Lock userCacheLock = new ReentrantLock();
    
    // 聊天关系表
    /**
     * private static Map<String, MessageRelation> messageRelationCache = new HashMap<String, MessageRelation>();
     * 
     * private static Lock messageRelationCacheLock = new ReentrantLock();
     */
    
    @Autowired
    UsersService userService;
    
    @Autowired
    MessageService messageService;
    
    public SimpUserInfo getSimpUserInfo(String userId, Boolean fresh)
    {
        if (StringUtils.isEmpty(userId))
        {
            LOGGER.warn("get simpuser by user id {} , but user id is null..", userId);
            return null;
            
        }
        userCacheLock.lock();
        try
        {
            SimpUserInfo sUserInfo = null;
            
            if (CollectionUtils.isEmpty(userCache) || !userCache.containsKey(userId) || fresh)
            {
                User user = loadUserById(userId);
                
                if (user != null)
                {
                    sUserInfo = new SimpUserInfo(user);
                    UserInfo userInfo = new UserInfo(user);
                    userCache.put(userId, userInfo);
                }
                
            }
            else
            {
                sUserInfo = new SimpUserInfo(userCache.get(userId));
            }
            
            if (sUserInfo != null)
            {
                return sUserInfo;
            }
            
            LOGGER.warn("get simpuser info  by user id {} ,but not exist..", userId);
            return null;
        }
        catch (Exception exception)
        {
            
            LOGGER.error("get simpuser by user id : " + userId + " exception ..", exception);
            return null;
            
        }
        finally
        {
            userCacheLock.unlock();
        }
    }
    
    public UserInfo getUserInfo(String userId, Boolean fresh)
    {
        if (StringUtils.isEmpty(userId))
        {
            LOGGER.warn("get user by user id {} , but user id is null..", userId);
            return null;
            
        }
        userCacheLock.lock();
        try
        {
            UserInfo userInfo = null;
            
            if (CollectionUtils.isEmpty(userCache) || !userCache.containsKey(userId) || fresh)
            {
                User user = loadUserById(userId);
                
                if (user != null)
                {
                    userInfo = new UserInfo(user);
                    userCache.put(userId, userInfo);
                }
            }
            else
            {
                userInfo = userCache.get(userId);
            }
            
            if (userInfo != null)
            {
                return userInfo;
            }
            
            LOGGER.warn("get user info  by user id {} ,but not exist..", userId);
            return null;
        }
        catch (Exception exception)
        {
            
            LOGGER.error("get user by user id : " + userId + " exception ..", exception);
            return null;
            
        }
        finally
        {
            userCacheLock.unlock();
        }
    }
    
    public void refreshUserInfo(String userId)
    {
        if (StringUtils.isEmpty(userId))
        {
            LOGGER.warn("refresh user by user id {} , but user id is null..", userId);
            return;
        }
        userCacheLock.lock();
        try
        {
            UserInfo userInfo = null;
            User user = loadUserById(userId);
            
            if (user != null)
            {
                userInfo = new UserInfo(user);
                userCache.put(userId, userInfo);
            }
        }
        catch (Exception exception)
        {
            LOGGER.error("refresh user by user id : " + userId + " exception ..", exception);
            return;
        }
        finally
        {
            userCacheLock.unlock();
        }
    }
    
    /**
     * public MessageRelation getMessageRelation(String userId, String receiveId) { List<MessageRelation> messageInfos =
     * new ArrayList<>(); messageRelationCacheLock.lock(); try { String key1 = userId + "|" + receiveId; String key2 =
     * receiveId + "|" + userId; if (CollectionUtils.isEmpty(messageRelationCache) ||
     * (!messageRelationCache.containsKey(key1) && !messageRelationCache.containsKey(key2))) { MessageRelation
     * messageRelation = new MessageRelation(); messageRelation.setUserId(userId);
     * messageRelation.setContactUserId(receiveId); messageRelation.setLimit(BaseConstant.PAGING_LIMIT_MAX);
     * messageRelation.setOffset(BaseConstant.PAGING_OFF_SET_START); messageInfos =
     * loadMessageRelation(messageRelation); if (!CollectionUtils.isEmpty(messageInfos)) { for (MessageRelation temp :
     * messageInfos) { messageRelationCache.put(temp.getUserId() + "|" + temp.getContactUserId(), temp); } } }
     * 
     * if (CollectionUtils.isEmpty(messageRelationCache)) { LOGGER.warn("get message info ,but is empty.."); return
     * null; }
     * 
     * if (null != messageRelationCache.get(key1)) { return messageRelationCache.get(key1); }
     * 
     * return messageRelationCache.get(key2); } catch (Exception exception) {
     * 
     * LOGGER.error("get message info error..", exception); return null; } finally { messageRelationCacheLock.unlock();
     * } }
     */
    
    private User loadUserById(String userId)
    {
        if (StringUtils.isEmpty(userId))
        {
            LOGGER.warn("get account by user id {} , but user id is null..", userId);
            return null;
        }
        
        User dbUser = userService.selectById(userId);
        
        if (null != dbUser)
        {
            return dbUser;
        }
        LOGGER.warn("get account by user id  {}, but not exist ..", userId);
        return null;
    }
    
    private List<MessageRelation> loadMessageRelation(MessageRelation messageRelation)
    {
        List<MessageRelation> messageList = messageService.seletctRelation(messageRelation).getData();
        
        if (CollectionUtils.isEmpty(messageList))
        {
            LOGGER.warn("get message info, but message info is null..");
            return new ArrayList<MessageRelation>();
        }
        return messageList;
    }
    
    public static boolean loadCountry()
    {
        // PagingData<Country> pagingData;
        return Boolean.FALSE;
    }
    
    public boolean loadUser()
    {
        User user = new User();
        
        userCacheLock.lock();
        try
        {
            Integer userCount = userService.selectByIndexCount(user);
            Integer pageSize = 100;
            
            if (userCount > 0)
            {
                PagingData<User> userData = new PagingData<User>();
                List<User> userList = new ArrayList<User>();
                int page = userCount / pageSize;
                int left = userCount % pageSize;
                int offset = 0;
                
                for (int i = 1; i <= page + 1; i++)
                {
                    user.setOffset(offset);
                    if (i == page + 1)
                    {
                        user.setLimit(left);
                    }
                    else
                    {
                        user.setLimit(pageSize);
                    }
                    
                    userData = userService.selectByIndex(user);
                    
                    userList = userData.getData();
                    
                    if (!CollectionUtils.isEmpty(userList))
                    {
                        for (User tempUser : userList)
                        {
                            if (!userCache.containsKey(tempUser.getUserId()))
                            {
                                UserInfo userInfo = new UserInfo(tempUser);
                                userCache.put(tempUser.getUserId(), userInfo);
                            }
                        }
                    }
                    
                    offset += page * pageSize;
                }
            }
            
            LOGGER.info("Init user success,the user size is {0}", userCache.size());
            return Boolean.TRUE;
        }
        finally
        {
            userCacheLock.unlock();
        }
    }
    
}