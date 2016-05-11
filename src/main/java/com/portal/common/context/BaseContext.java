package com.portal.common.context;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;

public class BaseContext {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseContext.class);
	
    private static ApplicationContext baseContext;
    
    private static String contentPath;
    
    public static void setBaseContext(ApplicationContext applicationContext) {
    	baseContext = applicationContext;
    }
    
    public static void setContentPath(String path) {
    	contentPath = path;
    }
    
    
    /**
     * 从spring content 获取bean 实例
     * */
    public static Object getBean(String name){
       if(StringUtils.isEmpty(name)){
    	  LOGGER.warn("bean name is empty..");
    	  return null;
       }
       try {
    	  return baseContext.getBean(name);
	   } catch (BeansException e) {
		  LOGGER.warn("Get bean from baseContent by bean name {} ,but not exist..",name);
		  return null;
	   }
    }
    
    /**
     * 从spring content 获取bean 实例
     * */
    public static <T> T getBean(String name,Class<T> type) {
        if(StringUtils.isEmpty(name)){
           LOGGER.warn("bean name is empty..");
           return null;
         }
         try {
      	   return baseContext.getBean(name,type);
  	   } catch (BeansException e) {
  		   LOGGER.warn("Get bean from baseContent by bean name {} ,but not exist..",name);
  		   return null;
  	   }
    }
    
    /**
     * 从spring content 获取bean 实例
     * */
    public static String getContentPath(){
       return contentPath;
    }
    
    
    public static String getMessage(String key) {
    	return getBaseMessage(key, new String[]{},getRequestLocale());
    }
    public static String getMessage(String key,Locale locale) {
    	return getBaseMessage(key,new String[]{},locale);
    }
    
    public static String getMessage(String msgKey,String[] values) {
    	return getBaseMessage(msgKey, values ,getRequestLocale());
    }
    
    /**
     * 从spring message 获取中value
     * */
    public static String getBaseMessage(String key,String[] values,Locale locale) {
    	if(StringUtils.isEmpty(key)){
    		LOGGER.warn("Get message from messageResource by key ,but key is empty..");
    		return key;
    	}
    	try {
    		return baseContext.getMessage(key, values ,locale);
		} catch (NoSuchMessageException e) {
			LOGGER.warn("Get message from messageResource by key {} ,but not exist..",key);
			return key;
		}
    }
    
    /**
     * 获取请求语言 用cookie维持会话语言
     * */
    public static Locale getRequestLocale(){
    	Locale locale = Locale.getDefault();
    	//get locale from request
    	String localeName = MDC.get("localeName");
    	if(!StringUtils.isEmpty(localeName)){
    		LOGGER.debug("Get message from messageResource by locale {} ..", localeName);
    		locale = org.springframework.util.StringUtils.parseLocaleString(localeName);
    	}
    	//get jvm default locale
    	LOGGER.debug("request locale {}",locale.getLanguage() + "_" + locale.getCountry());
    	return locale;
    }
}
