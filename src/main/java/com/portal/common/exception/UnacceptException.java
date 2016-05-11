package com.portal.common.exception;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import com.portal.common.context.BaseContext;
import com.portal.constant.BusinessCode;

public class UnacceptException extends RuntimeException {

	private static final long serialVersionUID = 6498303276342391310L;

	private String code;
	private String msg;
	private String desc;

	public UnacceptException() {

	}

	public UnacceptException(Throwable cause, BusinessCode error) {
		super(cause);
		this.code = error.getCode();
		this.msg = BaseContext.getMessage(error.getCode());
	}

	public UnacceptException(Throwable cause, BusinessCode error, String desc) {
		super(cause);
		this.code = error.getCode();
		String errorMessage = BaseContext.getMessage(error.getCode());
		if (StringUtils.isEmpty(errorMessage)) {
			this.msg = desc;
		}
		this.desc = desc;
	}

	public UnacceptException(Throwable cause, BusinessCode error, String[] infos) {
		super(cause);
		this.code = error.getCode();
		String errorMsg = BaseContext.getMessage(error.getCode());
		if (StringUtils.isNotEmpty(errorMsg)) {
			this.msg = MessageFormat.format(errorMsg, infos);
		}
	}

	public UnacceptException(Throwable cause, BusinessCode error, String[] infos, String desc) {
		super(cause);
		this.code = error.getCode();
		String errorMsg = BaseContext.getMessage(error.getCode());
		if (StringUtils.isNotEmpty(errorMsg)) {
			this.msg = MessageFormat.format(errorMsg, infos);
		}

		if (StringUtils.isEmpty(this.msg)) {
			this.msg = desc;
		}
		this.desc = desc;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
