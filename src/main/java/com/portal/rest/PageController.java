package com.portal.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.portal.common.mapper.JsonMapper;
import com.portal.manager.SignatureManager;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class PageController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);
    
    @Autowired
    SignatureManager signatureManager;
    
	@RequestMapping(value="/w/{pageName}", method=RequestMethod.GET)  
    public String w(@PathVariable String pageName) {	
		
		//添加csrf token
        return "/w/"+pageName;
    }
	@RequestMapping(value="/wx/{pageName}", method=RequestMethod.GET)  
    public String wx(HttpServletRequest request,@PathVariable String pageName,Model model) {	
	    String pageUrl = "http://weixin.acarry.com" + request.getRequestURI();
	    if(StringUtils.isNotEmpty(request.getQueryString())) {
	        pageUrl = pageUrl + "?" + request.getQueryString();
	    }
	    LOGGER.debug("singure url :" + pageUrl);
	    
        Map<String,String> map = signatureManager.signature(pageUrl,false);
        String signature = new JsonMapper().toJson(map);
	    model.addAttribute("signature", signature);
	    LOGGER.debug("signature :" + signature);
        return "/wx/"+pageName;
    }
	@RequestMapping(value="/m/{pageName}", method=RequestMethod.GET)  
    public String m(@PathVariable String pageName) {	
		
		//添加csrf token
        return "/m/"+pageName;
    }
	
	@RequestMapping(value="/console/{pageName}", method=RequestMethod.GET)  
    public String console(@PathVariable String pageName) {    
        
        //添加csrf token
        return "/console/"+pageName;
    }
	
	@RequestMapping(value="/error/{errorPage}", method=RequestMethod.GET)  
    public String errorPage(@PathVariable String errorPage) {	
		
        return "/error/" + errorPage;
    }
}
