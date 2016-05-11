package com.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portal.bean.PagingData;
import com.portal.bean.entity.Need;
import com.portal.bean.entity.NeedAccept;
import com.portal.bean.entity.Order;
import com.portal.common.mapper.JsonMapper;
import com.portal.common.util.Identities;
import com.portal.constant.BaseConstant;
import com.portal.constant.StatusConstant;
import com.portal.mapper.NeedMapper;
import com.portal.mapper.OrderMapper;
import com.portal.service.NeedService;

@Service
public class NeedServiceImpl implements NeedService
{
    
    @Autowired
    NeedMapper needMapper;
    
    @Autowired
    OrderMapper orderMapper;
    
    @Override
    public Long save(Need entity)
    {
        return needMapper.save(entity);
    }
    
    @Override
    public Integer deleteById(Need entity)
    {
        return needMapper.deleteById(entity);
    }
    
    @Override
    public Integer updateById(Need entity)
    {
        return needMapper.updateById(entity);
    }
    
    @Override
    public Need selectById(String id)
    {
        return needMapper.selectById(id);
    }
    
    @Override
    public Need selectOne(Need entity)
    {
        return needMapper.selectOne(entity);
    }
    
    @Override
    public PagingData<Need> selectByIndex(Need entity)
    {
        PagingData<Need> pagingData = new PagingData<Need>();
        int count = needMapper.selectByIndexCount(entity);
        if (count > 0)
        {
            pagingData.setData(needMapper.selectByIndex(entity));
        }
        pagingData.setCount(count);
        return pagingData;
    }
    
    @Override
    public List<NeedAccept> selectAcpListByNeedId(String needId)
    {
        return needMapper.selectAcpListByNeedId(needId);
    }
    
    @Override
    public Long saveAcp(NeedAccept entity)
    {
        return needMapper.saveAcp(entity);
    }
    
    @Override
    public NeedAccept selectAcpByAcpId(NeedAccept entity)
    {
        return needMapper.selectAcpByAcpId(entity);
    }
    
    @Override
    public String confirmNeed(Need entity, NeedAccept acp)
    {
        // 生成订单
        Order order = getOrder(entity, acp);
        orderMapper.save(order);
        return order.getId();
    }
    
    private Order getOrder(Need entity, NeedAccept acp)
    {
        Order order = new Order();
        JsonMapper mapper = new JsonMapper();
        order.setId(BaseConstant.ORDER_PREFIX + Identities.getSystemId());
        order.setServiceId(entity.getId());
        order.setBuyerId(entity.getUserId());
        order.setSaleId(acp.getAcpUser());
        Double price =
            (null == acp.getOriginprice() ? 0.0 : acp.getOriginprice())
                + (null == acp.getPostprice() ? 0.0 : acp.getPostprice())
                + (null == acp.getServiceprice() ? 0.0 : acp.getServiceprice())
                + (null == acp.getAddprice() ? 0.0 : acp.getAddprice());
        order.setPrice(price);
        order.setCount(entity.getCount());
        order.setTotalPrice(price);
        order.setServiceContent(mapper.toJson(entity));
        order.setStatus(StatusConstant.ORDER_NEED);
        order.setOperator(entity.getUserId());
        
        return order;
    }
}
