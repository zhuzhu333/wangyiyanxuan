package com.kgc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.Shoppingcart;
import com.kgc.provider.mapper.ShoppingcartMapper;
import com.kgc.provider.service.ShoppingcartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created By: YunCeng
 * Created on: 2019/10/17:19:23
 */
@Service
public class ShoppingcartServiceImpl implements ShoppingcartService {
    @Autowired
    private ShoppingcartMapper shoppingcartMapper;

    @Override
    public List<Shoppingcart> selectAll() {
        return shoppingcartMapper.selectAll();
    }

    @Override
    public int insert(Shoppingcart record) {
        return shoppingcartMapper.insert(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return shoppingcartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByGoodAmount(long id, Integer amount) {
        return shoppingcartMapper.updateByGoodAmount(id,amount);
    }

    @Override
    public Shoppingcart selectByPrimaryKey(Long id) {
        return shoppingcartMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Shoppingcart> selectByName(String name) {
        return shoppingcartMapper.selectByName(name);
    }

    @Override
    public int updateByGoodName(String goodname, Integer amount) {
        return shoppingcartMapper.updateByGoodName(goodname,amount);
    }
}
