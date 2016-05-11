package com.portal.manager;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.portal.bean.dto.AdminSessionInfo;
import com.portal.common.util.ThreadLocals;

/***
 * 用户session管理
 * 
 * **/
@Component
public class AdminSessionManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminSessionManager.class);
	public static final String SESSION_USER_INFO_KEY = "session-admin-info";
	
	
	/***
	 * session
	 * **/
	public boolean checkAdminSession(HttpServletRequest request) {
		if(request == null || request.getSession() == null) {
			LOGGER.warn("the session is null.");
			return false;
		}
		
		AdminSessionInfo adminSessionInfo = (AdminSessionInfo)request.getSession().getAttribute(SESSION_USER_INFO_KEY);
		if(adminSessionInfo == null || StringUtils.isEmpty(adminSessionInfo.getAccount())) {
			LOGGER.warn("get from session, but is null..");
			return false;
		}
		LOGGER.info("get account from session, account {} ..",adminSessionInfo.getAccount());
		ThreadLocals.put(SESSION_USER_INFO_KEY, adminSessionInfo);
        return true;
	}
	
	/***
	 * session
	 * **/
	public AdminSessionInfo getAdminSession(HttpServletRequest request) {
		if(request == null || request.getSession() == null) {
			LOGGER.warn("the session is null.");
			return null;
		}
		
		AdminSessionInfo adminSessionInfo = (AdminSessionInfo)request.getSession().getAttribute(SESSION_USER_INFO_KEY);
        return adminSessionInfo;
	}
	
	/***
	 * session
	 * **/
	public String getSessionAdminId(HttpServletRequest request) {		
		AdminSessionInfo adminSessionInfo = (AdminSessionInfo)request.getSession().getAttribute(SESSION_USER_INFO_KEY);
        return adminSessionInfo.getAccount();
	}
	
	/***
	 * session
	 * **/
	public boolean refreshAdminSession(HttpServletRequest request,AdminSessionInfo adminSessionInfo) {
		if(request == null || request.getSession() == null) {
			LOGGER.warn("the session is null.");
			return false;
		}
		
		request.getSession().setAttribute(SESSION_USER_INFO_KEY,adminSessionInfo);
        return true;
	}
}
