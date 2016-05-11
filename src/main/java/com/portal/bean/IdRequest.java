package com.portal.bean;

import javax.validation.constraints.NotNull;

public class IdRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = -404071043021358806L;
	
	@NotNull
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
