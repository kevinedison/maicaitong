package com.portal.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.portal.bean.IdRequest;
import com.portal.bean.PagingData;
import com.portal.bean.Response;
import com.portal.bean.ResponseData;
import com.portal.bean.dto.NeedAcceptInfo;
import com.portal.bean.dto.NeedInfo;
import com.portal.bean.dto.SimpUserInfo;
import com.portal.bean.entity.Need;
import com.portal.bean.entity.NeedAccept;
import com.portal.bean.request.NeedAcpConfirmRequest;
import com.portal.bean.request.NeedAcpSaveRequest;
import com.portal.bean.request.NeedSaveRequest;
import com.portal.bean.request.NeedSearchRequest;
import com.portal.bean.request.NeedUpdateRequest;
import com.portal.constant.BaseConstant;
import com.portal.constant.BusinessCode;
import com.portal.constant.StatusConstant;
import com.portal.manager.SessionManager;
import com.portal.manager.UserManager;
import com.portal.service.NeedService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NeedController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(NeedController.class);
    
    @Autowired
    NeedService needService;
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    UserManager userManager;
    
    @RequestMapping(value = "/need/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getAllNeed(@Valid NeedSearchRequest needSearchRequest)
    {
        LOGGER.info("query needlist start");
        ResponseData<PagingData<NeedInfo>> responseData = new ResponseData<PagingData<NeedInfo>>();
        
        Need need = needSearchRequest.toEntity();
        PagingData<Need> needData = needService.selectByIndex(need);
        
        List<Need> needList = needData.getData();
        List<NeedInfo> needInfoList = new ArrayList<NeedInfo>();
        
        if (!CollectionUtils.isEmpty(needList))
        {
            for (Need temp : needList)
            {
                NeedInfo needInfo = new NeedInfo(temp);
                SimpUserInfo sUserInfo = userManager.getSimpUserInfo(temp.getUserId(), Boolean.FALSE);
                needInfo.setUserInfo(sUserInfo);
                needInfoList.add(needInfo);
            }
        }
        
        PagingData<NeedInfo> pagingData = new PagingData<NeedInfo>();
        pagingData.setData(needInfoList);
        pagingData.setCount(needData.getCount());
        responseData.setData(pagingData);
        
        LOGGER.info("query needlist end");
        return responseData;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param id needID
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/need", method = RequestMethod.GET)
    @ResponseBody
    public Response getNeedDetail(@Valid IdRequest idRequest, HttpServletRequest request)
    {
        LOGGER.info("query need detail start");
        ResponseData<NeedInfo> responseData = new ResponseData<NeedInfo>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        String id = idRequest.getId();
        
        Need queryNeed = needService.selectById(id);
        if (null == queryNeed)
        {
            responseData.setCode(BusinessCode.NOT_EXIST.getCode());
            return responseData;
        }
        
        NeedInfo needInfo = new NeedInfo(queryNeed);
        needInfo.setUserInfo(userManager.getSimpUserInfo(needInfo.getUserId(), Boolean.FALSE));
        
        // 查询接单列表
        List<NeedAccept> needAcpList = needService.selectAcpListByNeedId(id);
        List<NeedAcceptInfo> needAcpInfoList = new ArrayList<NeedAcceptInfo>();
        if (!CollectionUtils.isEmpty(needAcpList))
        {
            for (NeedAccept temp : needAcpList)
            {
                if (temp.getAcpUser().equals(userId))
                {
                    needInfo.setAcpFlag(true);
                }
                NeedAcceptInfo acpInfo = new NeedAcceptInfo(temp);
                acpInfo.setAcpUserInfo(userManager.getSimpUserInfo(temp.getAcpUser(), Boolean.FALSE));
                acpInfo.setNeedUserInfo(userManager.getSimpUserInfo(temp.getNeedUser(), Boolean.FALSE));
                needAcpInfoList.add(acpInfo);
            }
        }
        needInfo.setNeedAcceptList(needAcpInfoList);
        
        responseData.setData(needInfo);
        LOGGER.info("query needdetail end");
        return responseData;
    }
    
    @RequestMapping(value = "/user/need/list", method = RequestMethod.GET)
    @ResponseBody
    public Response getUserNeed(HttpServletRequest request, @Valid NeedSearchRequest needSearchRequest)
    {
        LOGGER.info("query myneed begin");
        ResponseData<PagingData<NeedInfo>> responseData = new ResponseData<PagingData<NeedInfo>>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Need need = needSearchRequest.toEntity();
        need.setUserId(userId);
        PagingData<Need> needData = needService.selectByIndex(need);
        
        List<Need> needList = needData.getData();
        List<NeedInfo> needInfoList = new ArrayList<NeedInfo>();
        
        if (!CollectionUtils.isEmpty(needList))
        {
            for (Need temp : needList)
            {
                NeedInfo needInfo = new NeedInfo(temp);
                SimpUserInfo sUserInfo = userManager.getSimpUserInfo(temp.getUserId(), Boolean.FALSE);
                needInfo.setUserInfo(sUserInfo);
                needInfoList.add(needInfo);
            }
        }
        
        PagingData<NeedInfo> pagingData = new PagingData<NeedInfo>();
        pagingData.setData(needInfoList);
        pagingData.setCount(needData.getCount());
        responseData.setData(pagingData);
        
        LOGGER.info("query myneed end");
        return responseData;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @param NeedSaveRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/need", method = RequestMethod.POST)
    @ResponseBody
    public Response addNeed(HttpServletRequest request, @Valid @RequestBody NeedSaveRequest needSaveRequest)
    {
        LOGGER.info("add need begin");
        ResponseData<String> responseData = new ResponseData<String>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Need need = needSaveRequest.toEntity(userId);
        needService.save(need);
        responseData.setData(need.getId());
        LOGGER.info("add need end");
        return responseData;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @param NeedSaveRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/need", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteNeed(HttpServletRequest request, @Valid IdRequest idRequest)
    {
        LOGGER.info("delete need begin");
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Need deleteNeed = new Need();
        deleteNeed.setId(idRequest.getId());
        deleteNeed.setUserId(userId);
        needService.deleteById(deleteNeed);
        LOGGER.info("delete need end");
        return response;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @param needUpdateRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/need", method = RequestMethod.PUT)
    @ResponseBody
    public Response editNeed(HttpServletRequest request, @Valid @RequestBody NeedUpdateRequest needUpdateRequest)
    {
        LOGGER.info("edit need begin");
        ResponseData<String> responseData = new ResponseData<String>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Need need = needUpdateRequest.toEntity(userId);
        needService.updateById(need);
        
        responseData.setData(need.getId());
        
        LOGGER.info("edit need end");
        return responseData;
    }
    
    /**
     * <一句话功能简述> <功能详细描述> 接单
     * 
     * @param request
     * @param needAcpSaveRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/need/acp", method = RequestMethod.POST)
    @ResponseBody
    public Response acpNeed(HttpServletRequest request, @Valid @RequestBody NeedAcpSaveRequest needAcpSaveRequest)
    {
        LOGGER.info("accept need begin");
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        NeedAccept needAcp = needAcpSaveRequest.toEntity(userId);
        needAcp.setAcpUser(userId);
        
        Need queryNeed = needService.selectById(needAcp.getNeedId());
        if (null == queryNeed)
        {
            LOGGER.info("the need is not exist");
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        needAcp.setNeedUser(queryNeed.getUserId());
        
        if (userId.equals(queryNeed.getUserId()))
        {
            LOGGER.info("the user can not accept himself need");
            response.setCode(BusinessCode.CANNOT_ACCEPT_SELF.getCode());
            return response;
        }
        
        List<NeedAccept> needAcceptList = needService.selectAcpListByNeedId(needAcp.getNeedId());
        
        if (!CollectionUtils.isEmpty(needAcceptList))
        {
            if (needAcceptList.size() >= BaseConstant.NEED_ACP_MAX)
            {
                response.setCode(BusinessCode.ALREADY_REACH_MAX.getCode());
                return response;
            }
            else
            {
                for (NeedAccept temp : needAcceptList)
                {
                    if (temp.getAcpUser().equals(userId))
                    {
                        response.setCode(BusinessCode.ALREADY_ACCEPT_NEED.getCode());
                        return response;
                    }
                }
                // 接单
                needService.saveAcp(needAcp);
            }
        }
        else
        {
            needService.saveAcp(needAcp);
        }
        
        LOGGER.info("accept need end");
        return response;
    }
    
    /**
     * <一句话功能简述> <功能详细描述> 求购成交
     * 
     * @param request
     * @param needAcpSaveRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/user/need/confirm", method = RequestMethod.POST)
    @ResponseBody
    public Response confirmNeed(HttpServletRequest request,
        @Valid @RequestBody NeedAcpConfirmRequest needAcpConfirmRequest)
    {
        LOGGER.info("confirm accept need begin");
        ResponseData<String> response = new ResponseData<String>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        NeedAccept needAcp = needAcpConfirmRequest.toEntity(userId);
        
        // 判断need是否存在
        Need need = new Need();
        need.setUserId(userId);
        need.setId(needAcp.getNeedId());
        need.setStatus(StatusConstant.NEED_DEPLOY);
        Need queryNeed = needService.selectOne(need);
        if (null == queryNeed)
        {
            LOGGER.info("the need is not exist or need status is not correct");
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        // 查询当前接单ID是否正确
        needAcp.setStatus(StatusConstant.NEED_DEPLOY);
        NeedAccept queryNeedAcp = needService.selectAcpByAcpId(needAcp);
        
        if (null == queryNeedAcp)
        {
            LOGGER.info("there is no buyer accept the need");
            response.setCode(BusinessCode.NO_BUYER_ACCEPTNEED.getCode());
            return response;
        }
        
        String orderId = needService.confirmNeed(queryNeed, queryNeedAcp);
        response.setData(orderId);
        LOGGER.info("confirm accept need end");
        return response;
    }
}
