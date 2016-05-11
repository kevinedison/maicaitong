package com.portal.common.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.bean.entity.LogInfo;
import com.portal.common.context.BaseContext;
import com.portal.mapper.TaskMapper;

public final class LogCache
{
    private static final Logger LOGGER = LoggerFactory.getLogger("task-log");
    
    private static Map<String, ConcurrentLinkedQueue<LogInfo>> logCacheMap =
        new HashMap<String, ConcurrentLinkedQueue<LogInfo>>();
    
    private static LogCache instance = new LogCache();
    
    public static LogCache getInstance()
    {
        return instance;
    }
    
    public static void initLogCacheMap(String sql)
    {
        ConcurrentLinkedQueue<LogInfo> logCacheQueue = new ConcurrentLinkedQueue<LogInfo>();
        
        logCacheMap.put(sql, logCacheQueue);
    }
    
    public static void addLogCache(LogInfo logInfo, String sql)
    {
        ConcurrentLinkedQueue<LogInfo> logCacheQueue = logCacheMap.get(sql);
        
        if (null == logCacheQueue)
        {
            initLogCacheMap(sql);
            logCacheQueue = logCacheMap.get(sql);
        }
        
        synchronized (logCacheQueue)
        {
            logCacheQueue.add(logInfo);
        }
        
        LOGGER.info(logInfo.toString());
    }
    
    private void saveCacheList(ConcurrentLinkedQueue<LogInfo> logCacheQueue, String sql)
    {
        LOGGER.info("begin save cached log of {} ", sql);
        
        long startTime = System.currentTimeMillis();
        
        int loopCount = 0;
        try
        {
            List<LogInfo> logInfoList = new ArrayList<LogInfo>();
            int queueSize = logCacheQueue.size();
            TaskMapper taskMapper = BaseContext.getBean("taskMapper", TaskMapper.class);
            while (!logCacheQueue.isEmpty())
            {
                LogInfo log = (LogInfo)logCacheQueue.poll();
                logInfoList.add(log);
                loopCount++;
                if (loopCount % 400 == 0 || loopCount == queueSize)
                {
                    taskMapper.insertBatch(logInfoList);
                    logInfoList.clear();
                }
            }
            
            LOGGER.info("succ batch save logs.log type:{}, log size: {}, cost time : {}",
                new Object[] {sql, Integer.valueOf(loopCount), Long.valueOf(System.currentTimeMillis() - startTime)});
            
            LOGGER.info("end save cached log of {} ", sql);
        }
        catch (Exception e)
        {
            LOGGER.error("call {} throw exception!", e, new Object[] {sql});
        }
    }
    
    public static void saveAllLogCache()
    {
        LOGGER.info("Begin save cached logs ...");
        
        for (Entry<String, ConcurrentLinkedQueue<LogInfo>> temp : logCacheMap.entrySet())
        {
            
            ConcurrentLinkedQueue<LogInfo> logCacheQueue = new ConcurrentLinkedQueue<LogInfo>();
            synchronized (temp.getValue())
            {
                logCacheQueue.addAll(temp.getValue());
                
                temp.getValue().clear();
                
                instance.saveCacheList(logCacheQueue, temp.getKey());
            }
        }
        
        LOGGER.info("End save cached logs ...");
    }
    
}
