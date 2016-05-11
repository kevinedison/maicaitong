package com.portal.service;

import org.springframework.transaction.annotation.Transactional;

import com.portal.bean.entity.Bill;
import com.portal.wechat.bean.WechatTransferResData;

public interface BillService extends CrudService<Bill>
{
    @Transactional
    WechatTransferResData transfer(Bill bill);
}
