package com.portal.common.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.common.mapper.JsonMapper;
import com.portal.manager.SessionManager;

@Component
public class CommonFilter implements Filter
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonFilter.class);
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    UserSessionFilter userSessionFilter;
    
    @Autowired
    AdminSessionFilter adminSessionFilter;
    
    static FilterConfig filterConfig;
    
    private JsonMapper mapper = new JsonMapper();
    
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
        
        long startTime = System.currentTimeMillis();
        MDC.put("startTime", String.valueOf(startTime));
        MDC.put("logId", RandomStringUtils.randomAlphanumeric(4) + String.valueOf(System.nanoTime()));
        
        String requestURL = request.getRequestURI();
        
        boolean isExclude = isExcludeUrl(requestURL);
        try
        {
            if (!isExclude)
            {
                LOGGER.info("base filter, request url {},method {}", request.getRequestURI(), request.getMethod());
                
                HashMap paramMap = new HashMap(request.getParameterMap());
                
                LOGGER.info("http request parameter :" + mapper.toJson(paramMap));
                
                Boolean isAjaxRequest = true;
                String requestType = request.getHeader("X-Requested-With");
                if (requestType == null || !requestType.equals("XMLHttpRequest"))
                {
                    isAjaxRequest = false;
                }
                MDC.put("isAjaxRequest", String.valueOf(isAjaxRequest));
                
                if (requestURL.indexOf("/console") >= 0)
                {
                    LOGGER.info("console filter...");
                    adminSessionFilter.doFilter(request, response, filterChain);
                    return;
                }
                LOGGER.info("session filter...");
                userSessionFilter.doFilter(request, response, filterChain);
                return;
            }
            else
            {
                LOGGER.debug("base filter, request url is in exclude url {}", requestURL);
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            LOGGER.error("common filter error", e);
        }
        finally
        {
            if (!isExclude)
            {
                long costTime = (System.currentTimeMillis() - startTime) / 1000;
                LOGGER.info("base filter, request url {} ,logId {} ,costTime {}s",
                    request.getRequestURI(),
                    MDC.get("logId"),
                    costTime);
            }
            MDC.clear();
        }
    }
    
    private boolean isExcludeUrl(String url)
    {
        String[] excludeUrls =
            new String[] {".js", ".jpg", ".gif", ".png", ".ico", ".css", ".json", ".eot", ".svg", ".woff", ".ttf",
                ".map", ".html", ".php"};
        for (String e : excludeUrls)
        {
            if (url.endsWith(e))
            {
                return true;
            }
        }
        
        if (url.contains("/pay/result") || url.contains("/profile") || url.contains("/comment/list")
            || url.contains("/service/list"))
        {
            return true;
        }
        
        return false;
    }
}
