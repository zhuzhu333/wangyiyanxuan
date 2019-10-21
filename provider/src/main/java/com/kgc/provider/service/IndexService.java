package com.kgc.provider.service;

import com.kgc.provider.dto.Good;
import com.kgc.provider.dto.GoodsGroup;
import com.kgc.provider.dto.Order;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/17 19:08
 * @since
 */
public interface IndexService {
    public List<GoodsGroup> showGroup();
    public  GoodsGroup selectByName(String groupName);
    public List<Good> selectBySort(int goodSort,int sPage,int pSize);
    public Good selectByGoodName(String goodName);
    public Good selectHotGood();
    public List<Good> getAllGoods(String goodName,int sPage,int pSize);
}
