/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.portal.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装各种格式的编码解码工具类.
 * 
 * 1.Commons-Codec的 hex/base64 编码 2.自制的base62 编码 3.Commons-Lang的xml/html escape 4.JDK提供的URLEncoder
 * 
 * @author calvin
 */
public class Encodes
{
    
    private static final String DEFAULT_URL_ENCODING = "UTF-8";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Encodes.class);
    
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    
    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input)
    {
        return Hex.encodeHexString(input);
    }
    
    /**
     * Hex解码.
     */
    public static byte[] decodeHex(String input)
    {
        try
        {
            return Hex.decodeHex(input.toCharArray());
        }
        catch (DecoderException e)
        {
            throw Exceptions.unchecked(e);
        }
    }
    
    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input)
    {
        return Base64.encodeBase64String(input);
    }
    
    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     */
    public static String encodeUrlSafeBase64(byte[] input)
    {
        return Base64.encodeBase64URLSafeString(input);
    }
    
    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input)
    {
        return Base64.decodeBase64(input);
    }
    
    /**
     * Base62编码。
     */
    public static String encodeBase62(byte[] input)
    {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++)
        {
            chars[i] = BASE62[(input[i] & 0xFF) % BASE62.length];
        }
        return new String(chars);
    }
    
    /**
     * Html 转码.
     */
    public static String escapeHtml(String html)
    {
        return StringEscapeUtils.escapeHtml4(html);
    }
    
    /**
     * Html 解码.
     */
    public static String unescapeHtml(String htmlEscaped)
    {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }
    
    /**
     * Xml 转码.
     */
    public static String escapeXml(String xml)
    {
        return StringEscapeUtils.escapeXml(xml);
    }
    
    /**
     * Xml 解码.
     */
    public static String unescapeXml(String xmlEscaped)
    {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }
    
    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String part)
    {
        try
        {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        }
        catch (UnsupportedEncodingException e)
        {
            throw Exceptions.unchecked(e);
        }
    }
    
    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String part)
    {
        
        try
        {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        }
        catch (UnsupportedEncodingException e)
        {
            throw Exceptions.unchecked(e);
        }
    }
    
    public static String javaEscape(String s)
    {
        ScriptEngineManager sem = new ScriptEngineManager();
        
        ScriptEngine engine = sem.getEngineByExtension("js");
        try
        {
            // 直接解析
            String res = (String)engine.eval("escape('" + s + "')");
            return res;
        }
        catch (Exception ex)
        {
            LOGGER.error("java escape catch an exception:" + ex);
            return "";
        }
    }
    
    public static String javaUnescape(String s)
    {
        ScriptEngineManager sem = new ScriptEngineManager();
        
        ScriptEngine engine = sem.getEngineByExtension("js");
        try
        {
            // 直接解析
            String res = (String)engine.eval("unescape('" + s + "')");
            return res;
        }
        catch (Exception ex)
        {
            LOGGER.error("java unescape catch an exception:" + ex);
            return "";
        }
    }
    
    public static void main(String[] args)
    {
        String s = javaEscape("/wx/account-service?rmd=1457747606258&shareId=o853pt1EmuCezWwbYNAQWNU9Phqw&from=singlemessage&isappinstalled=0");
        System.out.println(s);
        
        System.out.println(javaUnescape(s));
    }
    
}
