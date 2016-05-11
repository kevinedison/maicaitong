package com.portal.rest.console;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.IdRequest;
import com.portal.bean.PagingData;
import com.portal.bean.RequestSearch;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.entity.Rate;
import com.portal.bean.request.RateSaveRequest;
import com.portal.bean.request.RateUpdateRequest;
import com.portal.constant.StatusConstant;
import com.portal.manager.AdminSessionManager;
import com.portal.manager.rate.RateManager;
import com.portal.service.RateService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ConsoleRateController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleRateController.class);
    
    @Autowired
    AdminSessionManager adminSessionManager;
    
    @Autowired
    private RateManager rateManager;
    
    @Autowired
    private RateService rateService;
    
    @RequestMapping(value = "/console/rate/list", method = RequestMethod.GET)
    @ResponseBody
    public Response listRate(HttpServletRequest request, @Valid RequestSearch requestSearch)
    {
        ResponseData<PagingData<Rate>> response = new  ResponseData<PagingData<Rate>>();
        Rate searchRate = new Rate();
        searchRate.setOffset(0);
        searchRate.setLimit(9999);
        searchRate.setStatus(StatusConstant.STATUS_ONLINE);
        
        response.setData(rateService.selectByIndex(searchRate));
        return response;
    }
    
    @RequestMapping(value = "/console/rate", method = RequestMethod.POST)
    @ResponseBody
    public Response saveRate(HttpServletRequest request, @Valid @RequestBody RateSaveRequest rateSaveRequest)
    {
        ResponseData<String> response = new ResponseData<String>();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        Rate rate = rateSaveRequest.toEntity(admin);
        rateService.save(rate);
        
        response.setData(rate.getId());
        return response;
    }
    
    @RequestMapping(value = "/console/rate", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteRate(HttpServletRequest request, IdRequest idRequest)
    {
        Response response = new Response();
        String admin = adminSessionManager.getSessionAdminId(request);
        
        Rate deleteRate = new Rate();
        deleteRate.setId(idRequest.getId());
        rateService.deleteById(deleteRate);
        return response;
    }
    
    @RequestMapping(value = "/console/rate", method = RequestMethod.PUT)
    @ResponseBody
    public Response updateRate(HttpServletRequest request, @Valid @RequestBody RateUpdateRequest rateUpdateRequest)
    {
        Response response = new Response();
        
        String admin = adminSessionManager.getSessionAdminId(request);
        Rate rate = rateUpdateRequest.toEntity(admin);
        rateService.updateById(rate);

        return response;
    }
    
    @RequestMapping(value = "/console/rate/refresh", method = RequestMethod.GET)
    @ResponseBody
    public Response refreshRate(HttpServletRequest request)
    {
        rateManager.refresh();
        return new Response();
    }
}
