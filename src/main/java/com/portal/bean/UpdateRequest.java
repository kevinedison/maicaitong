package com.portal.bean;

public class UpdateRequest extends IdRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -404071043021358806L;
	
	private String remark;
	
	private Integer status;
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
