package com.kgc.provider.service;

import com.kgc.provider.dto.Shoppingcart;

import java.util.List;

/**
 * Created By: YunCeng
 * Created on: 2019/10/17:19:22
 */
public interface ShoppingcartService {

    List<Shoppingcart> selectAll();
    int insert(Shoppingcart record);
    int deleteByPrimaryKey(Long id);
    int updateByGoodAmount(long id,Integer amount);

}
