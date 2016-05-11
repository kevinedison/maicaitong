package com.portal.common.filter;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.bean.Response;
import com.portal.bean.dto.UserInfo;
import com.portal.bean.dto.WxUserInfo;
import com.portal.bean.entity.LogInfo;
import com.portal.bean.entity.User;
import com.portal.common.exception.BusinessException;
import com.portal.common.log.LogCache;
import com.portal.common.mapper.JsonMapper;
import com.portal.common.util.Encodes;
import com.portal.common.util.IPUtil;
import com.portal.common.util.Responses;
import com.portal.constant.BusinessCode;
import com.portal.manager.HttpClientManager;
import com.portal.manager.SessionManager;
import com.portal.manager.UserManager;
import com.portal.service.UsersService;

@Component
public class UserSessionFilter implements Filter
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionFilter.class);
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    HttpClientManager httpClientManager;
    
    @Autowired
    UserManager userManager;
    
    @Autowired
    UsersService userService;
    
    static FilterConfig filterConfig;
    
    private JsonMapper mapper = new JsonMapper();
    
    @Override
    public void init(FilterConfig _filterConfig)
        throws ServletException
    {
        filterConfig = _filterConfig;
    }
    
    @Override
    public void destroy()
    {
        
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        boolean isAjaxRequest = Boolean.valueOf(MDC.get("isAjaxRequest"));
        try
        {
            String requestURL = request.getRequestURI();
            if (!sessionManager.checkUserSession(request))
            {
                if (isAjaxRequest)
                {
                    Response resp = new Response();
                    resp.setCode(BusinessCode.SESSION_TIMEOUT.getCode());
                    Responses.response(response, new ObjectMapper().writeValueAsString(resp));
                    return;
                }
                /**
                if (true)
                {
                    UserInfo user = userManager.getUserInfo("o853pt5GcnBVYP0Tg1Fal9DJFXIk", true);
                    sessionManager.refreshUserSession(request, user);
                    response.sendRedirect("http://localhost:8080/wx/account");
                    return;
                }*/
                
                // 微信回调地址
                if (requestURL.equals("/user/auth"))
                {
                    String r = "http://weixin.acarry.com/wx/top?rmd=" + new Date().getTime();
                    if (!StringUtils.isEmpty(request.getParameter("r")))
                    {
                        r = URLDecoder.decode(request.getParameter("r"), "UTF-8");
                        LOGGER.info("weixin callback url {}", r);
                        
                        // 编码转换
                        r = Encodes.javaUnescape(r);
                        LOGGER.info("weixin callback url after javaUnescape {}", r);
                    }
                    
                    LOGGER.info("filter to auth {}..", requestURL);
                    String code = request.getParameter("code");
                    if (StringUtils.isEmpty(code))
                    {
                        LOGGER.error("weixin callback code is null");
                        String rr = "http://weixin.acarry.com/wx/top?rmd=" + new Date().getTime();
                        response.sendRedirect(rr);
                        return;
                    }
                    
                    String accessUrl =
                        "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe8626eeb45600bbc&secret=e8c2ed793c14a5740c0fcd781ceb0fb0&code="
                            + code + "&grant_type=authorization_code";
                    LOGGER.info("auth access url  {} ..", accessUrl);
                    try
                    {
                        HttpGet httpGet = new HttpGet();
                        httpGet.setURI(new URI(accessUrl));
                        String resJsonStr = httpClientManager.executeAndGet(httpGet);
                        
                        LOGGER.info("auth access response {} ..", resJsonStr);
                        JsonMapper jsonMapper = new JsonMapper();
                        
                        Map<String, String> accessResponse =
                            jsonMapper.fromJson(resJsonStr,
                                jsonMapper.contructMapType(Map.class, String.class, String.class));
                        
                        String accessToken = accessResponse.get("access_token");
                        String openId = accessResponse.get("openid");
                        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId))
                        {
                            LOGGER.error("get user weixin openId error..");
                            throw new BusinessException();
                        }
                        
                        UserInfo user = userManager.getUserInfo(openId, true);
                        if (user == null)
                        {
                            String userUrl =
                                "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid="
                                    + openId + "&lang=zh_CN";
                            LOGGER.info("auth user url  {} ..", accessUrl);
                            
                            httpGet.setURI(new URI(userUrl));
                            resJsonStr = httpClientManager.executeAndGet(httpGet);
                            LOGGER.info("auth user response {} ..", resJsonStr);
                            WxUserInfo wxUserInfo = jsonMapper.fromJson(resJsonStr, WxUserInfo.class);
                            
                            User saveUser = new User();
                            saveUser.setUserId(openId);
                            
                            String weixinNickName = wxUserInfo.getNickname();
                            
                            weixinNickName = filterNickName(weixinNickName);
                            
                            saveUser.setNickName(weixinNickName);
                            saveUser.setGender(wxUserInfo.getSex());
                            saveUser.setAvatar(wxUserInfo.getHeadimgurl());
                            saveUser.setLanguage(wxUserInfo.getLanguage());
                            saveUser.setStatus(1);
                            saveUser.setIdentityAuth(0);
                            try
                            {
                                userService.save(saveUser);
                            }
                            catch (Exception e)
                            {
                                saveUser.setNickName("==");
                                userService.save(saveUser);
                            }
                            
                            user = userManager.getUserInfo(openId, true);
                        }
                        sessionManager.refreshUserSession(request, user);
                    }
                    catch (Exception e)
                    {
                        LOGGER.error("invoke weixin url fail ", e);
                        throw new BusinessException();
                    }
                    response.sendRedirect(r);
                    return;
                }
                
                String r = requestURL;
                if (!StringUtils.isEmpty(request.getQueryString()))
                {
                    LOGGER.info("web get query string {}", request.getQueryString());
                    r = r + "?" + request.getQueryString();
                }
                if (requestURL.equals("/user/login"))
                {
                    if (!StringUtils.isEmpty(request.getParameter("r")))
                    {
                        r = URLDecoder.decode(request.getParameter("r"), "UTF-8");
                        LOGGER.info("web page need login {}", r);
                    }
                }
                
                if (r.contains("&"))
                {
                    r = Encodes.javaEscape(r);
                }
                
                LOGGER.info("first redirect url  {}", r);
                String r_url =
                    "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe8626eeb45600bbc&redirect_uri="
                        + URLEncoder.encode("http://weixin.acarry.com/user/auth?r=" + r, "UTF-8")
                        + "&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
                LOGGER.info("to redirect url: " + r_url);
                response.sendRedirect(r_url);
                return;
            }
            
            LOGGER.info("base session filter, user : {}", sessionManager.getSessionUserId(request));
            LOGGER.info("base session filter, request : {}", request.getRequestURL().toString());
            if (requestURL.equals("/user/login") || requestURL.equals("/user/auth"))
            {
                String r = request.getParameter("r");
                if (StringUtils.isNotEmpty(r))
                {
                    r = URLDecoder.decode(r, "UTF-8");
                }
                else
                {
                    r = "http://weixin.acarry.com/wx/top?rmd=" + new Date().getTime();
                }
                LOGGER.info("base session filter,is already logined.. so redirect {}", r);
                response.sendRedirect(r);
                return;
            }
            try
            {
                // 记录页面日志
                if (!isAjaxRequest)
                {
                    addLog(request);
                }
                
            }
            catch (Exception e)
            {
                LOGGER.error("add page view log catch an exception", e);
            }
            
            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            LOGGER.error("user session filter error", e);
        }
    }
    
    public static String filterNickName(String content)
    {
        Pattern emoji =
            Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(content);
        if (emojiMatcher.find())
        {
            return "==";
        }
        return content;
    }
    
    public static String filterEmoji(String source)
    {
        if (StringUtils.isNotEmpty(source))
        {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        }
        else
        {
            return source;
        }
    }
    
    private void addLog(HttpServletRequest request)
    {
        LogInfo logInfo = new LogInfo();
        logInfo.setClientIp(IPUtil.getClientAddress(request));
        logInfo.setHostIp(IPUtil.getHostIp());
        logInfo.setOperator(sessionManager.getSessionUserId(request));
        logInfo.setOperateTime(new Date());
        logInfo.setPageUrl(request.getRequestURI());
        HashMap paramMap = new HashMap(request.getParameterMap());
        String ids[] = (String[])paramMap.get("id");
        
        if (ArrayUtils.isNotEmpty(ids))
        {
            logInfo.setReqId(ids[0]);
        }
        logInfo.setReqParam(mapper.toJson(paramMap));
        LogCache.addLogCache(logInfo, "taskmapper.pageview");
    }
}
