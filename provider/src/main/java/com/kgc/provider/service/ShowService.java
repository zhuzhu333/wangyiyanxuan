package com.kgc.provider.service;

import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.User;

import java.util.List;

/**
 * Created BY: WYM
 * Created on: 2019/10/17 17:34
 */
public interface ShowService {

    //展示商品
    Good showGoods(String gid);
    //查出用户的积分
    User queryIntegral(String uid);
    //查订单中某个商品好评的个数
    int highPraise(String gid);
    //查订单中某个商品总个数
    int totalOrder(String gid);

}
