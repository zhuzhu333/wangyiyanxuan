package com.kgc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.GoodExample;
import com.kgc.provider.dto.Order;
import com.kgc.provider.dto.OrderExample;
import com.kgc.provider.mapper.GoodMapper;
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
    @Autowired
    private GoodMapper goodMapper;

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
        boolean flag = orderMapper.isDel(id) == 1 ? true : false;
        return flag;
    }

    public List<Order> recycle(){
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIsDeleteEqualTo(1);
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        return orderList;
    }

    public int delRecycle(){
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIsDeleteEqualTo(1);
        int delCount = orderMapper.deleteByExample(orderExample);
        return delCount;
    }

    public int updateGoodIntegral(int id,int score){
        Order order = orderMapper.selectByPrimaryKey((long) id);
        if (order.getStatus() == 1){
            if ((score>0)&&(score<=10)){
                return orderMapper.updateGoodIntegral(id,score);
            }
        }
        return 0;
    }

    @Override
    public Good isExist(String goodId) {
        Good good=new Good();
        GoodExample goodExample=new GoodExample();
        goodExample.createCriteria().andGoodIdEqualTo(goodId);
        List<Good> list=goodMapper.selectByExample(goodExample);
        return list.get(0);

    }


    @Override
    public void cutStock(String phone) {

    }

    @Override
    public void delStock(String gid) {
        orderMapper.delStock(gid);
    }

}
