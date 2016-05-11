package com.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.console.Category;
import com.portal.common.mapper.JsonMapper;
import com.portal.mapper.console.CategoryMapper;
import com.portal.rest.PayController;
import com.portal.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);
    
    @Autowired
    CategoryMapper categoryMapper;
    
    @Override
    public Long save(Category entity)
    {
        return categoryMapper.save(entity);
    }
    
    @Override
    public Integer deleteById(Category entity)
    {
        return categoryMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(Category entity)
    {
        return categoryMapper.updateById(entity);
    }
    
    @Override
    public Category selectById(String id)
    {
        Category category = categoryMapper.selectById(id);
        return parseCategory(category);
    }
    
    @Override
    public Category selectOne(Category entity)
    {
        return categoryMapper.selectOne(entity);
    }
    
    @Override
    public PagingData<Category> selectByIndex(Category entity)
    {
        PagingData<Category> pagingData = new PagingData<Category>();
        int count = categoryMapper.selectByIndexCount(entity);
        pagingData.setCount(count);
        
        if (count > 0)
        {
            pagingData.setData(categoryMapper.selectByIndex(entity));
        }
        
        return pagingData;
    }
    
    public Integer selectByIndexCount(Category entity)
    {
        return categoryMapper.selectByIndexCount(entity);
    }
    
    @Override
    public Category selectByCode(String code)
    {
        Category category = categoryMapper.selectByCode(code);
        return parseCategory(category);
    }
    
    private Category parseCategory(Category category)
    {
        if (null != category)
        {
            String brand = category.getBrand();
            String subType = category.getSubType();
            JsonMapper mapper = new JsonMapper();
            if (StringUtils.isEmpty(brand))
            {
                category.setBrands(new ArrayList<String>());
            }
            else
            {
                category.setBrands(mapper.fromJson(brand, List.class));
            }
            
            if (StringUtils.isEmpty(subType))
            {
                category.setSubTypes(new HashMap<String, String>());
            }
            else
            {
                category.setSubTypes(mapper.fromJson(subType, Map.class));
            }
        }
        
        return category;
    }
}
