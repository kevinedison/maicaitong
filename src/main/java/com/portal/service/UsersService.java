package com.portal.service;

import com.portal.bean.entity.User;

public interface UsersService extends CrudService<User>
{
    User selectLoginInfo(String account);
    
    Integer selectByIndexCount(User entity);
}
