package com.portal.wechat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.portal.common.context.BaseContext;
import com.portal.manager.SignatureManager;
import com.portal.wechat.bean.MessageTemplate;
import com.portal.wechat.service.WechatMessageService;
import com.thoughtworks.xstream.XStream;

/**
 * User: rizenguo Date: 2014/10/23 Time: 14:59
 */
public class Util
{
    
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
    
    /**
     * 通过反射的方式遍历对象的属性和属性值，方便调试
     *
     * @param o 要遍历的对象
     * @throws Exception
     */
    public static void reflect(Object o)
        throws Exception
    {
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field f = fields[i];
            f.setAccessible(true);
            Util.log(f.getName() + " -> " + f.get(o));
        }
    }
    
    public static byte[] readInput(InputStream in)
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) > 0)
        {
            out.write(buffer, 0, len);
        }
        out.close();
        in.close();
        return out.toByteArray();
    }
    
    public static String inputStreamToString(InputStream is)
        throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1)
        {
            baos.write(i);
        }
        return baos.toString();
    }
    
    public static InputStream getStringStream(String sInputString)
    {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals(""))
        {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }
    
    public static Object getObjectFromXML(String xml, Class tClass)
    {
        // 将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", tClass);
        xStreamForResponseData.ignoreUnknownElements();// 暂时忽略掉一些新增的字段
        return xStreamForResponseData.fromXML(xml);
    }
    
    public static String getStringFromMap(Map<String, Object> map, String key, String defaultValue)
    {
        if (key == "" || key == null)
        {
            return defaultValue;
        }
        String result = (String)map.get(key);
        if (result == null)
        {
            return defaultValue;
        }
        else
        {
            return result;
        }
    }
    
    public static int getIntFromMap(Map<String, Object> map, String key)
    {
        if (key == "" || key == null)
        {
            return 0;
        }
        if (map.get(key) == null)
        {
            return 0;
        }
        return Integer.parseInt((String)map.get(key));
    }
    
    /**
     * 打log接口
     * 
     * @param log 要打印的log字符串
     * @return 返回log
     */
    public static String log(Object log)
    {
        LOGGER.info(log.toString());
        // System.out.println(log);
        return log.toString();
    }
    
    /**
     * 读取本地的xml数据，一般用来自测用
     * 
     * @param localPath 本地xml文件路径
     * @return 读到的xml字符串
     */
    public static String getLocalXMLString(String localPath)
        throws IOException
    {
        return Util.inputStreamToString(Util.class.getResourceAsStream(localPath));
    }
    
    public static void sendNotify(MessageTemplate message)
    {
        Map<String, String> weixinConfig = (Map<String, String>)BaseContext.getBean("wechatConfig");
        SignatureManager signatureManager = BaseContext.getBean("signatureManager", SignatureManager.class);
        
        Configure.setCertLocalPath(Util.class.getResource("/").getPath() + "/cert/apiclient_cert.p12");
        String messageUrl = weixinConfig.get("messageUrl");
        messageUrl += signatureManager.getAccessToken(Boolean.FALSE);
        LOGGER.info("Post message url: " + messageUrl);
        try
        {
            WechatMessageService messageService = new WechatMessageService(messageUrl);
            String result = messageService.request(message);
            LOGGER.info("Post message result: " + result);
        }
        catch (Exception e)
        {
            LOGGER.error("Post message catch an exception", e);
        }
    }
    
}
