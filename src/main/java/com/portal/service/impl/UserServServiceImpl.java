package com.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.User;
import com.portal.bean.entity.UserService;
import com.portal.common.mapper.JsonMapper;
import com.portal.mapper.ServiceMapper;
import com.portal.mapper.UserMapper;
import com.portal.service.UserServService;

@Service
public class UserServServiceImpl implements UserServService
{
    
    @Autowired
    ServiceMapper serviceMapper;
    
    @Autowired
    UserMapper userMapper;
    
    @Override
    public Long save(UserService entity)
    {
        return null;
    }
    
    @Override
    public Integer deleteById(UserService entity)
    {
        return serviceMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(UserService entity)
    {
        return serviceMapper.updateById(entity);
    }
    
    @Override
    public UserService selectById(String id)
    {
        return serviceMapper.selectById(id);
    }
    
    @Override
    public UserService selectOne(UserService entity)
    {
        return serviceMapper.selectOne(entity);
    }
    
    @Override
    public PagingData<UserService> selectByIndex(UserService entity)
    {
        PagingData<UserService> pagingData = new PagingData<UserService>();
        int count = serviceMapper.selectByIndexCount(entity);
        if (count > 0)
        {
            pagingData.setData(serviceMapper.selectByIndex(entity));
        }
        pagingData.setCount(count);
        return pagingData;
    }
    
    @Override
    public Long saveService(UserService service, UserInfo userInfo)
    {
        String serviceType = userInfo.getServicetype();
        List<String> serviceTypeList = new ArrayList<String>();
        JsonMapper mapper = new JsonMapper();
        
        if (!StringUtils.isEmpty(serviceType))
        {
            String[] serviceTypeArray = mapper.fromJson(serviceType, String[].class);
            
            for (String temp : serviceTypeArray)
            {
                serviceTypeList.add(temp);
            }
        }
        
        if (!serviceTypeList.contains(service.getCategory()))
        {
            serviceTypeList.add(service.getCategory());
        }
        
        Object[] newArray = serviceTypeList.toArray();
        
        User user = new User();
        user.setUserId(userInfo.getUserId());
        user.setServicetype(mapper.toJson(newArray));
        
        // 更新用户信息
        userMapper.updateById(user);
        
        return serviceMapper.save(service);
    }
    
    public static void main(String[] args)
    {
        // ["milk","bag","cosmetics","special"]
    }
}
