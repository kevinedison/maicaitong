package com.portal.bean.request;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.portal.bean.Request;

public class FileUploadRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = -404071043021358806L;

	private CommonsMultipartFile[] file;

	public CommonsMultipartFile[] getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile[] file) {
		this.file = file;
	}
}
