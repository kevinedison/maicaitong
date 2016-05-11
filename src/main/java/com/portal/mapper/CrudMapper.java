package com.portal.mapper;

import java.util.List;

public interface CrudMapper<T> {
	
	Long save(T entity);
	
	Integer deleteById(T entity);
	
	Integer updateById(T entity);
	
	T selectById(String id);
	
	T selectOne(T entity);
	
	List<T> selectByIndex(T entity);
	
	Integer selectByIndexCount(T entity);
}
