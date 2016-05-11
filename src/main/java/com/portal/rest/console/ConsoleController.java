package com.portal.rest.console;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.Response;
import com.portal.bean.dto.AdminSessionInfo;
import com.portal.constant.BusinessCode;
import com.portal.manager.AdminSessionManager;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ConsoleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleController.class);	
	
    @Autowired
	AdminSessionManager adminSessionManager;
    	
	@RequestMapping(value="/console/account/login", method=RequestMethod.POST)  
    public String accountLogin(HttpServletRequest request,String account,String password,Model model) {	
		Response response = new Response();
		
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
			
			LOGGER.warn("login fail, account {} or password {} is null",account,password);
			response.setCode(BusinessCode.FAIL.getCode());
			response.setMsg("account or password is null");
			
			model.addAttribute(response);
			return "redirect:/w/login";
		}
		
		if(!account.equals("admin") || !password.equals("admin")) {
		    return "redirect:/console/login";
		}
		
		AdminSessionInfo adminSessionInfo = new AdminSessionInfo();
		adminSessionInfo.setId(account);
		adminSessionInfo.setAccount(account);
		adminSessionManager.refreshAdminSession(request, adminSessionInfo);	
		LOGGER.info("account {} login success..");
		return "redirect:/console/index";
    }
	
	@RequestMapping(value="/console/account/logout", method=RequestMethod.GET)  
    public String logout(HttpServletRequest request) {	
		if(null != request) {
			if(null != request.getSession()) {
				request.getSession().invalidate();
			}
		}	
        return "redirect:/console/login";
	}
}
