package com.portal.bean;

import org.springframework.beans.BeanUtils;

import com.portal.bean.entity.base.PagingEntity;

public class RequestPage extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7584671848291002123L;
	
	private Integer page;
	
	private Integer pageSize;
	
	private String sortBy="1";
	
	private String sortType="1";
	
	public Integer getPage() {
		if(page == null) {
			return new Integer(1);
		}
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		if(pageSize == null) {
			return new Integer(10);
		} else if(pageSize.intValue() > 100) {
			return new Integer(99);
		}
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getOffset() {
		Integer page  = getPage();
		Integer pageSize = getPageSize();
		Integer offset = (page - 1) * pageSize;
		return offset;
	}
	
	public Integer getLimit() {
		return getPageSize();
	}
	
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	public PagingEntity pagingEntity() {
		PagingEntity pagingEntity = new PagingEntity();
        BeanUtils.copyProperties(this, pagingEntity);
        return pagingEntity;
	}
}
