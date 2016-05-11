package com.portal.manager.rate;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Rate;
import com.portal.common.mapper.JsonMapper;
import com.portal.constant.StatusConstant;
import com.portal.manager.HttpClientManager;
import com.portal.service.RateService;

@Component
public class RateManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RateManager.class);
    
    private static List<RateCurrency> rateCache = new ArrayList<RateCurrency>();
    private static Long rateCacheTime = System.currentTimeMillis();
    private static Lock rateCacheLock = new ReentrantLock();
    
    @Autowired
    HttpClientManager httpClientManager;
    
    @Autowired
    RateService rateService;
    
    public List<RateCurrency> getRateList()
    {
        long time = System.currentTimeMillis() - rateCacheTime;
        if(CollectionUtils.isEmpty(rateCache) || (time/1000) >= 7200) {
            try {
                rateCacheLock.lock();
                
                Rate searchRate = new Rate();
                searchRate.setOffset(0);
                searchRate.setLimit(9999);
                searchRate.setStatus(StatusConstant.STATUS_ONLINE);
                PagingData<Rate> pageRate = rateService.selectByIndex(searchRate);
                if(pageRate != null && !CollectionUtils.isEmpty(pageRate.getData())) {
                    List<RateCurrency> rates = new ArrayList<RateCurrency>();
                    RateCurrency temp = null;
                    for(Rate r : pageRate.getData()) {
                        temp =  exchange(r.getCode(), "CNY", new Double(1));
                        if(temp != null) {
                            temp.setFromCurrencyName(r.getName());
                            rates.add(temp);
                        }
                    }
                    rateCache.clear();
                    rateCache.addAll(rates);
                    rateCacheTime = System.currentTimeMillis();
                }
            } finally {
                rateCacheLock.unlock();
            }   
        }      
        LOGGER.info("get ticket token from cache..");
        return rateCache;
    } 
    
    public void refresh()
    {
        try {
            rateCacheLock.lock();
            
            Rate searchRate = new Rate();
            searchRate.setOffset(0);
            searchRate.setLimit(9999);
            searchRate.setStatus(StatusConstant.STATUS_ONLINE);
            PagingData<Rate> pageRate = rateService.selectByIndex(searchRate);
            if(pageRate != null && !CollectionUtils.isEmpty(pageRate.getData())) {
                List<RateCurrency> rates = new ArrayList<RateCurrency>();
                RateCurrency temp = null;
                for(Rate r : pageRate.getData()) {
                    temp =  exchange(r.getCode(), "CNY", new Double(1));
                    if(temp != null) {
                        temp.setFromCurrencyName(r.getName());
                        rates.add(temp);
                    }
                }
                rateCache.clear();
                rateCache.addAll(rates);
                rateCacheTime = System.currentTimeMillis();
            }
        } finally {
            rateCacheLock.unlock();
        }    
    } 
    
    public RateCurrency exchange(String from,String to, Double amount)
    {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("fromCurrency", from));
        params.add(new BasicNameValuePair("toCurrency", to));
        params.add(new BasicNameValuePair("amount", String.valueOf(amount)));

        String param = URLEncodedUtils.format(params, "UTF-8");
        String url = "http://apis.baidu.com/apistore/currencyservice/currency" + "?" + param;
        
        LOGGER.info("exchange url  {} ..", url);
        try
        {
            HttpGet httpGet = new HttpGet();
            httpGet.setHeader("apikey", "fe508d37a54261c552e5ad5b88c9cf44");
            httpGet.setURI(new URI(url));
            String resJsonStr = httpClientManager.executeAndGet(httpGet);
            
            LOGGER.info("exchange  response {} ..", resJsonStr);
            RateResult response = new JsonMapper().fromJson(resJsonStr,RateResult.class);
            if(response.getErrNum().equals("0")) {
                return response.getRetData();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}