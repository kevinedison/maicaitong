package com.portal.mapper;

import java.util.List;

import com.portal.bean.entity.Need;
import com.portal.bean.entity.NeedAccept;

public interface NeedMapper extends CrudMapper<Need>
{
    List<NeedAccept> selectAcpListByNeedId(String needId);
    
    Long saveAcp(NeedAccept entity);
    
    NeedAccept selectAcpByAcpId(NeedAccept entity);
    
    Long updateAcpList(List<NeedAccept> list);
}
