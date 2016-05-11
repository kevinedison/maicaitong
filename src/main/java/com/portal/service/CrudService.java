package com.portal.service;

import org.springframework.transaction.annotation.Transactional;

import com.portal.bean.PagingData;

public interface CrudService<T> {
	
    @Transactional
	Long save(T entity);
	
	Integer deleteById(T entity);
	
	Integer updateById(T entity);
	
	T selectById(String id);
	
	T selectOne(T entity);
	
	PagingData<T> selectByIndex(T entity);
}
