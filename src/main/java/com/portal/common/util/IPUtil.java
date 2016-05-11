package com.portal.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IPUtil.class);
    
    public static String getClientAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (isValidIP(ip))
        {
            ip = ip.split(",", -1)[0].trim();
            LOGGER.info("Get ip [{0}] from http header [x-forwarded-for].", ip);
            return ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (isValidIP(ip))
        {
            LOGGER.info("Get ip [{0}] from http header [Proxy-Client-IP].", ip);
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (isValidIP(ip))
        {
            LOGGER.info("Get ip [{0}] from http header [WL-Proxy-Client-IP].", ip);
            return ip;
        }
        ip = request.getRemoteAddr();
        LOGGER.info("Get ip [{0}] from remote address.", ip);
        return ip;
    }
    
    private static boolean isValidIP(String ip)
    {
        return (!StringUtils.isEmpty(ip)) && (!"unknown".equalsIgnoreCase(ip));
    }
    
    public static String getHostIp()
    {
        String tomcatxmlPath =
            Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/server/tomcat.xml";
        
        FileReader in = null;
        BufferedReader buffer = null;
        try
        {
            in = new FileReader(tomcatxmlPath);
            buffer = new BufferedReader(in);
            String lineStr = null;
            while ((lineStr = buffer.readLine()) != null)
            {
                lineStr = lineStr.trim();
                if ((lineStr.indexOf("Connector") > -1) && (lineStr.indexOf("address") > -1))
                {
                    return subString(lineStr, "", "address=\"", "\"");
                }
            }
            if (null != buffer)
            {
                try
                {
                    buffer.close();
                }
                catch (IOException e)
                {
                    LOGGER.error("Failed to close", e);
                }
            }
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    LOGGER.error(" catch one exception when close tomcat.xml.");
                }
            }
            try
            {
                return InetAddress.getLocalHost().getHostAddress();
            }
            catch (UnknownHostException e)
            {
                LOGGER.error("get local ip failed", e, new Object[0]);
            }
        }
        catch (FileNotFoundException e)
        {
            LOGGER.error(" The file of " + tomcatxmlPath + " is not exit.");
        }
        catch (IOException e)
        {
            LOGGER.error(" catch one exception when read tomcat.xml.");
        }
        finally
        {
            if (null != buffer)
            {
                try
                {
                    buffer.close();
                }
                catch (IOException e)
                {
                    LOGGER.error("Failed to close", e);
                }
            }
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    LOGGER.error(" catch one exception when close tomcat.xml.");
                }
            }
        }
        return "127.0.0.1";
    }
    
    public static String subString(String content, String keyString, String beforeString, String afterString)
    {
        int startIndex;
        if ((keyString == null) || ("".equals(keyString)))
        {
            startIndex = 0;
        }
        else
        {
            startIndex = content.indexOf(keyString);
        }
        if (startIndex > -1)
        {
            startIndex += keyString.length();
            int sIndex = content.indexOf(beforeString, startIndex);
            sIndex = sIndex > -1 ? sIndex + beforeString.length() : sIndex;
            if (sIndex > -1)
            {
                int eIndex = content.indexOf(afterString, sIndex);
                if ((eIndex > -1) && (eIndex >= sIndex))
                {
                    return content.substring(sIndex, eIndex);
                }
            }
        }
        return null;
    }
    
    public static void main(String[] args)
    {
        System.out.println("......" + getHostIp());
    }
}
