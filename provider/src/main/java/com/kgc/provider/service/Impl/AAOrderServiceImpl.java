package com.kgc.provider.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.Order;
import com.kgc.provider.mapper.OrderMapper;
import com.kgc.provider.service.AAOrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created By: YunCeng
 * Created on: 2019/10/22:18:52
 */
@Service
public class AAOrderServiceImpl implements AAOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order selectByGoodId(String goodId) {
        Order order=orderMapper.selectByGoodId(goodId);

        if(1==1){

        }

        return order;
    }

    @Override
    public Order selectBygoodIdAndPhone(String goodId, String phone) {
        return orderMapper.selectBygoodIdAndPhone(goodId,phone);
    }

    @Override
    public int updateStatus(String code) {
        return orderMapper.updateStatus(code);
    }
}
