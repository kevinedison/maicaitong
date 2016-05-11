package com.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.OptLog;
import com.portal.mapper.OptLogMapper;
import com.portal.service.OptLogService;

@Service
public class OptLogServiceImpl implements OptLogService {

	@Autowired
	OptLogMapper optLogMapper;

	@Override
	public Long save(OptLog entity) {
		return optLogMapper.save(entity);
	}

	@Override
	public Integer deleteById(OptLog entity) {
		return null;
	}

	@Override
	public Integer updateById(OptLog entity) {
		return optLogMapper.updateById(entity);
	}

	@Override
	public OptLog selectById(String id) {
		return optLogMapper.selectById(id);
	}

	@Override
	public OptLog selectOne(OptLog entity) {
		return optLogMapper.selectOne(entity);
	}

	@Override
	public PagingData<OptLog> selectByIndex(OptLog entity) {
        PagingData<OptLog> pagingData = new PagingData<OptLog>();
		
		int rowCount = optLogMapper.selectByIndexCount(entity);
		pagingData.setCount(rowCount);
		
		if (rowCount > 0) {
			pagingData.setData(optLogMapper.selectByIndex(entity));
		}
		
		return pagingData;
	}	
}
