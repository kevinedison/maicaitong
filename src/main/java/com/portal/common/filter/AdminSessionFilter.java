package com.portal.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.bean.Response;
import com.portal.common.util.Responses;
import com.portal.constant.BusinessCode;
import com.portal.manager.AdminSessionManager;

@Component
public class AdminSessionFilter implements Filter
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonFilter.class);
    
    @Autowired
    AdminSessionManager adminSessionManager;
    
    static FilterConfig filterConfig;
    
    @Override
    public void init(FilterConfig _filterConfig)
        throws ServletException
    {
        filterConfig = _filterConfig;
    }
    
    @Override
    public void destroy()
    {
        
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        
        try
        {
            String requestURL = request.getRequestURI();
            if (!adminSessionManager.checkAdminSession(request))
            {
                boolean isAjaxRequest = Boolean.valueOf(MDC.get("isAjaxRequest"));
                if (isAjaxRequest)
                {
                    Response resp = new Response();
                    resp.setCode(BusinessCode.SESSION_TIMEOUT.getCode());
                    Responses.response(response, new ObjectMapper().writeValueAsString(resp));
                    return;
                }
                
                if (requestURL.equals("/console/login") || requestURL.equals("/console/account/login"))
                {
                    LOGGER.info("filter to login page..");
                    
                }
                else
                {
                    String loginUrl = "/console/login";
                    response.sendRedirect(loginUrl);
                    return;
                }
            }
            else if (requestURL.equals("/console"))
            {
                String index = "/console/index";
                response.sendRedirect(index);
                return;
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            LOGGER.error("admin filter error", e);
        }
    }
}
