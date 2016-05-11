package com.portal.common.filter;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class ParameterRequestWrapper extends HttpServletRequestWrapper
{
    private Map params;
    
    private HttpServletResponse response;
    
    private String handleClass;
    
    public ParameterRequestWrapper(HttpServletRequest request, HttpServletResponse resp, Map map)
    {
        super(request);
        this.response = resp;
        this.params = map;
    }
    
    public Map getParameterMap()
    {
        return this.params;
    }
    
    public Enumeration getParameterNames()
    {
        Vector pNames = new Vector(this.params.keySet());
        return pNames.elements();
    }
    
    public String getHandleClass()
    {
        return this.handleClass;
    }
    
    public void setHandleClass(String handleClass)
    {
        this.handleClass = handleClass;
    }
    
    public String getParameter(String name)
    {
        Object v = this.params.get(name);
        
        return getParameterValue(v);
    }
    
    private String getParameterValue(Object v)
    {
        if (v == null)
        {
            return null;
        }
        if ((v instanceof String[]))
        {
            String[] strArr = (String[])v;
            if (strArr.length > 0)
            {
                return strArr[0];
            }
            return null;
        }
        if ((v instanceof String))
        {
            return (String)v;
        }
        return v.toString();
    }
    
    public String[] getParameterValues(String name)
    {
        Object pValue = this.params.get(name);
        if (pValue == null)
        {
            return null;
        }
        if ((pValue instanceof String))
        {
            return new String[] {(String)pValue};
        }
        if ((pValue instanceof String[]))
        {
            return (String[])pValue;
        }
        return new String[] {pValue.toString()};
    }
    
    public HttpServletResponse getResponse()
    {
        return this.response;
    }
    
    public void setResponse(HttpServletResponse response)
    {
        this.response = response;
    }
}