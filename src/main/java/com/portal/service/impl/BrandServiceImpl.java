package com.portal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.console.Brand;
import com.portal.mapper.console.BrandMapper;
import com.portal.rest.PayController;
import com.portal.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);
    
    @Autowired
    BrandMapper brandMapper;
    
    @Override
    public Long save(Brand entity)
    {
        return brandMapper.save(entity);
        
        // 刷新缓存
        // userManager.refreshUserInfo(entity.getUserId());
    }
    
    @Override
    public Integer deleteById(Brand entity)
    {
        return brandMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(Brand entity)
    {
        return brandMapper.updateById(entity);
    }
    
    @Override
    public Brand selectById(String id)
    {
        return brandMapper.selectById(id);
    }
    
    @Override
    public Brand selectOne(Brand entity)
    {
        return null;
    }
    
    @Override
    public PagingData<Brand> selectByIndex(Brand entity)
    {
        PagingData<Brand> pagingData = new PagingData<Brand>();
        int count = brandMapper.selectByIndexCount(entity);
        pagingData.setCount(count);
        
        if (count > 0)
        {
            pagingData.setData(brandMapper.selectByIndex(entity));
        }
        
        return pagingData;
    }
    
}
