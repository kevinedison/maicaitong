package com.portal.mapper;

import java.util.List;

import com.portal.bean.console.StaticService;
import com.portal.bean.entity.Location;
import com.portal.bean.entity.LogInfo;

public interface TaskMapper
{
    List<Location> staticBuyer();
    
    List<Location> staticService();
    
    Long batchUpdateBuyer(List<Location> locationList);
    
    Long saveStatic();
    
    Long updateStaticOrder();
    
    Long insertBatch(List<LogInfo> logInfoList);
    
    Long insertBatchViewStatic(List<StaticService> staticServiceList);
    
    Integer deleteOverNeedOrder();
}
