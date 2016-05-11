package com.portal.rest.console;

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
import com.portal.bean.RequestSearch;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.entity.Feedback;
import com.portal.manager.AdminSessionManager;
import com.portal.service.FeedbackService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ConsoleFeedbackController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleFeedbackController.class);
    
    @Autowired
    AdminSessionManager adminSessionManager;
    
    @Autowired
    private FeedbackService feedbackService;
    
    @RequestMapping(value = "/console/feedback/list", method = RequestMethod.GET)
    @ResponseBody
    public Response listFeedback(HttpServletRequest request, @Valid RequestSearch requestSearch)
    {
        ResponseData<PagingData<Feedback>> response = new  ResponseData<PagingData<Feedback>>();
        Feedback searchFeedback = new Feedback();
        searchFeedback.setOffset(requestSearch.getOffset());
        searchFeedback.setLimit(requestSearch.getLimit());        
        response.setData(feedbackService.selectByIndex(searchFeedback));
        return response;
    }
}
