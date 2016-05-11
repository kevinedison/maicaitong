package com.portal.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.portal.bean.entity.Location;
import com.portal.common.context.BaseContext;
import com.portal.mapper.TaskMapper;

public class SeviceStaticTask
{
    private static final Logger LOGGER = LoggerFactory.getLogger("task-log");
    
    private static final SeviceStaticTask instance = new SeviceStaticTask();
    
    private SeviceStaticTask()
    {
        
    }
    
    public static SeviceStaticTask getInstance()
    {
        return instance;
    }
    
    public static void execute()
        throws SchedulerException
    {
        TaskMapper taskMapper = BaseContext.getBean("taskMapper", TaskMapper.class);
        LOGGER.info("start static buyer and service task");
        List<Location> buyerList = taskMapper.staticBuyer();
        Map<String, Location> locationMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(buyerList))
        {
            for (Location temp : buyerList)
            {
                temp.setServiceCount(0);
                locationMap.put(StringUtils.lowerCase(temp.getCode()), temp);
            }
            
        }
        
        List<Location> serviceList = taskMapper.staticService();
        if (!CollectionUtils.isEmpty(serviceList))
        {
            for (Location temp : serviceList)
            {
                if (locationMap.containsKey(StringUtils.lowerCase(temp.getCode())))
                {
                    locationMap.get(StringUtils.lowerCase(temp.getCode())).setServiceCount(temp.getServiceCount());
                }
                else
                {
                    temp.setBuyerCount(0);
                    locationMap.put(StringUtils.lowerCase(temp.getCode()), temp);
                }
            }
            
        }
        
        List<Location> locationList = new ArrayList(locationMap.values());
        LOGGER.info("locationList.size()==" + locationList.size());
        
        if (!CollectionUtils.isEmpty(locationList))
        {
            taskMapper.batchUpdateBuyer(locationList);
        }
        
        LOGGER.info("end static buyer and service task");
    }
}
