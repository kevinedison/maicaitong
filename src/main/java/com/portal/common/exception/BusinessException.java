package com.portal.common.exception;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.portal.common.context.BaseContext;
import com.portal.constant.BusinessCode;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6498303276342391310L;

	private String code;
	private String msg;

	public BusinessException() {

	}

	public BusinessException(BusinessCode error) {
		this.code = error.getCode();
		this.msg = BaseContext.getMessage(error.getCode());
	}

	/**
	 * 直接返回依赖系统错误信息
	 **/
	public BusinessException(BusinessCode error, String errorMsg) {
		this.code = error.getCode();
		if (StringUtils.isEmpty(errorMsg)) {
			this.msg = BaseContext.getMessage(error.getCode());
		} else {
			this.msg = errorMsg;
		}
	}

	public BusinessException(BusinessCode error, String[] infos) {
		this.code = error.getCode();
		String message = BaseContext.getMessage(this.code);
		if (StringUtils.isNotEmpty(message)) {
			this.msg = MessageFormat.format(message, infos);
		}
	}

	// 转移到UnacceptException
	public BusinessException(Throwable cause, BusinessCode error) {
		super(cause);
		this.code = error.getCode();
		this.msg = BaseContext.getMessage(error.getCode());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
