package com.portal.service;

import com.portal.bean.PagingData;
import com.portal.bean.console.StaticService;

public interface StaticServiceService extends CrudService<StaticService>
{
    PagingData<StaticService> selectPageViewByIndex(StaticService entity);
}
