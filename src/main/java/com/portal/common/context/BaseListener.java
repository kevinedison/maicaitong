package com.portal.common.context;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseListener.class);
	
    @Override
    public void contextInitialized(ServletContextEvent event) { 
    	long startTime  = System.currentTimeMillis();
    	LOGGER.info("database service  context initialize begin {}",startTime);
    	
    	WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        BaseContext.setBaseContext(ctx);
        
        String path = event.getServletContext().getRealPath("/"); 
        BaseContext.setContentPath(path);
        LOGGER.info("database service  context initialize success, cost time {}",System.currentTimeMillis() - startTime);
    }

    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
    }
}
