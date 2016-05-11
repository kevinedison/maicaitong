package com.portal.service;

import org.springframework.transaction.annotation.Transactional;

import com.portal.bean.dto.UserInfo;
import com.portal.bean.entity.UserService;

public interface UserServService extends CrudService<UserService>
{
    @Transactional
    Long saveService(UserService service,UserInfo userInfo);
}
