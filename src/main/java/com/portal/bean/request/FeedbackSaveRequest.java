package com.portal.bean.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import com.portal.bean.Request;
import com.portal.bean.entity.Feedback;
import com.portal.common.util.Identities;
import com.portal.constant.StatusConstant;

public class FeedbackSaveRequest extends Request {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1442990138141764246L;

    @NotBlank
    private String question;
    
    private String picture;
    
    public Feedback toEntity(String operator) {
        Feedback feedBack = new Feedback();
        BeanUtils.copyProperties(this, feedBack);
        feedBack.setId(Identities.uuid2());
        feedBack.setOperator(operator);
        feedBack.setOperateTime(new Date());
        
        feedBack.setStatus(StatusConstant.STATUS_ONLINE);
        return feedBack;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }
}
