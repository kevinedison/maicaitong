package com.portal.mapper;

import java.util.List;

import com.portal.bean.entity.Message;
import com.portal.bean.entity.MessageRelation;

public interface MessageMapper extends CrudMapper<Message>
{
    List<MessageRelation> seletctRelation(MessageRelation messageRelation);
    
    Long saveRelation(MessageRelation messageRelation);
    
    Integer updateRelationByRelationId(MessageRelation messageRelation);
    
    Integer seletctRelationCount(MessageRelation messageRelation);
    
    Integer deleteMessageByRelId(String id);
    
    MessageRelation seletctSaveRelation(MessageRelation messageRelation); 
    
    Message selectLatestTime(String id);
}
