package com.portal.bean;


/**
 * @author: hongjiao.hj
 * @Date: 2014-09-04
 * @Time: 下午3:44
 */
public class ResponseData<T> extends Response {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4099434800883579488L;
	
	private T data;
	

    public ResponseData(T data) {
		super();
		this.data = data;
	}

    public ResponseData() {
		super();
	}


	public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    } 
}
