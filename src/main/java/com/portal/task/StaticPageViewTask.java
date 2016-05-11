package com.portal.task;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.bean.console.StaticService;
import com.portal.bean.entity.Need;
import com.portal.bean.entity.UserService;
import com.portal.common.context.BaseContext;
import com.portal.common.mapper.JsonMapper;
import com.portal.mapper.TaskMapper;
import com.portal.mapper.console.StaticMapper;
import com.portal.service.NeedService;
import com.portal.service.UserServService;

public class StaticPageViewTask
{
    private static final Logger LOGGER = LoggerFactory.getLogger("task-log");
    
    private static final StaticPageViewTask instance = new StaticPageViewTask();
    
    private static Lock pageViewLock = new ReentrantLock();
    
    private StaticPageViewTask()
    {
        
    }
    
    public static StaticPageViewTask getInstance()
    {
        return instance;
    }
    
    public static void execute()
        throws SchedulerException
    {
        LOGGER.info("start static page view task");
        TaskMapper taskMapper = BaseContext.getBean("taskMapper", TaskMapper.class);
        StaticMapper staticMapper = BaseContext.getBean("staticMapper", StaticMapper.class);
        NeedService needService = BaseContext.getBean("needServiceImpl", NeedService.class);
        UserServService userServService = BaseContext.getBean("userServServiceImpl", UserServService.class);
        
        // 先查询出统计的总数据
        try
        {
            pageViewLock.lock();
            List<StaticService> pageViewList = staticMapper.dayStaticPageView();
            
            if (pageViewList.size() > 0)
            {
                // 更新
                staticMapper.batchUpdatePageView();
                
                JsonMapper mapper = new JsonMapper();
                
                // 遍历，查询对应的服务
                for (StaticService temp : pageViewList)
                {
                    // 求购
                    if (temp.getServiceId().startsWith("N"))
                    {
                        // 获取求购
                        Need need = needService.selectById(temp.getServiceId());
                        if (null != need)
                        {
                            temp.setUserId(need.getUserId());
                            temp.setServiceContent(mapper.toJson(need));
                        }
                    }
                    // 服务
                    else if (temp.getServiceId().startsWith("S"))
                    {
                        UserService service = userServService.selectById(temp.getServiceId());
                        if (null != service)
                        {
                            temp.setUserId(service.getUserId());
                            temp.setServiceContent(mapper.toJson(service));
                        }
                    }
                }
                
                // 插入统计数据
                taskMapper.insertBatchViewStatic(pageViewList);
            }
        }
        finally
        {
            pageViewLock.unlock();
        }
        
        LOGGER.info("end static page view task");
    }
}
