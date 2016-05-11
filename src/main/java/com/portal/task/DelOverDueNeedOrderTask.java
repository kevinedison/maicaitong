package com.portal.task;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.common.context.BaseContext;
import com.portal.mapper.TaskMapper;

public class DelOverDueNeedOrderTask
{
    private static final Logger LOGGER = LoggerFactory.getLogger("task-log");
    
    private static final DelOverDueNeedOrderTask instance = new DelOverDueNeedOrderTask();
    
    private DelOverDueNeedOrderTask()
    {
        
    }
    
    public static DelOverDueNeedOrderTask getInstance()
    {
        return instance;
    }
    
    public static void execute()
        throws SchedulerException
    {
        LOGGER.info("start del overdue need order task");
        TaskMapper taskMapper = BaseContext.getBean("taskMapper", TaskMapper.class);
        taskMapper.deleteOverNeedOrder();
        LOGGER.info("end del overdue need order task");
    }
}
