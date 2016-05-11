package com.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.User;
import com.portal.mapper.UserMapper;
import com.portal.service.UsersService;

@Service
public class UserServiceImpl implements UsersService
{
    
    @Autowired
    UserMapper userMapper;
    
    @Override
    public Long save(User entity)
    {
        return userMapper.save(entity);
    }
    
    @Override
    public Integer deleteById(User entity)
    {
        return null;
    }
    
    @Override
    public Integer updateById(User entity)
    {
        return userMapper.updateById(entity);
    }
    
    @Override
    public User selectById(String id)
    {
        return userMapper.selectById(id);
    }
    
    @Override
    public User selectOne(User entity)
    {
        return userMapper.selectOne(entity);
    }
    
    @Override
    public PagingData<User> selectByIndex(User entity)
    {
        PagingData<User> pagingData = new PagingData<User>();
        int count = userMapper.selectByIndexCount(entity);
        if (count > 0)
        {
            pagingData.setData(userMapper.selectByIndex(entity));
        }
        pagingData.setCount(count);
        return pagingData;
    }
    
    @Override
    public User selectLoginInfo(String account)
    {
        return userMapper.selectLoginInfo(account);
    }
    
    @Override
    public Integer selectByIndexCount(User entity)
    {
        return userMapper.selectByIndexCount(entity);
    }
    
}
