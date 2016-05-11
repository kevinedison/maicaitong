package com.portal.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.RequestSearch;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.request.RateExchangeRequest;
import com.portal.manager.rate.RateCurrency;
import com.portal.manager.rate.RateManager;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RateController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RateController.class);
    
    @Autowired
    private RateManager rateManager;
    
    
    @RequestMapping(value = "/rate", method = RequestMethod.GET)
    @ResponseBody
    public Response getExchange(HttpServletRequest request, @Valid RateExchangeRequest exchange)
    {
        ResponseData<RateCurrency> response = new ResponseData<RateCurrency>();
        RateCurrency rateCurrency = rateManager.exchange(exchange.getFrom(), exchange.getTo(), exchange.getAmount());
        response.setData(rateCurrency);
        
        return response;
    }
    
    @RequestMapping(value = "/rate/list", method = RequestMethod.GET)
    @ResponseBody
    public Response rateList(HttpServletRequest request,RequestSearch requestSearch)
    {
        ResponseData<List<RateCurrency>> response = new ResponseData<List<RateCurrency>>();
        response.setData(rateManager.getRateList());
        return response;
    }
}
