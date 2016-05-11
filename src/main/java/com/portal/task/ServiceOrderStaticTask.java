package com.portal.task;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.common.context.BaseContext;
import com.portal.mapper.TaskMapper;

public class ServiceOrderStaticTask
{
    private static final Logger LOGGER = LoggerFactory.getLogger("task-log");
    
    private static final ServiceOrderStaticTask instance = new ServiceOrderStaticTask();
    
    private ServiceOrderStaticTask()
    {
        
    }
    
    public static ServiceOrderStaticTask getInstance()
    {
        return instance;
    }
    
    public synchronized static void execute()
        throws SchedulerException
    {
        TaskMapper taskMapper = BaseContext.getBean("taskMapper", TaskMapper.class);
        LOGGER.info("start static service order task");
        
        // 先插入数据
        LOGGER.info("start save the static info");
        taskMapper.saveStatic();
        LOGGER.info("end save the static info");
        
        // 更新数据
        LOGGER.info("start update the static log");
        taskMapper.updateStaticOrder();
        LOGGER.info("end update the static log");
        
        LOGGER.info("end static service order task");
    }
}
