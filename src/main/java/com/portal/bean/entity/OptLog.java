package com.portal.bean.entity;

import com.portal.bean.entity.base.PagingEntity;

public class OptLog extends PagingEntity {
	private String account;
	private String optIp;
	private String optContent;
	private String optResult;
	private Long costTime;

	public String getOptContent() {
		return optContent;
	}
	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}
	public Long getCostTime() {
		return costTime;
	}
	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}
	
	public String getOptIp() {
		return optIp;
	}
	public void setOptIp(String optIp) {
		this.optIp = optIp;
	}
	
	public String getOptResult() {
		return optResult;
	}
	public void setOptResult(String optResult) {
		this.optResult = optResult;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}  
}
