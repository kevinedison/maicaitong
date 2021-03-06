package com.portal.rest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.portal.bean.IdRequest;
import com.portal.bean.ResponseData;
import com.portal.bean.request.FileUploadRequest;
import com.portal.common.context.BaseContext;
import com.portal.common.util.Identities;
import com.portal.common.util.Responses;
import com.portal.constant.BusinessCode;
import com.portal.manager.HttpClientManager;
import com.portal.manager.SessionManager;
import com.portal.manager.SignatureManager;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class FileController
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
    
    @Autowired
    SessionManager sessionManager;
    
    @Autowired
    HttpClientManager httpClientManager;
    
    @Autowired
    SignatureManager signatureManager;
    
    @RequestMapping(value = "/user/file/upload", method = RequestMethod.POST)
    public void fileUpload(HttpServletRequest request,HttpServletResponse response, @Valid FileUploadRequest fileUploadRequest)
    {
        ResponseData<String> responseData = new ResponseData<String>();
        
        String operator = sessionManager.getSessionUserId(request);
        
        CommonsMultipartFile file = fileUploadRequest.getFile()[0];
        if (StringUtils.isEmpty(file.getOriginalFilename()) || file.getSize() == 0)
        {
            LOGGER.info("{} upload file is empty...", operator);
            responseData.setCode(BusinessCode.UPLOAD_FILE_FAIL.getCode());
            Responses.responseHtml(response, responseData);
            return;
        }
        
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String path = BaseContext.getMessage("image.folder") + operator + "/";
        LOGGER.info("the upload file path is {0}...", path);
        java.io.File folder = new java.io.File(path);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        
        String newPicName = Identities.uuid2() + "." + ext;
        String filePath = path + newPicName;
        LOGGER.info("file {} save path {}", file.getOriginalFilename(), filePath);
        java.io.File target = new java.io.File(filePath);
        try
        {
            IOUtils.copy(file.getInputStream(), new FileOutputStream(target));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        String accessPath = "/" + operator + "/" + newPicName;
        LOGGER.info("file {} access path {}", newPicName, accessPath);
        responseData.setData(accessPath);
        Responses.responseHtml(response, responseData);
    }
    
    @RequestMapping(value = "/user/wx/file/download", method = RequestMethod.GET)
    public void downloadWxFile(HttpServletRequest request,HttpServletResponse response, @Valid IdRequest idRequest)
    {
        ResponseData<String> responseData = new ResponseData<String>();
        
        String operator = sessionManager.getSessionUserId(request);
        String wxFilePath = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+signatureManager.getAccessToken(true)+"&media_id="+idRequest.getId();
        
        String path = BaseContext.getMessage("image.folder") + operator + "/";
        LOGGER.info("the upload file path is {0}...", path);
        java.io.File folder = new java.io.File(path);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        
        String fileName = Identities.uuid2() + ".png";
        String filePath = path + fileName;
        LOGGER.info("file {} save path {}", wxFilePath, filePath);
        java.io.File target = new java.io.File(filePath);
        try
        {
            FileUtils.copyURLToFile(new URL(wxFilePath), target);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        LOGGER.info("save file size : " + target.length());
        String accessPath = operator + "/" + fileName;
        LOGGER.info("file imageId {} access path {}", idRequest.getId(), accessPath);
        responseData.setData(accessPath);
        Responses.response(response, responseData);
    }
}
