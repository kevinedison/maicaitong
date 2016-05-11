package com.portal.manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.portal.bean.PagingData;
import com.portal.bean.console.Brand;
import com.portal.bean.console.Category;
import com.portal.common.mapper.JsonMapper;
import com.portal.service.BrandService;
import com.portal.service.CategoryService;

@Component
public class CategoryBrandManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryBrandManager.class);
    
    private static Map<String, Category> categoryCache = new LinkedHashMap<String, Category>();
    
    private static Lock categoryCacheLock = new ReentrantLock();
    
    private static Map<String, Brand> brandCache = new LinkedHashMap<String, Brand>();
    
    private static Lock brandCacheLock = new ReentrantLock();
    
    @Autowired
    CategoryService categoryService;
    
    @Autowired
    BrandService brandService;
    
    public Category getCategory(String code, Boolean fresh)
    {
        if (StringUtils.isEmpty(code))
        {
            LOGGER.warn("get category by category code {} , but category code is null..", code);
            return null;
            
        }
        categoryCacheLock.lock();
        try
        {
            Category category = null;
            
            if (CollectionUtils.isEmpty(categoryCache) || !categoryCache.containsKey(code) || fresh)
            {
                category = loadCategoryByCode(code);
                
                if (category != null)
                {
                    categoryCache.put(code, category);
                }
            }
            
            if (category != null)
            {
                return category;
            }
            
            LOGGER.warn("get category info  by category code {} ,but not exist..", code);
            return null;
        }
        catch (Exception exception)
        {
            LOGGER.error("get category by category code : " + code + " exception ..", exception);
            return null;
        }
        finally
        {
            categoryCacheLock.unlock();
        }
    }
    
    private Category loadCategoryByCode(String code)
    {
        if (StringUtils.isEmpty(code))
        {
            LOGGER.warn("get category by category code {} , but category code is null..", code);
            return null;
        }
        
        Category category = categoryService.selectByCode(code);
        
        if (null != category)
        {
            return category;
        }
        LOGGER.warn("get category by category code  {}, but not exist ..", code);
        return null;
    }
    
    public boolean loadCategory()
    {
        Category category = new Category();
        
        categoryCacheLock.lock();
        try
        {
            PagingData<Category> categoryData = new PagingData<Category>();
            List<Category> categoryList = new ArrayList<Category>();
            
            category.setOffset(0);
            category.setLimit(999);
            
            categoryData = categoryService.selectByIndex(category);
            
            categoryList = categoryData.getData();
            
            if (!CollectionUtils.isEmpty(categoryList))
            {
                categoryCache.clear();
                for (Category tempCategory : categoryList)
                {
                    if (!categoryCache.containsKey(tempCategory.getCode()))
                    {
                        categoryCache.put(tempCategory.getCode(), tempCategory);
                    }
                }
            }
            
            LOGGER.info("Init category success,the category size is {}", categoryCache.size());
            JsonMapper mapper = new JsonMapper();
            LOGGER.info("Init category success,the category is {}", mapper.toJson(categoryCache));
            return Boolean.TRUE;
        }
        finally
        {
            categoryCacheLock.unlock();
        }
    }
    
    public boolean loadBrand()
    {
        Brand brand = new Brand();
        
        brandCacheLock.lock();
        try
        {
            PagingData<Brand> brandData = new PagingData<Brand>();
            List<Brand> brandList = new ArrayList<Brand>();
            
            brand.setOffset(0);
            brand.setLimit(999);
            
            brandData = brandService.selectByIndex(brand);
            
            brandList = brandData.getData();
            
            if (!CollectionUtils.isEmpty(brandList))
            {
                brandCache.clear();
                for (Brand tempBrand : brandList)
                {
                    if (!brandCache.containsKey(tempBrand.getCode()))
                    {
                        brandCache.put(tempBrand.getCode(), tempBrand);
                    }
                }
            }
            
            LOGGER.info("Init brand success,the brand size is {}", brandCache.size());
            JsonMapper mapper = new JsonMapper();
            LOGGER.info("Init brand success,the brand is {}", mapper.toJson(brandCache));
            return Boolean.TRUE;
        }
        finally
        {
            brandCacheLock.unlock();
        }
    }
    
    public Map<String, Category> getCategory()
    {
        try
        {
            categoryCacheLock.lock();
            if (CollectionUtils.isEmpty(categoryCache))
            {
                loadCategory();
            }
            return categoryCache;
        }
        finally
        {
            categoryCacheLock.unlock();
        }
    }
    
    public Map<String, Brand> getBrand()
    {
        try
        {
            brandCacheLock.lock();
            if (CollectionUtils.isEmpty(brandCache))
            {
                loadBrand();
            }
            return brandCache;
        }
        finally
        {
            brandCacheLock.unlock();
        }
    }
}