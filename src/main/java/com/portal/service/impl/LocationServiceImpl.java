package com.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Location;
import com.portal.mapper.LocationMapper;
import com.portal.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationMapper locationMapper;

	@Override
	public Long save(Location entity) {
		return locationMapper.save(entity);
	}

	@Override
	public Integer deleteById(Location entity) {
		return locationMapper.deleteById(entity);
	}

	@Override
	public Integer updateById(Location entity) {
		return locationMapper.updateById(entity);
	}

	@Override
	public Location selectById(String id) {
		return locationMapper.selectById(id);
	}

	@Override
	public Location selectOne(Location entity) {
		return locationMapper.selectOne(entity);
	}

	@Override
	public PagingData<Location> selectByIndex(Location entity) {
        PagingData<Location> pagingData = new PagingData<Location>();
        List<Location> countrys = locationMapper.selectByIndex(entity);
        if(!CollectionUtils.isEmpty(countrys)) {
        	pagingData.setData(countrys);
        	pagingData.setCount(countrys.size());
        }
		return pagingData;
	}	
}
