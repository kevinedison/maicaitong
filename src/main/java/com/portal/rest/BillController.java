package com.portal.rest;

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

import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.entity.Bill;
import com.portal.bean.request.BillSearchRequest;
import com.portal.manager.SessionManager;
import com.portal.service.BillService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class BillController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(BillController.class);
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    BillService billService;
    
    /**
     * <一句话功能简述>订购服务 <功能详细描述>
     * 
     * @param request
     * @param orderSaveRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/bill", method = RequestMethod.GET)
    @ResponseBody
    public Response getUserBill(HttpServletRequest request, @Valid BillSearchRequest billSearchRequest)
    {
        ResponseData<PagingData<Bill>> response = new ResponseData<>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Bill bill = billSearchRequest.toEntity(userId);
        
        response.setData(billService.selectByIndex(bill));
        return response;
    }
}
