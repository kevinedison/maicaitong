package com.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Feedback;
import com.portal.mapper.FeedbackMapper;
import com.portal.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	FeedbackMapper feedbackMapper;

	@Override
	public Long save(Feedback entity) {
		return feedbackMapper.save(entity);
	}

	@Override
	public Integer deleteById(Feedback entity) {
		return feedbackMapper.deleteById(entity);
	}

	@Override
	public Integer updateById(Feedback entity) {
		return feedbackMapper.updateById(entity);
	}

	@Override
	public Feedback selectById(String id) {
		return feedbackMapper.selectById(id);
	}

	@Override
	public Feedback selectOne(Feedback entity) {
		return feedbackMapper.selectOne(entity);
	}

	@Override
	public PagingData<Feedback> selectByIndex(Feedback entity) {
        PagingData<Feedback> pagingData = new PagingData<Feedback>();
        int count = feedbackMapper.selectByIndexCount(entity);
        
        if (count > 0)
        {
            pagingData.setData(feedbackMapper.selectByIndex(entity));
        }
        pagingData.setCount(count);
        return pagingData;
	}
}
