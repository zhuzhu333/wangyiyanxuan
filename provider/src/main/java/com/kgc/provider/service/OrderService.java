package com.kgc.provider.service;

import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.Order;

import java.util.List;

/**
 * Created by Campbell on 2019/10/19 10:28
 */
public interface OrderService {

    int addOrder(Order order);

    List<Order> getOrderList(Order order);

    int count(Order order);

    boolean isDel(int id);

    List<Order> recycle();

    int delRecycle();

    int updateGoodIntegral(int id, int score);

    Good isExist(String goodId);

    void cutStock(String phone);

    //减库存
    void delStock(String gid);

}
