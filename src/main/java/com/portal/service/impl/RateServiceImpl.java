package com.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Rate;
import com.portal.mapper.RateMapper;
import com.portal.service.RateService;

@Service
public class RateServiceImpl implements RateService {

	@Autowired
	RateMapper rateMapper;

	@Override
	public Long save(Rate entity) {
		return rateMapper.save(entity);
	}

	@Override
	public Integer deleteById(Rate entity) {
		return rateMapper.deleteById(entity);
	}

	@Override
	public Integer updateById(Rate entity) {
		return rateMapper.updateById(entity);
	}

	@Override
	public Rate selectById(String id) {
		return rateMapper.selectById(id);
	}

	@Override
	public Rate selectOne(Rate entity) {
		return rateMapper.selectOne(entity);
	}

	@Override
	public PagingData<Rate> selectByIndex(Rate entity) {
        PagingData<Rate> pagingData = new PagingData<Rate>();
        List<Rate> countrys = rateMapper.selectByIndex(entity);
        if(!CollectionUtils.isEmpty(countrys)) {
        	pagingData.setData(countrys);
        	pagingData.setCount(countrys.size());
        }
		return pagingData;
	}
}
