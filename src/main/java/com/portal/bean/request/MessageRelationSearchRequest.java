package com.portal.bean.request;

import org.springframework.beans.BeanUtils;

import com.portal.bean.RequestPage;
import com.portal.bean.entity.MessageRelation;

public class MessageRelationSearchRequest extends RequestPage
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 3678141740506907243L;
    
    public MessageRelation toEntity()
    {
        MessageRelation messageRelation = new MessageRelation();
        BeanUtils.copyProperties(this, messageRelation);
        
        messageRelation.setOffset(getOffset());
        messageRelation.setLimit(getLimit());
        
        return messageRelation;
    }
    
}
