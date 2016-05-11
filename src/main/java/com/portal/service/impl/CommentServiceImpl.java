package com.portal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Comment;
import com.portal.bean.entity.Order;
import com.portal.bean.entity.User;
import com.portal.bean.entity.UserService;
import com.portal.manager.UserManager;
import com.portal.mapper.CommentMapper;
import com.portal.mapper.OrderMapper;
import com.portal.mapper.ServiceMapper;
import com.portal.mapper.UserMapper;
import com.portal.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);
    
    @Autowired
    CommentMapper commentMapper;
    
    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    ServiceMapper serviceMapper;
    
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    UserManager userManager;
    
    @Override
    public Long save(Comment comment)
    {
        return null;
    }
    
    @Override
    public Integer deleteById(Comment entity)
    {
        return commentMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(Comment entity)
    {
        return commentMapper.updateById(entity);
    }
    
    @Override
    public Comment selectById(String id)
    {
        return commentMapper.selectById(id);
    }
    
    @Override
    public Comment selectOne(Comment entity)
    {
        return commentMapper.selectOne(entity);
    }
    
    @Override
    public PagingData<Comment> selectByIndex(Comment entity)
    {
        PagingData<Comment> pagingData = new PagingData<Comment>();
        int count = commentMapper.selectByIndexCount(entity);
        pagingData.setCount(count);
        
        if (count > 0)
        {
            pagingData.setData(commentMapper.selectByIndex(entity));
        }
        
        return pagingData;
    }
    
    @Override
    public Long saveComment(Comment comment, String saleUserId)
    {
        // 保存评论
        Long saveResult = commentMapper.save(comment);
        
        // 更新订单
        Order order = new Order();
        order.setIsComment(1);
        order.setId(comment.getOrderId());
        orderMapper.updateById(order);
        
        // 更新服务 评论数+1
        UserService userService = new UserService();
        userService.setId(comment.getServiceId());
        userService.setCommentcount(1);
        serviceMapper.updateById(userService);
        
        // 更新用户评论总数
        User user = new User();
        user.setUserId(saleUserId);
        user.setCommentCount(1);
        userMapper.updateById(user);
        
        // 刷新用户缓存
        userManager.refreshUserInfo(saleUserId);
        
        return saveResult;
    }
    
}
