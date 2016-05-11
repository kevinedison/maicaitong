package com.portal.manager;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.common.exception.BusinessException;
import com.portal.common.mapper.JsonMapper;
import com.portal.common.util.Sign;

@Component
public class SignatureManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureManager.class);
    
    
    @Autowired
    HttpClientManager httpClientManager;
    
    public static String ticketCache = "";
    private static Long ticketTime = System.currentTimeMillis();  
    private static Lock ticketLock = new ReentrantLock();
    
    public static String accessTokenCache = "";
    private static Long accessTokenTime = System.currentTimeMillis();  
    private static Lock accessTokenLock = new ReentrantLock();
    
    public Map<String,String> signature(String url,Boolean refresh) {
        long time = System.currentTimeMillis() - ticketTime;
        if(StringUtils.isEmpty(ticketCache) || (time/1000) >= 1000 || refresh) {
            try {
                ticketLock.lock();
                String accessToken = getAccessToken(refresh);
                if(StringUtils.isEmpty(accessToken)) {
                    LOGGER.error("get access token error");
                    throw new BusinessException();
                }
                
                String ticket =  loadTicketToken(accessToken);          
                if(StringUtils.isEmpty(ticket)) {
                    LOGGER.error("get ticket token error");
                    throw new BusinessException();
                }
                
                ticketCache = ticket;
                ticketTime = System.currentTimeMillis();  
            } finally {
                ticketLock.unlock();
            }   
        }      
        LOGGER.info("get ticket token from cache..");
        return Sign.sign(ticketCache, url);
    }

    public String getAccessToken(Boolean refresh) {
        long time = System.currentTimeMillis() - accessTokenTime;
        if(StringUtils.isEmpty(accessTokenCache) || (time/1000) >= 1000 || refresh) {
            try {
                accessTokenLock.lock();
                String accessToken = loadAccessToken();
                if(StringUtils.isEmpty(accessToken)) {
                    LOGGER.error("load access token error");
                    throw new BusinessException();
                }
                
                accessTokenCache = accessToken;
                accessTokenTime = System.currentTimeMillis();  
            } finally {
                accessTokenLock.unlock();
            }   
        }      
        LOGGER.info("get access token from cache..");
        return accessTokenCache;
    }
    
    public String loadAccessToken()
    {
        String accessUrl =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxe8626eeb45600bbc&secret=e8c2ed793c14a5740c0fcd781ceb0fb0";
        LOGGER.info("access token url  {} ..", accessUrl);
        
        try
        {
            HttpGet httpGet = new HttpGet();
            
            httpGet.setURI(new URI(accessUrl));
            String resJsonStr = httpClientManager.executeAndGet(httpGet);
            
            LOGGER.info("access token  response {} ..", resJsonStr);
            
            JsonMapper jsonMapper = new JsonMapper();
            
            Map<String, String> accessResponse =
                jsonMapper.fromJson(resJsonStr, jsonMapper.contructMapType(Map.class, String.class, String.class));
            if (StringUtils.isEmpty(accessResponse.get("errcode")))
            {
                return accessResponse.get("access_token");
            }
            LOGGER.info("access token  response error . {} ..", accessResponse.get("errcode"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        throw new BusinessException();
    }
    
    public String loadTicketToken(String accessToken)
    {
        String tickettUrl =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        LOGGER.info("ticket token url  {} ..", tickettUrl);
        
        try
        {
            HttpGet httpGet = new HttpGet();
            
            httpGet.setURI(new URI(tickettUrl));
            String resJsonStr = httpClientManager.executeAndGet(httpGet);
            
            LOGGER.info("ticket token  response {} ..", resJsonStr);
            
            JsonMapper jsonMapper = new JsonMapper();
            
            Map<String, String> accessResponse =
                jsonMapper.fromJson(resJsonStr, jsonMapper.contructMapType(Map.class, String.class, String.class));
            if (StringUtils.isNotEmpty(accessResponse.get("errcode")) && accessResponse.get("errcode").equals("0"))
            {
                return accessResponse.get("ticket");
            }
            LOGGER.info("ticket token response error . {} ..", accessResponse.get("errcode"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        throw new BusinessException();
    }
}