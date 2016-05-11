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
import com.portal.bean.dto.FavoriteInfo;
import com.portal.bean.dto.ServiceInfo;
import com.portal.bean.entity.Favorite;
import com.portal.bean.entity.UserService;
import com.portal.bean.request.FavoriteSaveRequest;
import com.portal.bean.request.FavoriteSearchRequest;
import com.portal.constant.BaseConstant;
import com.portal.constant.BusinessCode;
import com.portal.manager.SessionManager;
import com.portal.manager.UserManager;
import com.portal.service.FavoriteService;
import com.portal.service.UserServService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class FavoriteController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteController.class);
    
    @Autowired
    FavoriteService favoriteService;
    
    @Autowired
    UserServService userServService;
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    SessionManager sessionManager;
    
    @RequestMapping(value = "/user/favorite", method = RequestMethod.POST)
    @ResponseBody
    public Response saveUserFavorite(HttpServletRequest request,@Valid @RequestBody FavoriteSaveRequest favoriteSaveRequest)
    {
        
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Favorite entity = favoriteSaveRequest.toEntity(userId);
        entity.setUserId(userId);
        entity.setTargetType(BaseConstant.FAVORITE_TYPE_SERVICE);
        
        UserService userService = userServService.selectById(entity.getTargetId());
        
        if (null == userService)
        {
            response.setCode(BusinessCode.NOT_EXIST.getCode());
            return response;
        }
        
        if (userId.equals(userService.getUserId()))
        {
            response.setCode(BusinessCode.CANNOT_FAVORITE_SELF.getCode());
            return response;
        }
        
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setTargetId(favoriteSaveRequest.getTargetId());
        // 目前只有收藏服务
        favorite.setTargetType(BaseConstant.FAVORITE_TYPE_SERVICE);
        
        Favorite dbFavorite = favoriteService.selectOne(favorite);
        if (dbFavorite == null)
        {
            favoriteService.save(entity);
        }
        else
        {
            response.setCode(BusinessCode.ALREADY_FAVORITE.getCode());
            return response;
        }
        
        return response;
    }
    
    @RequestMapping(value = "/user/favorite", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteUserFavorite(HttpServletRequest request,@Valid IdRequest idRequest)
    {
        
        Response response = new Response();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Favorite deleteFavorite = new Favorite();
        deleteFavorite.setId(idRequest.getId());
        deleteFavorite.setUserId(userId);
        
        favoriteService.deleteById(deleteFavorite);
        return response;
    }
    
    @RequestMapping(value = "/user/favorite/check", method = RequestMethod.GET)
    @ResponseBody
    public Response checkUserFavorite(HttpServletRequest request,@Valid FavoriteSaveRequest favoriteSaveRequest)
    {
        
        ResponseData<Boolean> response = new ResponseData<Boolean>();
        
        String userId = sessionManager.getSessionUserId(request);
        
        Favorite entity = favoriteSaveRequest.toEntity(userId);
        entity.setUserId(userId);
        
        Favorite dbFavorite = favoriteService.selectOne(entity);
        if (dbFavorite == null)
        {
            response.setData(false);
        }
        else
        {
            response.setData(true);
        }
        return response;
    }
    
    @RequestMapping(value = "/favorite/list", method = RequestMethod.GET)
    @ResponseBody
    public Response listFavorite(@Valid FavoriteSearchRequest favoriteSearchRequest)
    {
        Favorite favorite = favoriteSearchRequest.toEntity();
        return getFavoriteList(favorite);
    }
    
    @RequestMapping(value = "/user/favorite/list", method = RequestMethod.GET)
    @ResponseBody
    public Response listUserFavorite(HttpServletRequest request,@Valid FavoriteSearchRequest favoriteSearchRequest)
    {
        
        String userId = sessionManager.getSessionUserId(request);
        
        Favorite favorite = favoriteSearchRequest.toEntity();
        favorite.setUserId(userId);
        
        return getFavoriteList(favorite);
    }
    
    private ResponseData<PagingData<FavoriteInfo<?>>> getFavoriteList(Favorite favorite)
    {
        ResponseData<PagingData<FavoriteInfo<?>>> response = new ResponseData<PagingData<FavoriteInfo<?>>>();
        PagingData<Favorite> pagingFavorite = favoriteService.selectByIndex(favorite);
        if (null != pagingFavorite && !CollectionUtils.isEmpty(pagingFavorite.getData()))
        {
            PagingData<FavoriteInfo<?>> pagingFavoriteInfo = new PagingData<FavoriteInfo<?>>();
            pagingFavoriteInfo.setCount(pagingFavorite.getCount());
            
            List<FavoriteInfo<?>> favoriteInfoList = new ArrayList<FavoriteInfo<?>>();
            for (Favorite f : pagingFavorite.getData())
            {
                if (f.getTargetType().equals(BaseConstant.FAVORITE_TYPE_SERVICE))
                {
                    FavoriteInfo<ServiceInfo> favoriteInfo = new FavoriteInfo<ServiceInfo>(f);
                    UserService userService = userServService.selectById(f.getTargetId());
                    if (userService != null)
                    {
                        ServiceInfo serviceInfo = new ServiceInfo(userService);
                        serviceInfo.setUserInfo(userManager.getSimpUserInfo(userService.getUserId(), Boolean.FALSE));
                        favoriteInfo.setContent(serviceInfo);
                        favoriteInfo.setSimpUser(userManager.getSimpUserInfo(f.getUserId(), Boolean.FALSE));
                        favoriteInfoList.add(favoriteInfo);
                    }
                }
            }
            
            LOGGER.info("query session user {} favorite type {} size {} ...",
                favorite.getUserId(),
                BaseConstant.FAVORITE_TYPE_SERVICE,
                favoriteInfoList.size());
            pagingFavoriteInfo.setData(favoriteInfoList);
            pagingFavoriteInfo.setCount(pagingFavorite.getCount());
            response.setData(pagingFavoriteInfo);
        }
        return response;
    }
}
