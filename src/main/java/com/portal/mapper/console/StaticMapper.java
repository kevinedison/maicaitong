package com.portal.mapper.console;

import java.util.List;

import com.portal.bean.console.StaticService;
import com.portal.mapper.CrudMapper;

public interface StaticMapper extends CrudMapper<StaticService>
{
    List<StaticService> dayStaticPageView();
    
    Integer batchUpdatePageView();
    
    List<StaticService> selectPageViewByIndex(StaticService entity);
    
    Integer selectPageViewByIndexCount(StaticService entity);
}
