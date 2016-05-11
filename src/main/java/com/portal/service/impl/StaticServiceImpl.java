package com.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.console.StaticService;
import com.portal.mapper.console.StaticMapper;
import com.portal.service.StaticServiceService;

@Service
public class StaticServiceImpl implements StaticServiceService
{
    @Autowired
    StaticMapper staticMapper;
    
    @Override
    public Long save(StaticService entity)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Integer deleteById(StaticService entity)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Integer updateById(StaticService entity)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public StaticService selectById(String id)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public StaticService selectOne(StaticService entity)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public PagingData<StaticService> selectByIndex(StaticService entity)
    {
        int count = staticMapper.selectByIndexCount(entity);
        PagingData<StaticService> pagingData = new PagingData<StaticService>();
        if (count > 0)
        {
            pagingData.setData(staticMapper.selectByIndex(entity));
        }
        pagingData.setCount(count);
        return pagingData;
    }
    
    @Override
    public PagingData<StaticService> selectPageViewByIndex(StaticService entity)
    {
        int count = staticMapper.selectPageViewByIndexCount(entity);
        PagingData<StaticService> pagingData = new PagingData<StaticService>();
        if (count > 0)
        {
            pagingData.setData(staticMapper.selectPageViewByIndex(entity));
        }
        pagingData.setCount(count);
        return pagingData;
    }
}
