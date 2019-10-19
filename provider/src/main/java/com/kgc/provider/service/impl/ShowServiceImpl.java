package com.kgc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.GoodExample;
import com.kgc.provider.dto.User;
import com.kgc.provider.dto.UserExample;
import com.kgc.provider.mapper.GoodMapper;
import com.kgc.provider.mapper.OrderMapper;
import com.kgc.provider.mapper.UserMapper;
import com.kgc.provider.service.ShowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created BY: WYM
 * Created on: 2019/10/17 17:39
 */

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Good showGoods(String gid) {

        GoodExample goodExample = new GoodExample();
        goodExample.createCriteria().andGoodIdEqualTo(gid);
        List<Good> goodList = goodMapper.selectByExample(goodExample);
        if (null != goodList) {
            Good good = new Good();
            BeanUtils.copyProperties(goodList.get(0),good);
            good.setGoodPrice(goodList.get(0).getGoodPrice() * 0.98);
            return good;
        }
        return null;
    }

    @Override
    public User queryIntegral(String uid) {
        UserExample userExample = new UserExample();
        User user = userMapper.selectByPrimaryKey(new Long(uid));
        if (null != user) {
            return user;
        }
        return null;
    }

    @Override
    public int highPraise(String gid) {
        int highPraise = orderMapper.highPraise(gid);
        if (highPraise!=0){
            return highPraise;
        }
        return 0;
    }

    @Override
    public int totalOrder(String gid) {
        int totalOrder = orderMapper.totalOrder(gid);
        if (totalOrder!=0){
            return totalOrder;
        }
        return 0;
    }
}
