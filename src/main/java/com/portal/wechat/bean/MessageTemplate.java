package com.portal.wechat.bean;

import java.util.HashMap;
import java.util.Map;

import com.portal.bean.Model;
import com.portal.common.mapper.JsonMapper;

public class MessageTemplate extends Model
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7820159162324111204L;
    
    private String touser;
    
    private String template_id;
    
    private String url;
    
    private String topcolor;
    
    private Map<String, Map<String, String>> data;
    
    public MessageTemplate()
    {
        
    }
    
    public MessageTemplate(String toUser, String templatId, String url, Map<String, Map<String, String>> data)
    {
        this.touser = toUser;
        this.template_id = templatId;
        this.url = url;
        this.topcolor = "#FF0000";
        this.data = data;
    }
    
    public String getTouser()
    {
        return touser;
    }
    
    public void setTouser(String touser)
    {
        this.touser = touser;
    }
    
    public String getTemplate_id()
    {
        return template_id;
    }
    
    public void setTemplate_id(String template_id)
    {
        this.template_id = template_id;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getTopcolor()
    {
        return topcolor;
    }
    
    public void setTopcolor(String topcolor)
    {
        this.topcolor = topcolor;
    }
    
    public Map<String, Map<String, String>> getData()
    {
        return data;
    }
    
    public void setData(Map<String, Map<String, String>> data)
    {
        this.data = data;
    }
    
}
