package com.portal.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.portal.bean.entity.Need;
import com.portal.bean.entity.NeedAccept;

public interface NeedService extends CrudService<Need>
{
    List<NeedAccept> selectAcpListByNeedId(String needId);
    
    Long saveAcp(NeedAccept entity);
    
    NeedAccept selectAcpByAcpId(NeedAccept entity);
    
    @Transactional
    String confirmNeed(Need entity, NeedAccept acp);
}
