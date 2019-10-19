package com.kgc.provider.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.Order;
import com.kgc.provider.mapper.OrderMapper;
import com.kgc.provider.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Campbell on 2019/10/19 10:28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public int addOrder(Order order){
        return orderMapper.insertSelective(order);
    }

    public List<Order> getOrderList(Order order){
        return orderMapper.getGoodList(order);
    }

    public int count(Order order){
        return orderMapper.count(order);
    }

    public boolean isDel(int id){
        boolean flag = orderMapper.updateById(id) == 1 ? true : false;
        return flag;
    }
}
