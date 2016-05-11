package com.portal.bean;

import java.util.List;


/**
 * @author: hongjiao.hj
 * @Date: 2014-09-04
 * @Time: 下午3:44
 */
public class PagingData<T> {
	
	private List<T> data;
	private Integer count = 0;

    public PagingData(List<T> data,Integer count) {
		super();
		this.setData(data);
		this.count = count;
	}

    public PagingData() {
		super();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	} 
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
