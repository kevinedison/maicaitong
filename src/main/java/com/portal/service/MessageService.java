package com.portal.service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Message;
import com.portal.bean.entity.MessageRelation;

public interface MessageService extends CrudService<Message>
{
    
    PagingData<MessageRelation> seletctRelation(MessageRelation messageRelation);
    
    Long saveRelation(MessageRelation messageRelation);
    
    Integer updateRelationByRelationId(MessageRelation messageRelation);
    
    Integer deleteMessageByRelId(String id);
    
    MessageRelation seletctSaveRelation(MessageRelation messageRelation);
}
