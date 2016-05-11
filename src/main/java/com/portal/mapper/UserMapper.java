package com.portal.mapper;

import java.util.List;

import com.portal.bean.entity.SimpUser;
import com.portal.bean.entity.User;

public interface UserMapper extends CrudMapper<User>
{
    List<SimpUser> selectByIndexSimp(User user);
    
    SimpUser selectOneSimp(User user);
    
    List<SimpUser> selectByCitySimp(String cityId);
    
    User selectLoginInfo(String account);  
}
