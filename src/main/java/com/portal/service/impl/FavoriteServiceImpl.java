package com.portal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Favorite;
import com.portal.mapper.FavoriteMapper;
import com.portal.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteServiceImpl.class);
    
    @Autowired
    FavoriteMapper favoriteMapper;
    
    @Override
    public Long save(Favorite entity)
    {
        return favoriteMapper.save(entity);
    }
    
    @Override
    public Integer deleteById(Favorite entity)
    {
        return favoriteMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(Favorite entity)
    {
        return favoriteMapper.updateById(entity);
    }
    
    @Override
    public Favorite selectById(String id)
    {
        return favoriteMapper.selectById(id);
    }
    
    @Override
    public Favorite selectOne(Favorite entity)
    {
        return null;
    }
    
    @Override
    public PagingData<Favorite> selectByIndex(Favorite entity)
    {
        PagingData<Favorite> pagingData = new PagingData<Favorite>();
        int count = favoriteMapper.selectByIndexCount(entity);
        pagingData.setCount(count);
        
        if (count > 0)
        {
            pagingData.setData(favoriteMapper.selectByIndex(entity));
        }
        
        return pagingData;
    }
}
