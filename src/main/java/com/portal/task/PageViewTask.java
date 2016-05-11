package com.portal.task;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.common.log.LogCache;

public class PageViewTask
{
    private static final Logger LOGGER = LoggerFactory.getLogger("task-log");
    
    private static final PageViewTask instance = new PageViewTask();
    
    private PageViewTask()
    {
        
    }
    
    public static PageViewTask getInstance()
    {
        return instance;
    }
    
    public static void execute()
        throws SchedulerException
    {
        LOGGER.info("start add page view log task");
        LogCache.saveAllLogCache();
        LOGGER.info("end add page view log task");
    }
}
